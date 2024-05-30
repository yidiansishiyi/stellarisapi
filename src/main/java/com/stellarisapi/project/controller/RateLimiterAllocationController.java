package com.stellarisapi.project.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.stellarisapi.project.model.dto.rateLimiterAllocation.RateLimiterAllocationQueryRequest;
import com.stellarisapi.project.model.dto.rateLimiterAllocation.RateLimiterAllocationUpdateRequest;
import com.stellarisapi.project.service.RateLimiterAllocationService;
import com.stellarisapi.project.service.UserService;
import com.stellaris.stellarisapicommon.common.BaseResponse;
import com.stellaris.stellarisapicommon.common.DeleteRequest;
import com.stellaris.stellarisapicommon.common.ErrorCode;
import com.stellaris.stellarisapicommon.common.ResultUtils;
import com.stellaris.stellarisapicommon.exception.BusinessException;
import com.stellaris.stellarisapicommon.exception.ThrowUtils;
import com.stellaris.stellarisapicommon.model.dto.RateLimiterAllocationAddRequest;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import com.stellaris.stellarisapicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 图表接口
 *
 * @author sanqi
 */
@RestController
@RequestMapping("/rateLimiterAllocation")
@Slf4j
public class RateLimiterAllocationController {

    @Resource
    private RateLimiterAllocationService rateLimiterAllocationService;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 创建
     *
     * @param rateLimiterAllocationAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addRateLimiterAllocation(@RequestBody RateLimiterAllocationAddRequest rateLimiterAllocationAddRequest, HttpServletRequest request) {
        if (rateLimiterAllocationAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        RateLimiterAllocation rateLimiterAllocation = new RateLimiterAllocation();
        BeanUtils.copyProperties(rateLimiterAllocationAddRequest, rateLimiterAllocation);
        User loginUser = userService.getLoginUser(request);
        rateLimiterAllocation.setCreateUserId(loginUser.getId().toString());
        rateLimiterAllocation.setCreateUserName(loginUser.getUserName());
        // 缓存和实体都同步
        Integer usageStatus = rateLimiterAllocationAddRequest.getUsageStatus();
//        if (usageStatus != 1) {
//
////            ThrowUtils.throwIf(!redisLimiterManager.put(rateLimiterAllocation), ErrorCode.SYSTEM_ERROR);
//        }
        boolean result = rateLimiterAllocationService.save(rateLimiterAllocation);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newRateLimiterAllocationId = rateLimiterAllocation.getId();
        updateGatewayRateLimiter();
        return ResultUtils.success(newRateLimiterAllocationId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteRateLimiterAllocation(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        RateLimiterAllocation oldRateLimiterAllocation = rateLimiterAllocationService.getById(id);
        ThrowUtils.throwIf(oldRateLimiterAllocation == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldRateLimiterAllocation.getCreateUserId().equals(user.getId().toString()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
//        ThrowUtils.throwIf(!redisLimiterManager.removed(oldRateLimiterAllocation), ErrorCode.SYSTEM_ERROR);
        boolean b = rateLimiterAllocationService.removeById(id);
        updateGatewayRateLimiter();
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param rateLimiterAllocationUpdateRequest
     * @return
     */
    @PostMapping("/update")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateRateLimiterAllocation(@RequestBody RateLimiterAllocationUpdateRequest rateLimiterAllocationUpdateRequest) {
        if (rateLimiterAllocationUpdateRequest == null || rateLimiterAllocationUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        RateLimiterAllocation rateLimiterAllocation = new RateLimiterAllocation();
        BeanUtils.copyProperties(rateLimiterAllocationUpdateRequest, rateLimiterAllocation);
        long id = rateLimiterAllocationUpdateRequest.getId();
        // 判断是否存在
        RateLimiterAllocation oldRateLimiterAllocation = rateLimiterAllocationService.getById(id);
        ThrowUtils.throwIf(oldRateLimiterAllocation == null, ErrorCode.NOT_FOUND_ERROR);
        boolean update = false;
        Integer usageStatus = rateLimiterAllocationUpdateRequest.getUsageStatus();
        // 为空处理
        if (usageStatus == 1) {
//            update = redisLimiterManager.removed(oldRateLimiterAllocation);
        }else {
            RateLimiterAllocation newEntity = rateLimiterAllocationUpdateRequest.getNewEntity(oldRateLimiterAllocation);
//            update = redisLimiterManager.update(newEntity);
        }
        ThrowUtils.throwIf(!update, ErrorCode.NOT_FOUND_ERROR);
        boolean result = rateLimiterAllocationService.updateById(rateLimiterAllocation);
        updateGatewayRateLimiter();
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<RateLimiterAllocation> getRateLimiterAllocationById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        RateLimiterAllocation rateLimiterAllocation = rateLimiterAllocationService.getById(id);
        if (rateLimiterAllocation == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(rateLimiterAllocation);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param rateLimiterAllocationQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<RateLimiterAllocation>> listRateLimiterAllocationByPage(@RequestBody RateLimiterAllocationQueryRequest rateLimiterAllocationQueryRequest,
                                                     HttpServletRequest request) {
        long current = rateLimiterAllocationQueryRequest.getCurrent();
        long size = rateLimiterAllocationQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<RateLimiterAllocation> rateLimiterAllocationPage = rateLimiterAllocationService.page(new Page<>(current, size),
                rateLimiterAllocationService.getQueryWrapper(rateLimiterAllocationQueryRequest));
        return ResultUtils.success(rateLimiterAllocationPage);
    }

    private void updateGatewayRateLimiter() {
        HttpResponse execute = HttpRequest.get("http://localhost:8090/actuator/reload").execute();
    }

//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param rateLimiterAllocationQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page")
//    public BaseResponse<Page<RateLimiterAllocation>> listMyRateLimiterAllocationByPage(@RequestBody RateLimiterAllocationQueryRequest rateLimiterAllocationQueryRequest,
//                                                       HttpServletRequest request) {
//        if (rateLimiterAllocationQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        rateLimiterAllocationQueryRequest.setUserId(loginUser.getId());
//        long current = rateLimiterAllocationQueryRequest.getCurrent();
//        long size = rateLimiterAllocationQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<RateLimiterAllocation> rateLimiterAllocationPage = rateLimiterAllocationService.page(new Page<>(current, size),
//                rateLimiterAllocationService.getQueryWrapper(rateLimiterAllocationQueryRequest));
//        return ResultUtils.success(rateLimiterAllocationPage);
//    }
//
//
//    /**
//     * 编辑（用户）
//     *
//     * @param rateLimiterAllocationEditRequest
//     * @param request
//     * @return
//     * @Validated 淡出放这
//     */
//    @PostMapping("/edit")
//    public BaseResponse<Boolean> editRateLimiterAllocation(@RequestBody RateLimiterAllocationEditRequest rateLimiterAllocationEditRequest, HttpServletRequest request) {
//        if (rateLimiterAllocationEditRequest == null || rateLimiterAllocationEditRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        RateLimiterAllocation rateLimiterAllocation = new RateLimiterAllocation();
//        BeanUtils.copyProperties(rateLimiterAllocationEditRequest, rateLimiterAllocation);
//        User loginUser = userService.getLoginUser(request);
//        long id = rateLimiterAllocationEditRequest.getId();
//        // 判断是否存在
//        RateLimiterAllocation oldRateLimiterAllocation = rateLimiterAllocationService.getById(id);
//        ThrowUtils.throwIf(oldRateLimiterAllocation == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可编辑
//        if (!oldRateLimiterAllocation.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        boolean result = rateLimiterAllocationService.updateById(rateLimiterAllocation);
//        return ResultUtils.success(result);
//    }

}
