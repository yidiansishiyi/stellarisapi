package com.stellarisapi.project.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stellaris.stellarisapicommon.model.entity.InterfaceInfo;
import com.stellaris.stellarisapicommon.model.entity.User;
import com.stellarisapi.project.annotation.AuthCheck;
import com.stellarisapi.project.common.*;
import com.stellarisapi.project.constant.CommonConstant;
import com.stellarisapi.project.exception.BusinessException;
import com.stellarisapi.project.model.dto.WsensitiveQuery;
import com.stellarisapi.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.stellarisapi.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.stellarisapi.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.stellarisapi.project.model.entity.Wmsensitive;
import com.stellarisapi.project.model.enums.InterfaceInfoStatusEnum;
import com.stellarisapi.project.service.InterfaceInfoService;
import com.stellarisapi.project.service.UserService;
import com.stellarisapi.project.service.WmsensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口管理
 *

 */
@RestController
@RequestMapping("/wmsensitiveInfo")
@Slf4j
public class WmsensitiveController {

    @Resource
    private WmsensitiveService wmsensitiveService;

    @Resource
    private UserService userService;

//    @Resource
//    private stellarisapiClient stellarisapiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param wmsensitive
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> addInterfaceInfo(String wmsensitive,HttpServletRequest request) {
        if (StrUtil.isBlank(wmsensitive)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Wmsensitive inscriptionWords = new Wmsensitive(wmsensitive);
        wmsensitiveService.save(inscriptionWords);
        long newInterfaceInfoId = inscriptionWords.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Wmsensitive wmsensitive = wmsensitiveService.getById(id);
        if (wmsensitive == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 管理员可删除
        boolean b = wmsensitiveService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
//    @AuthCheck(mustRole = "admin")
//    @GetMapping("/list")
//    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(WsensitiveQuery wsensitiveQuery) {
//        Wmsensitive wmsensitive = new Wmsensitive();
////        if (wsensitiveQuery != null) {
////            wmsensitive.setSensitives();
////        }
//        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
//        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
//        return ResultUtils.success(interfaceInfoList);
//    }


}
