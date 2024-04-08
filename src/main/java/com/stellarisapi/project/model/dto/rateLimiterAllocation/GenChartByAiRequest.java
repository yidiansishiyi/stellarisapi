package com.stellarisapi.project.model.dto.rateLimiterAllocation;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 * <p>
 * sanqi
 *
 *   
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}