package com.nrsc.redis.learning.demo;

import com.nrsc.redis.learning.domain.User;
import com.nrsc.redis.learning.untils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisSimpleTest {


    @Test
    public void stringTest() {


        User u1 = new User("yoyo", 18, "M");
        User u2 = new User("Lucy", 18, "M");
        User u3 = new User("mike", 18, "M");

        boolean yoyo = RedisUtils.set("yoyo", u1);
        User yoyo1 = (User) RedisUtils.get("yoyo");

        List<User> users = Arrays.asList(u1, u2, u3);

        boolean userList = RedisUtils.set("userList", users);

        List<User> userList1 = (List<User>) RedisUtils.get("userList");


        System.out.println(111);


    }


}
