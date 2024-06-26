package com.stellarisapi.project.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellaris.stellarisapicommon.model.enums.UserRoleEnum;
import com.stellarisapi.project.common.DeleteRequest;
import com.stellarisapi.project.common.ErrorCode;
import com.stellarisapi.project.exception.BusinessException;
import com.stellarisapi.project.mapper.UserMapper;
import com.stellarisapi.project.service.UserService;
import com.stellaris.stellarisapicommon.model.entity.User;
import com.stellarisapi.project.utils.SymmetricEncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.crypto.digest.DigestUtil.*;
import static com.stellarisapi.project.constant.UserConstant.ADMIN_ROLE;
import static com.stellarisapi.project.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户服务实现类
 *
 
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yidiansishiyi";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 分配 accessKey, secretKey
            String accessKey = md5Hex(SALT + userAccount + RandomUtil.randomNumbers(5));
            String secretKey = md5Hex(SALT + userAccount + RandomUtil.randomNumbers(8));
            // 4. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && ADMIN_ROLE.equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public Boolean resetAccessKey(DeleteRequest deleteRequest, HttpServletRequest request) {
        User loginUser = getLoginUser(request);
        Long loginUserId = loginUser.getId();
        String userRole = loginUser.getUserRole();
        UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(userRole);

        if (!Objects.equals(loginUserId, deleteRequest.getId()) && !UserRoleEnum.ADMIN.equals(enumByValue)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        QueryWrapper<User> userUpdateWrapper = new QueryWrapper<>();
        userUpdateWrapper.eq("id", loginUserId);

        User updateUser = getOne(userUpdateWrapper);
        if (updateUser == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long userId = updateUser.getId();
        String accessKey = DigestUtil.md5Hex(SALT  + userId + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(SALT + userId + RandomUtil.randomNumbers(8));
        updateUser.setAccessKey(accessKey);
        updateUser.setSecretKey(secretKey);

        return updateById(updateUser);
    }

    @Override
    public Map<String, Object> getAccessKey(HttpServletRequest request) throws Exception {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();
        Long userId = user.getId();
        String userMailbox = user.getUserMailbox();

        String secretKeyAES = SymmetricEncryptionUtil.getSecretKeyAES(userId.toString(), userMailbox);
        String ak = SymmetricEncryptionUtil.encryptAES(accessKey, secretKeyAES);
        String ck = SymmetricEncryptionUtil.encryptAES(secretKey, secretKeyAES);

        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("accessKeyBytes" ,ak);
        resMap.put("secretKeyBytes" ,ck);
        return resMap;
    }

    public static void main(String[] args) {
        // 待加密的原始数据
        String data = "Hello, AES!";

        String accessKey = "12d";
        String secretKey = "12djij";
        Long userId = 1l;
        String key = userId + "yidiansishiyi";
        SymmetricCrypto aes = new SymmetricCrypto("AES", key.getBytes());

        // AES 加密
        byte[] accessKeyBytes = aes.encrypt(accessKey);
        String s = new String(accessKeyBytes);
        byte[] secretKeyBytes = aes.encrypt(secretKey);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("accessKeyBytes" ,s);
        resMap.put("secretKeyBytes" ,secretKeyBytes);
        String s1 = resMap.get(secretKeyBytes).toString();


//        byte[] decryptBytes = aes.decrypt(byte[] resMap.get(secretKeyBytes));

        // 将字节数组转换为字符串
//        String decryptedStr = new String(decryptBytes);

        // AES 解密
//        byte[] decryptBytes = aes.decrypt(encryptBytes);
//
//        // 将字节数组转换为字符串
//        String decryptedStr = new String(decryptBytes);

        // 输出解密后的数据
//        System.out.println("解密后的数据：" + decryptedStr);
    }

}




