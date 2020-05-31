package com.nrsc.redis.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class LuaDemoController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/lua-limit")
    public String LuaDemo() {
        // 构造RedisScript
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定要使用的lua脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-lua/ipcount.lua")));
        //指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Arrays.asList("127.0.0.1"), 5, 2);
        log.info("是否获可以访问:{}", result == 1 ? "是" : "否");
        return "OK";
    }


    @GetMapping("/lua-demo")
    public String LuaDemo2() {
        //lua脚本
        String script = "local key1 = KEYS[1]\n" +
                "local key2 = KEYS[2]\n" +
                "local arg1 = ARGV[1]\n" +
                "local arg2 = tonumber(ARGV[2])\n" +
                "\n" +
                "redis.call(\"SET\", key1, arg1)\n" +
                "redis.call(\"lpush\",key2,arg2)\n" +
                "\n" +
                "return 1";

        // 构造RedisScript并指定返回类类型
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Arrays.asList("name111", "age111"), "yoyo111", 19);
        System.out.println(result);
        return "OK";
    }
}