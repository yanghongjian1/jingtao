package com.jingtao.jtmanage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JtManageApplicationTests {

    @Test
    public void contextLoads() {

//        //从Redis中，通过Key获取信息
//        String redisExample = stringRedisTemplate.opsForValue().get("redisExample");
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.159.100", 6379);

        jedis.set("1801班", "1801");

        System.out.println("获取redis的数据:" + jedis.get("1801班"));

    }


    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.159.100", 6379);

        jedis.set("1801班", "1801");

        System.out.println("获取redis的数据:" + jedis.get("1801班"));

    }

}

