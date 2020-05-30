package com.nrsc.redis.learning.resp;

import java.io.IOException;
import java.net.Socket;

public class SelfRedisClient {
    /*
    *3
    $3
    SET
    $4
    name
    $6
    rehash
     */
    public static String set(Socket socket, String key, String value) throws IOException {
        //按照RESP协议拼接字符串
        StringBuffer str = new StringBuffer();
        str.append("*3").append("\r\n");
        str.append("$3").append("\r\n");
        str.append("SET").append("\r\n");
        str.append("$").append(key.getBytes().length).append("\r\n");
        str.append(key).append("\r\n");
        str.append("$").append(value.getBytes().length).append("\r\n");
        str.append(value).append("\r\n");

        socket.getOutputStream().write(str.toString().getBytes());
        byte[] response = new byte[2048];
        socket.getInputStream().read(response);
        return new String(response);

    }

    /*
    *2
    $3
    GET
    $4
    name
     */
    public static String get(Socket socket, String key) throws IOException {
        //按照RESP协议拼接字符串
        StringBuffer str = new StringBuffer();
        str.append("*2").append("\r\n");
        str.append("$3").append("\r\n");
        str.append("GET").append("\r\n");
        str.append("$").append(key.getBytes().length).append("\r\n");
        str.append(key).append("\r\n");
        socket.getOutputStream().write(str.toString().getBytes());
        byte[] response = new byte[2048];
        socket.getInputStream().read(response);
        return new String(response);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6379);
        String set = set(socket, "shaka", "loveStus");
        System.out.println(set);
        System.out.println(get(socket, "shaka"));
    }
}
