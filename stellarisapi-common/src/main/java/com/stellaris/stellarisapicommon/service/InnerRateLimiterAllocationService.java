package com.stellaris.stellarisapicommon.service;

import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;

import java.util.Map;

public interface InnerRateLimiterAllocationService {
    Map<String, Map<Integer, Map<String, RateLimiterAllocation>>> rateLimiterAllocationsMap();
}