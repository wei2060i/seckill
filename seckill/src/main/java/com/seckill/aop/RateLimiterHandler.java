package com.seckill.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Aspect
@Component
public class RateLimiterHandler {
    Logger logger = LoggerFactory.getLogger(RateLimiterHandler.class);
    @Resource
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/rateLimiter.lua")));
        logger.info("脚本加载完成");
    }

    @Pointcut("@annotation(rateLimiter)")
    public void RateLimiterPoint(RateLimiter rateLimiter) {
    }

    @Around(value = "RateLimiterPoint(rateLimiter)", argNames = "proceedingJoinPoint,rateLimiter")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RateLimiter rateLimiter) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("RateLimiterHandler[分布式限流处理器]开始执行限流操作");
        }
        Signature signature = proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the Annotation @RateLimiter must used on method");
        }
        //限流模块key
        String key = rateLimiter.key();
        //限流阈值
        long limitNum = rateLimiter.limit();
        //超时时间
        long expire = rateLimiter.expire();
        if (logger.isDebugEnabled()) {
            logger.debug("RateLimiterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}", limitNum, expire);
        }
        //执行脚本
        List<String> keyList = new ArrayList<>();
        //设置key值为注解中的值
        keyList.add(key);
        /**
         * 脚本执行
         */
        Long result = (Long) redisTemplate.execute(getRedisScript, keyList, expire, limitNum);
        System.out.println("限流" + result);
        if (result == 0) {
            String msg = "由于超过单位时间=" + expire + "-允许的请求次数=" + limitNum + "[触发限流]";
            logger.debug(msg);
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("RateLimiterHandler[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
        }
        return proceedingJoinPoint.proceed();
    }


}
