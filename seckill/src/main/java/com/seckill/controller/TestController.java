package com.seckill.controller;

import com.seckill.aop.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @RateLimiter(key = "limiter:1.0.0", limit = 20, expire = -1)
    public String test() {
        return "hello";
    }

    public static void main(String[] args) {

    }
}
