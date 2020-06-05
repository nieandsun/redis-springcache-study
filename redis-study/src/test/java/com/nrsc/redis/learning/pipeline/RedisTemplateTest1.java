package com.nrsc.redis.learning.pipeline;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * 这种方式有问题
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTemplateTest1 {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private int arrayLength = 10000;
    private String[] keys = new String[arrayLength];
    private List<String> keys2 = Lists.newArrayList();

    @Test
    public void test() {
        Map<String, String> map = initData();
        long t = System.currentTimeMillis();
        setPipe(map);
        getPipe(keys);

        System.out.println(System.currentTimeMillis() - t);
    }

    /***
     * 初始化数据
     * @return
     */
    public Map<String, String> initData() {
        Map<String, String> map = Maps.newHashMap();

        for (int i = 0; i < arrayLength; i++) {
            String key = "key:" + i;
            String value = "value:" + i;
            map.put(key, value);
            keys[i] = key;
            keys2.add(key);
        }
        return map;
    }


    public void setPipe(Map<String, String> map) {

        List list = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();

                connection.set(next.getKey().getBytes(), next.getValue().getBytes());
            }
            return null;
        });

        System.out.println("setPipe" + list);
    }


    public void getPipe(String... keys) {
        List<Object> list = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            for (String s : keys) {
                connection.get(s.getBytes());
            }
            return null;
        });

        System.out.println("pipeline获取结果" + list);

        List<Object> list1 = redisTemplate.opsForValue().multiGet(keys2);
        System.out.println("multiGet获取结果" + list);
    }


}
