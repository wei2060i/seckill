package com.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SecKillApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        List<String> keys = new ArrayList<>();
        keys.add("as");
        DefaultRedisScript<Long> getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptText(
                "local key1 = KEYS[1]" +
                        "redis.call('incr', key1)" +
                        "redis.call('expire', key1, tonumber(-1))" +
                        "return redis.call('get', key1)");

        Long result = (Long) redisTemplate.execute(getRedisScript,
                keys);
        System.out.println(result);
    }

}
