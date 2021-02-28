package com.seckill.controller;


import com.seckill.service.ISecKillOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/secKillOrder")
public class SecKillOrderController {
    @Resource
    private ISecKillOrderService secKillOrderService;

    @GetMapping("add/{goodsId}")
    public String addOrder(@PathVariable Long goodsId) {
        Long uid = 110L;
        secKillOrderService.addOrder(goodsId, uid);
        return "suc";
    }

}