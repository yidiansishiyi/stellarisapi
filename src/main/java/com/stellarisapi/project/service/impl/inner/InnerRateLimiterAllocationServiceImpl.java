package com.stellarisapi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import com.stellaris.stellarisapicommon.service.InnerRateLimiterAllocationService;
import com.stellarisapi.project.service.RateLimiterAllocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DubboService
@Slf4j
public class InnerRateLimiterAllocationServiceImpl implements InnerRateLimiterAllocationService {

    @Resource
    private RateLimiterAllocationService rateLimiterAllocationService;

    @Override
    public Map<String, Map<Integer, Map<String, RateLimiterAllocation>>> rateLimiterAllocationsMap() {
        // 假设rateLimiterAllocations已经被填充了数据
        LambdaQueryWrapper<RateLimiterAllocation> qw = new LambdaQueryWrapper<>();
        qw.eq(RateLimiterAllocation::getUsageStatus, "0");

        List<RateLimiterAllocation> rateLimiterAllocations = rateLimiterAllocationService.list(qw);

        // 按照state和roadSigns将RateLimiterAllocation放入Map中
        log.info("限流规则加载成功: " + rateLimiterAllocations.size() + "条");
        return  rateLimiterAllocations.stream()
                .collect(Collectors.groupingBy(
                        RateLimiterAllocation::getState,
                        Collectors.groupingBy(
                                RateLimiterAllocation::getScene,
                                Collectors.toMap(
                                        RateLimiterAllocation::getRoadSigns,
                                        allocation -> allocation,
                                        (existing, replacement) -> replacement
                                )
                        )
                ));
    }
}
