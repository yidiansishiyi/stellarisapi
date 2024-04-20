package com.stellarisapi.adapter;

import com.stellarisapi.model.dto.RequestAdapterDTO;

import java.util.Map;

/**
 * 请求参数转换器
 */
public interface ParameterAdapter {
    /**
     * 转换请求参数
     *
     * @param originalParameters 原始入参
     * @return
     */
    RequestAdapterDTO parameterAdapter(Map<String, Object> originalParameters);
}

