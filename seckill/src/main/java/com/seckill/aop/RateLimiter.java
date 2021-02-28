package com.seckill.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     * key–表示限流模块名，指定该值用于区分不同应用，不同场景
     * 推荐格式为：应用名:模块名:ip:接口名:方法名
     * @return
     */
    String key() default "rate:limiter";

    /**
     * 单位时间限制通过请求数
     */
    long limit() default 10;

    /**
     * 过期时间,单位秒
     * expire–incr的值的过期时间
     * 业务中表示限流的单位时间。
     */
    long expire() default 1;
}
