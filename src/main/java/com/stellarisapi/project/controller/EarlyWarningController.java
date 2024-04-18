package com.stellarisapi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stellaris.stellarisapicommon.common.BaseResponse;
import com.stellaris.stellarisapicommon.common.DeleteRequest;
import com.stellaris.stellarisapicommon.common.ErrorCode;
import com.stellaris.stellarisapicommon.common.ResultUtils;
import com.stellaris.stellarisapicommon.constant.CommonConstant;
import com.stellaris.stellarisapicommon.exception.BusinessException;
import com.stellaris.stellarisapicommon.model.entity.User;
import com.stellaris.stellarisapicommon.model.entity.UserInterfaceInfo;
import com.stellarisapi.project.annotation.AuthCheck;

import com.stellarisapi.project.model.dto.earlyWarning.EarlyWarningAddRequest;
import com.stellarisapi.project.model.dto.earlyWarning.EarlyWarningQueryRequest;
import com.stellarisapi.project.model.entity.EarlyWarning;
import com.stellarisapi.project.service.EarlyWarningService;
import com.stellarisapi.project.service.UserInterfaceInfoService;
import com.stellarisapi.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/earlyWarning")
public class EarlyWarningController {
    @Resource
    private EarlyWarningService earlyWarningService;

    @Resource
    private UserService userService;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 创建
     *
     * @param earlyWarningAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addEarlyWarning(@RequestBody EarlyWarningAddRequest earlyWarningAddRequest, HttpServletRequest request) {
        if (earlyWarningAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        EarlyWarning earlyWarning = new EarlyWarning();
        BeanUtils.copyProperties(earlyWarningAddRequest, earlyWarning);
        // 校验
        earlyWarningService.validEarlyWarning(earlyWarning, true);
        User loginUser = userService.getLoginUser(request);
        earlyWarning.setCreateUserId(loginUser.getId());
        boolean result = earlyWarningService.save(earlyWarning);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newEarlyWarningId = earlyWarning.getId();
        return ResultUtils.success(newEarlyWarningId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEarlyWarning(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        EarlyWarning oldEarlyWarning = earlyWarningService.getById(id);
        if (oldEarlyWarning == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldEarlyWarning.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = earlyWarningService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<EarlyWarning> getEarlyWarningById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        EarlyWarning earlyWarning = earlyWarningService.getById(id);
        return ResultUtils.success(earlyWarning);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param earlyWarningQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<EarlyWarning>> listEarlyWarning(EarlyWarningQueryRequest earlyWarningQueryRequest) {
        EarlyWarning earlyWarningQuery = new EarlyWarning();
        if (earlyWarningQueryRequest != null) {
            BeanUtils.copyProperties(earlyWarningQueryRequest, earlyWarningQuery);
        }
        QueryWrapper<EarlyWarning> queryWrapper = new QueryWrapper<>(earlyWarningQuery);
        List<EarlyWarning> earlyWarningList = earlyWarningService.list(queryWrapper);
        return ResultUtils.success(earlyWarningList);
    }

    /**
     * 分页获取列表
     *
     * @param earlyWarningQueryRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list/page")
    public BaseResponse<Page<EarlyWarning>> listEarlyWarningByPage(EarlyWarningQueryRequest earlyWarningQueryRequest, HttpServletRequest request) {
        if (earlyWarningQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        EarlyWarning earlyWarningQuery = new EarlyWarning();
        BeanUtils.copyProperties(earlyWarningQueryRequest, earlyWarningQuery);
        long current = earlyWarningQueryRequest.getCurrent();
        long size = earlyWarningQueryRequest.getPageSize();
        String sortField = earlyWarningQueryRequest.getSortField();
        String sortOrder = earlyWarningQueryRequest.getSortOrder();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<EarlyWarning> queryWrapper = new QueryWrapper<>(earlyWarningQuery);
//        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<EarlyWarning> earlyWarningPage = earlyWarningService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(earlyWarningPage);
    }

    /**
     * 分页获取列表
     *
     * @param earlyWarningQueryRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list/send/page")
    public BaseResponse<Page<UserInterfaceInfo>> listEarlyWarningSendByPage(EarlyWarningQueryRequest earlyWarningQueryRequest, HttpServletRequest request) {
        if (earlyWarningQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = earlyWarningQueryRequest.getCurrent();
        long size = earlyWarningQueryRequest.getPageSize();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(UserInterfaceInfo::getLeftNum,50);
        Page<UserInterfaceInfo> earlyWarningPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(earlyWarningPage);
    }

}
