package com.stellarisapi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stellaris.stellarisapicommon.model.entity.UserInterfaceInfo;
import com.stellarisapi.project.service.UserInterfaceInfoService;
import com.stellaris.stellarisapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部用户接口信息服务实现类
 *7
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public boolean thereIsALimit(long userId) {
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(UserInterfaceInfo::getLeftNum,0)
                .eq(UserInterfaceInfo::getUserId,userId)
                .eq(UserInterfaceInfo::getStatus,0);
        UserInterfaceInfo one = userInterfaceInfoService.getOne(queryWrapper);
        return one != null;
    }
}
