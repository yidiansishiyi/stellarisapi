package com.stellarisapi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellarisapi.project.mapper.RateLimiterAllocationMapper;
import com.stellarisapi.project.model.dto.rateLimiterAllocation.RateLimiterAllocationQueryRequest;
import com.stellarisapi.project.service.RateLimiterAllocationService;
import com.stellarisapi.project.utils.SqlUtils;
import com.stellaris.stellarisapicommon.constant.CommonConstant;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterKeyInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author zeroc
* @description 针对表【rate_limiter_allocation(限流配置)】的数据库操作Service实现
* @createDate 2024-02-06 12:27:45
*/
@Service
public class RateLimiterAllocationServiceImpl extends ServiceImpl<RateLimiterAllocationMapper, RateLimiterAllocation>
    implements RateLimiterAllocationService {

    @Override
    public QueryWrapper<RateLimiterAllocation> getQueryWrapper(RateLimiterAllocationQueryRequest rateLimiterAllocationQueryRequest) {
        QueryWrapper<RateLimiterAllocation> queryWrapper = new QueryWrapper<>();
        if (rateLimiterAllocationQueryRequest == null) {
            return queryWrapper;
        }
        Long id = rateLimiterAllocationQueryRequest.getId();
        String roadSigns = rateLimiterAllocationQueryRequest.getRoadSigns();
        String rateIntervalUnit = rateLimiterAllocationQueryRequest.getRateIntervalUnit();
        String remark = rateLimiterAllocationQueryRequest.getRemark();
        Integer scene = rateLimiterAllocationQueryRequest.getScene();
        String state = rateLimiterAllocationQueryRequest.getState();
        Integer usageStatus = rateLimiterAllocationQueryRequest.getUsageStatus();
        String createUserName = rateLimiterAllocationQueryRequest.getCreateUserName();
        String createUserId = rateLimiterAllocationQueryRequest.getCreateUserId();
        String sortField = rateLimiterAllocationQueryRequest.getSortField();
        String sortOrder = rateLimiterAllocationQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(roadSigns), "roadSigns", roadSigns);
        queryWrapper.eq(StringUtils.isNotBlank(state), "state", state);
        queryWrapper.eq(ObjectUtils.isNotEmpty(scene), "scene", scene);
        queryWrapper.eq(ObjectUtils.isNotEmpty(scene), "scene", scene);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public RateLimiterAllocation getRateLimiterKeyInfo(RateLimiterKeyInfo rateLimiterKeyInfo) {
        
        return null;
    }
}




