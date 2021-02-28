package com.seckill.controller;


import com.seckill.beans.po.SecKillGoods;
import com.seckill.service.ISecKillGoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/secKillGoods")
public class SecKillGoodsController {

    @Resource
    private ISecKillGoodsService secKillGoodsService;

    @GetMapping("list")
    public List<SecKillGoods> getSecKillGoods() {
        return secKillGoodsService.getSecKillGoods();
    }

}

