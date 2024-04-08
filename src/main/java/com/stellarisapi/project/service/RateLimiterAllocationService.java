package com.stellarisapi.project.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stellarisapi.project.model.dto.rateLimiterAllocation.RateLimiterAllocationQueryRequest;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterKeyInfo;


/**
* @author zeroc
* @description 针对表【rate_limiter_allocation(限流配置)】的数据库操作Service
* @createDate 2024-02-06 12:27:45
*/
public interface RateLimiterAllocationService extends IService<RateLimiterAllocation> {

    QueryWrapper<RateLimiterAllocation> getQueryWrapper(RateLimiterAllocationQueryRequest rateLimiterAllocationQueryRequest);

    RateLimiterAllocation getRateLimiterKeyInfo(RateLimiterKeyInfo rateLimiterKeyInfo);

}
