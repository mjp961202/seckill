package com.mjp.seckill;

import com.mjp.seckill.utils.redis.RedisService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class SeckillApplicationTests {

    @Resource
    private RedisService redisService;

    @Test
    public void setKey() {
        redisService.setIfAbsent("pro:123","10000",2, TimeUnit.SECONDS);
    }

    @Test
    public void getKey(){
        Object pro = redisService.get("pro");
        System.out.println(pro.toString());
    }
}
