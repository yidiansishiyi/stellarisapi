package com.stellarisapi.manager;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Component
public class RedisManager {

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    void init() {
        System.out.println("加載測試3");
    }

    /**
     * 随机数唯一保证,保证每一个随机数每一个用户五分
     * 钟内使用一次
     * 使用随机数做 key 用户身份码做值,多随机数相同时使用 ,分割
     * 限制随机数大小上限,防止多用户统一随机数,影响数据库正常
     * @param randomNumber 随机数
     * @param userId 用户标识
     * @param maxLength 最大长度
     * @return 是否成功插入
     */

    // 将随机数存储到 Redis 中，并设置过期时间为两分钟
    public boolean storeRandomNumber(String randomNumber, String userId, int maxLength) {
        RBucket<String> bucket = redissonClient.getBucket(randomNumber);
        String currentValue = bucket.get();
        if (currentValue == null) {
            // 如果当前值为空，则写入自己的标识
            bucket.set(userId, 300, TimeUnit.SECONDS); // 设置过期时间为两分钟
            return true;
        } else {
            // 如果当前值不为空，则判断是否包含自己的标识
            if (currentValue.contains(userId)) {
                return false; // 如果包含自己的标识，则返回 false
            } else {
                // 如果不包含自己的标识，则添加自己的标识到后面
                String newValue = currentValue + "," + userId;
                // 如果长度超过了设定的阈值，则清除字段
                if (newValue.length() > maxLength) {
                    bucket.delete();
                    bucket.set(userId, 300, TimeUnit.SECONDS); // 重新设置过期时间为两分钟
                } else {
                    bucket.set(newValue, 300, TimeUnit.SECONDS); // 重新设置过期时间为两分钟
                }
                return true;
            }
        }
    }

    // 将随机数存储到 Redis 中，并设置过期时间为两分钟
    public void storeRandomNumber(String userId, String randomNumber) {
        RBucket<String> bucket = redissonClient.getBucket(userId);
        bucket.set(randomNumber, 300, TimeUnit.SECONDS); // 5 分钟内唯一
    }

    // 检查随机数是否存在
    public boolean isRandomNumberExist(String userId, String randomNumber) {
        RBucket<String> bucket = redissonClient.getBucket(userId);
        String storedRandomNumber = bucket.get();
        return randomNumber.equals(storedRandomNumber);
    }

}
