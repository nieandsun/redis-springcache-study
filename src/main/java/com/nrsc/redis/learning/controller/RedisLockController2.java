package com.nrsc.redis.learning.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisLockController2 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/deduct-stock2")
    public String DeductStock() {
        String LOCK_KEY = "deduct-stock-lock";
        String LOCK_VALUE = "deduct-stock-value";

        //尝试加锁
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY, LOCK_VALUE);

        //加锁失败
        if (!flag) {
            System.out.println("秒杀失败，请重试！！！");
        }

        //加锁成功
        if (flag) {
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
        }

        //释放锁
        Boolean delete = stringRedisTemplate.delete(LOCK_KEY);
        System.out.println("删除" + LOCK_KEY + ":" + delete); //打印日志
        return "ok！！！";
    }
}
