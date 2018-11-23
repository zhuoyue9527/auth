package com.zhuo.springbootredis;

import com.zhuo.springbootredis.entity.User;
import com.zhuo.springbootredis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

    @Autowired
    private  RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setAge(18);
        user.setName("卓越");

        redisUtil.putValueToHashCache("user","name",user);
    }

}
