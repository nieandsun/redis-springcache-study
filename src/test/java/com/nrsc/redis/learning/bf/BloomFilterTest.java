package com.nrsc.redis.learning.bf;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BloomFilterTest {
    private static final int insertions = 100000000;

    @Test
    public void bfTest() {
        //初始化一个存储String数据的布隆过滤器，初始化大小100W
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insertions,0.0001);


        //初始化一个存储String数据的set，初始化大小100w
        //Set<String> sets = new HashSet<>(insertions);
        Set<String> sets = Sets.newHashSetWithExpectedSize(insertions); //用guava进行new HashSet() 的方式，与上面的代码一个意思

        //初始化一个存储String数据的set，初始化大小100w
        //List<String> lists = new ArrayList<String>(insertions);
        ArrayList<String> lists = Lists.newArrayListWithCapacity(insertions);//用guava进行new HashSet() 的方式，与上面的代码一个意思

        //向三个容器初始化100万个随机并且唯一的字符串 , 100万个uuid 34M多
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }
        int wrong = 0;//布隆过滤器错误判断的次数
        int right = 0;//布隆过滤器正确判断的次数

        /****
         * 相信你耷眼一看就知道，这10000次循环里，会有100个肯定是在布隆过滤器里存在的
         * 剩下10000 - 100个肯定是不在布隆过滤器里的
         */
        for (int i = 0; i < 10000; i++) {

            String test = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();//按照一定比例选择bf中肯定存在的值

            if (bf.mightContain(test)) {
                if (sets.contains(test)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        System.out.println("=============right=============" + right);
        System.out.println("=============误判率=============" + wrong / 10000.0);
    }
}