package com.nrsc.redis.learning.pipeline;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JedisTest {


    public static void main(String[] args) {
        RedisTools.initRedisData();
        long t = System.currentTimeMillis();
        delNoPipe(RedisTools.keys);
        //delNoStus(RedisTools.keys);

        System.out.println(System.currentTimeMillis() - t);
    }

    public static void delNoStus(String... keys) {
        Jedis jedis = new Jedis(RedisTools.ip, RedisTools.port);
        //在循环中进行批量删除
        for (String key : keys) {
            jedis.del(key);
        }
        jedis.close();
    }

    //使用pipeline方式进行删除
    public static void delNoPipe(String... keys) {
        Jedis jedis = new Jedis(RedisTools.ip, RedisTools.port);
        Pipeline pipelined = jedis.pipelined();
        for (String key : keys) {
            pipelined.del(key);//只是封装命令但未提交
        }
        pipelined.sync();//一次性提交
        jedis.close();
    }
}
