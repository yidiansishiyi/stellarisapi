package com.stellarisapi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stellarisapi.project.model.entity.EarlyWarning;

/**
* @author zeroc
* @description 针对表【early_warning(接口预警记录表)】的数据库操作Service
* @createDate 2024-04-16 23:10:54
*/
public interface EarlyWarningService extends IService<EarlyWarning> {
    void sendWarningMessage();

    void validEarlyWarning(EarlyWarning earlyWarning, boolean b);
}
