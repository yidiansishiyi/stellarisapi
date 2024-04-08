package com.stellarisapi.project.model.dto.rateLimiterAllocation;

import com.stellaris.stellarisapicommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author sanqi
 *   
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RateLimiterAllocationQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private String roadSigns;

    private String rateIntervalUnit;

    private String remark;

    private Integer scene;

    private String state;

    private Integer usageStatus;

    private String createUserName;

    private String createUserId;

    private static final long serialVersionUID = 1L;
}