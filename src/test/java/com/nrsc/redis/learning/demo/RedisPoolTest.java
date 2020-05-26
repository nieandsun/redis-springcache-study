package com.nrsc.redis.learning.demo;

import com.nrsc.redis.learning.domain.User;
import com.nrsc.redis.learning.untils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisPoolTest {

    static class SetValue implements Runnable {
        public CountDownLatch latch;

        public SetValue(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            User u1 = new User("yoyo", 18, "M");
            User u2 = new User("Lucy", 18, "M");
            User u3 = new User("mike", 18, "M");

            try {
                log.info("线程{}先等待一下", Thread.currentThread().getName());
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程{}开始插入", Thread.currentThread().getName());
            boolean flag = RedisUtils.set("yoyo", u1);

            log.info("插入{}", flag == true ? "成功" : "失败");
        }
    }


    @Test
    public void connectionTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 4; i++) {
            new Thread(new SetValue(latch), "线程" + i).start();
        }

        TimeUnit.SECONDS.sleep(3);
        latch.countDown();

        //log.info("1111");
    }


}
