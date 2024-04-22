package com.stellarisapi.project.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stellaris.stellarisapicommon.model.entity.User;
import com.stellarisapi.project.annotation.AuthCheck;
import com.stellarisapi.project.common.*;
import com.stellarisapi.project.exception.BusinessException;
import com.stellarisapi.project.model.dto.WsensitiveQuery;
import com.stellarisapi.project.model.entity.Wmsensitive;
import com.stellarisapi.project.service.UserService;
import com.stellarisapi.project.service.WmsensitiveService;
import com.stellarisapi.project.utils.SensitiveWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口管理
 */
@RestController
@RequestMapping("/wmsensitiveInfo")
@Slf4j
public class WmsensitiveController {

    @Resource
    private WmsensitiveService wmsensitiveService;

    @Resource
    private UserService userService;

    @PostConstruct
    void initWmsensitive() {
        List<Wmsensitive> wmSensitives = wmsensitiveService.list(Wrappers.<Wmsensitive>lambdaQuery()
                .select(Wmsensitive::getSensitives)
                .isNotNull(Wmsensitive::getSensitives)
                .groupBy(Wmsensitive::getSensitives));
        List<String> sensitiveList = wmSensitives.stream().map(Wmsensitive::getSensitives).collect(Collectors.toList());

        // 初始化敏感词库
        SensitiveWordUtil.initMap(sensitiveList);
    }

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
    public BaseResponse<Long> addInterfaceInfo(@RequestBody WsensitiveQuery wmsensitive) {
        if (StrUtil.isBlank(wmsensitive.getKeyword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Wmsensitive inscriptionWords = new Wmsensitive(wmsensitive.getKeyword());
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
     * @param wsensitiveQuery
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list/page")
    public BaseResponse<Page<Wmsensitive>> listInterfaceInfo(WsensitiveQuery wsensitiveQuery) {
        LambdaQueryWrapper<Wmsensitive> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(wsensitiveQuery.getKeyword()),
                Wmsensitive::getSensitives, wsensitiveQuery.getKeyword());
        Page<Wmsensitive> wsensitiveList = wmsensitiveService.page(new Page<>(wsensitiveQuery.getPageSize(), wsensitiveQuery.getPageSize()), queryWrapper);
        return ResultUtils.success(wsensitiveList);
    }

    @AuthCheck(mustRole = "admin")
    @GetMapping("/map")
    public BaseResponse<Map<String, Object>> listInterfaceInfo() {
        return ResultUtils.success(SensitiveWordUtil.getDictionaryMap());
    }


}
