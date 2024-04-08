package com.stellaris.stellarisapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName rate_limiter_allocation
 */
@TableName(value ="rate_limiter_allocation")
@Data
public class RateLimiterAllocation implements Serializable {
    private Long id;

    private String roadSigns;

    private Long rate;

    private Long rateInterval;

    private String rateIntervalUnit;

    private String createUserId;

    private String createUserName;

    private String remark;

    private Integer scene;

    private String state;

    private Integer usageStatus;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;

}