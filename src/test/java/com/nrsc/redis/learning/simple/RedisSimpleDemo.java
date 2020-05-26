package com.nrsc.redis.learning.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisSimpleDemo {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Test
    public void test1() {
        redisTemplate.multi();
        redisTemplate.opsForValue().set("k1", "Hello Redis1....");
        int i = 100 / 0;
        redisTemplate.opsForValue().set("k2", "Hello Redis2....");
        redisTemplate.exec();
    }


    @Test
    public void test2() {
        stringRedisTemplate.multi();
        stringRedisTemplate.opsForValue().set("k1", "Hello Redis1....");
        int i = 100 / 0;
        stringRedisTemplate.opsForValue().set("k2", "Hello Redis2....");
        stringRedisTemplate.exec();
    }


}
