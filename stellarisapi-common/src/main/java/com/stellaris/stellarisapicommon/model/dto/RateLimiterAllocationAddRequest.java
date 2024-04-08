package com.stellaris.stellarisapicommon.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author sanqi
 *   
 */
@Data
public class RateLimiterAllocationAddRequest implements Serializable {

    private String roadSigns;

    private Long rate;

    private Long rateInterval;

    private String rateIntervalUnit;

    private String remark;

    private Integer scene;

    private String state;

    private Integer usageStatus;

    private static final long serialVersionUID = 1L;

}