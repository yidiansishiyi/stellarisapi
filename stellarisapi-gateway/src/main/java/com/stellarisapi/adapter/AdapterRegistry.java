package com.stellarisapi.adapter;

import com.stellarisapi.model.enums.ParameterAdapterEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数转换器注册表
 *
 * @author sanqi
 */
@Component
public class AdapterRegistry {

    @Resource
    private ZelinAiParameterAdapter zelinAiAdapter;

    private Map<Long,ParameterAdapter> typeParameterAdapterMap;

    /**
     * 初始化注册表
     */
    @PostConstruct
    public void doInit(){
        typeParameterAdapterMap = new HashMap(){{
            put(ParameterAdapterEnum.ZELINAI.getValue(),zelinAiAdapter);
        }};
    }

    /**
     * 根据 type 获取对应数据适配器
     * @param type 前端传入类型
     * @return 对应数据源实例
     */
    public ParameterAdapter getDataSourceByType(Long type) {
        return typeParameterAdapterMap == null ? null : typeParameterAdapterMap.get(type);
    }
}
