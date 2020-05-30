package com.nrsc.redis.learning.resp;

import redis.clients.jedis.Jedis;

public class ClientRedis {

    /***
     * 使用redis客户端jedis给6379端口发送消息
     * @param args
     */
    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("name", "lisonLength");
        jedis.close();
    }
}
