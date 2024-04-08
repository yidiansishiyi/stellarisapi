package com.stellaris.manager;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.stellaris.stellarisapicommon.common.ErrorCode;
import com.stellaris.stellarisapicommon.exception.BusinessException;
import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import com.stellaris.stellarisapicommon.service.RateLimiterAllocationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.*;

import javax.annotation.Resource;
import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 专门提供 RedisLimiter 限流基础服务的（提供了通用的能力）
 */
//@Service
@DubboService
@Slf4j
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    @DubboReference
    private RateLimiterAllocationService rateLimiterAllocationService;


    private Map<String, Map<Integer, Map<String, RateLimiterAllocation>>> rateLimiterAllocationsMap;


    /**
     * 限流操作 sql 配置版本
     *
     * @param key 区分不同的限流器，比如不同的用户 id 应该分别统计
     */
    public void interceptionAndCurrentLimiting(RateLimiterKeyInfo rateLimiterKeyInfo) {
        Integer scene = rateLimiterKeyInfo.getScene();
        String key = getRateLimiterKey(rateLimiterKeyInfo);

        Map<String, RateLimiterAllocation> initOriginal = rateLimiterAllocationsMap.get("正常").get(scene);

        RRateLimiter rateLimiter = null;
        if (initOriginal.containsKey(rateLimiterKeyInfo.getUrl())) {
            rateLimiter = redissonClient.getRateLimiter(key);
            if (!rateLimiter.isExists()) {
                String url = rateLimiterKeyInfo.getUrl();
                RateLimiterAllocation rateLimiterAllocation = initOriginal.get(url);
                rateLimiter.trySetRate(RateType.OVERALL, rateLimiterAllocation.getRate(),
                        rateLimiterAllocation.getRateInterval(),
                        getRateTimeUnit(rateLimiterAllocation.getRateIntervalUnit()));
                rateLimiter.expire(72, TimeUnit.HOURS);
            }
        }
//        RRateLimiter rateLimiter = (RRateLimiter) cacheManager.get(key);
//        // 如果不包含该限流器,去配置表内拉去限流器
//        if (!cacheManager.containsKey(key)) {
//            synchronized (RedisLimiterManager.class) {
//                if (!cacheManager.containsKey(key)) {
//                    // 如果已有限流器不存在,根据注解创新的限流器加入到类中
//                    rateLimiter = redissonClient.getRateLimiter(key);
//                    rateLimiter.delete();
//                    // 设置限流器超时时间,清理 redis 内数据该方法不推荐使用,RExpirable
//                    rateLimiter.expire(72, TimeUnit.HOURS);
//                    String url = rateLimiterKeyInfo.getUrl();
//                    // 初始使用正常状态的限流参数限流器
//                    // 当提供方接口数据异常之后提取对应状态参数创建限流器更新 cacheManager
//                    Map<String, RateLimiterAllocation> initOriginal = rateLimiterAllocationsMap.get("正常").get(scene);
//                    if (initOriginal.containsKey(url)) {
//                        RateLimiterAllocation rateLimiterAllocation = initOriginal.get(url);
//                        rateLimiter.trySetRate(RateType.OVERALL, rateLimiterAllocation.getRate(),
//                                rateLimiterAllocation.getRateInterval(),
//                                getRateTimeUnit(rateLimiterAllocation.getRateIntervalUnit()));
//                        cacheManager.put(key, rateLimiter);
//                    } else {
//                        rateLimiter = null;
//                    }
//                }
//            }
//        }
        if (rateLimiter == null) {
            return;
        }
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }

    /**
     * 获取限流器时间单位
     *
     * @param rateIntervalUnit
     * @return
     */
    private static RateIntervalUnit getRateTimeUnit(String rateIntervalUnit) {
        switch (rateIntervalUnit.toUpperCase()) {
            case "MILLISECONDS":
                return RateIntervalUnit.MILLISECONDS;
            case "SECONDS":
                return RateIntervalUnit.SECONDS;
            case "MINUTES":
                return RateIntervalUnit.MINUTES;
            case "HOURS":
                return RateIntervalUnit.HOURS;
            case "DAYS":
                return RateIntervalUnit.DAYS;
            default:
                throw new IllegalArgumentException("Unsupported rate interval unit: " + rateIntervalUnit);
        }
    }

    /**
     * 获取限流 key
     *
     * @param rateLimiterKeyInfo 限流维度类
     * @return redission 限流 key
     */
    private static String getRateLimiterKey(RateLimiterKeyInfo rateLimiterKeyInfo) {
        String rateLimiterKey = rateLimiterKeyInfo.getUrl() + ":"
                + rateLimiterKeyInfo.getScene() + ":";
        String userSign = rateLimiterKeyInfo.getUserSign();
        if (CharSequenceUtil.isNotBlank(userSign)) {
            rateLimiterKey += userSign;
        }
        return rateLimiterKey;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RateLimiterKeyInfo implements Serializable {

        /**
         * 请求路径
         */
        private String url;

        /**
         * 用户唯一标识
         */
        private String userSign;

        /**
         * 请求方式
         */
        private String requestMethod;

        /**
         * 限流场景 1.接口限流 2.用户维度限流
         */
        private Integer scene;

    }

    /**
     * 限流操作注解版本
     *
     * @param key 区分不同的限流器，比如不同的用户 id 应该分别统计
     */
    public void doRateLimit(String key, long value, long duration) {
            // 如果已有限流器不存在,根据注解创新的限流器加入到类中
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
            if (rateLimiter.isExists()) {
                rateLimiter.trySetRate(RateType.OVERALL, value, duration, RateIntervalUnit.SECONDS);
            }
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }
}
