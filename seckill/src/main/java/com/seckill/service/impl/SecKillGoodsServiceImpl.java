package com.seckill.service.impl;

import com.seckill.beans.po.SecKillGoods;
import com.seckill.dao.SecKillGoodsDao;
import com.seckill.service.ISecKillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@Service
public class SecKillGoodsServiceImpl extends ServiceImpl<SecKillGoodsDao, SecKillGoods> implements ISecKillGoodsService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<SecKillGoods> getSecKillGoods() {
        return redisTemplate.boundHashOps("secKillGoods").values();
    }

}
