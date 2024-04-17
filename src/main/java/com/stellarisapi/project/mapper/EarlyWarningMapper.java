package com.stellarisapi.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stellarisapi.project.model.entity.EarlyWarning;

import java.util.List;
import java.util.Map;

/**
    * @author zeroc
    * @description 针对表【early_warning(接口预警记录表)】的数据库操作Mapper
    * @createDate 2024-04-16 23:10:54
    * @Entity com.stellarisapi.project.model.entity.EarlyWarning
    */
public interface EarlyWarningMapper extends BaseMapper<EarlyWarning> {
    List<Map<String,Object>> getWarningInformation();
}