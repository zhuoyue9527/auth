package com.zhuo.springbootredis;

import com.zhuo.springbootredis.entity.User;
import com.zhuo.springbootredis.util.RedisUtil;
import org.junit.jupiter.api.Test;

public class TestRedis {

    @Test
    public  void test(){
        User user = new User();
        user.setAge(18);
        user.setName("卓越");

        //RedisUtil.putValueToHashCache("user","name",user);

    }
}
