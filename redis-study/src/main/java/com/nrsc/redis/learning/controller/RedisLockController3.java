package com.nrsc.redis.learning.controller;


import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisLockController3 {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Redisson redisson;

    @GetMapping("/deduct-stock3")
    public String DeductStock() {
        String LOCK_KEY = "deduct-stock-lock";
        RLock redissonLock = redisson.getLock(LOCK_KEY);
        //加锁成功
        try {
            redissonLock.lock();
            //查看数据库中是否有库存
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            //如果有库存则购买一个商品
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存:" + realStock);
            } else {
                //如果没有库存，则扣减失败
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            redissonLock.unlock();
            System.out.println("成功释放锁"); //打印日志
        }

        return "ok！！！";
    }
}
