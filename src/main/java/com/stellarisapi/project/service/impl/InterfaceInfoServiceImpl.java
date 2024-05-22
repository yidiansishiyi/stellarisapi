package com.stellarisapi.project.service.impl;

import com.alibaba.nacos.api.config.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellarisapi.project.common.ErrorCode;
import com.stellarisapi.project.exception.BusinessException;
import com.stellarisapi.project.mapper.InterfaceInfoMapper;
import com.stellarisapi.project.service.InterfaceInfoService;
import com.stellaris.stellarisapicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 接口信息服务实现类
 *
 
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
    
}




