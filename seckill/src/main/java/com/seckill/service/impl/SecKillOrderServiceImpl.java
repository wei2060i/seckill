package com.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.seckill.beans.dto.SecKillStatusListDto;
import com.seckill.beans.po.SecKillGoods;
import com.seckill.beans.po.SecKillOrder;
import com.seckill.dao.SecKillOrderDao;
import com.seckill.service.ISecKillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@Service
public class SecKillOrderServiceImpl extends ServiceImpl<SecKillOrderDao, SecKillOrder> implements ISecKillOrderService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void addOrder(Long goodsId, Long uid) {
        //incr uid 自增1  并发请求也不可能返回多个值是一样的,因为redis单线程
        Long userQueueCount = redisTemplate.boundHashOps("userQueueCount").increment(uid.toString(), 1);
        if (userQueueCount > 1) {
            throw new RuntimeException("重复排队");
        }
        //排队信息封装
        SecKillStatusListDto statusListDto = new SecKillStatusListDto(uid, LocalDateTime.now(), 1, goodsId);
        //将秒杀抢单信息存入到Redis中,这里采用List方式存储,List本身是一个队列
        redisTemplate.boundListOps("secKillOrderQueue").leftPush(statusListDto);
        //用户抢单状态
        redisTemplate.boundHashOps("userQueueStatus").put(uid.toString(), statusListDto);
        //多线程操作
        this.asyncAddOrder();
    }

    @Async
    public void asyncAddOrder() {
        SecKillStatusListDto secKillOrderQueue = (SecKillStatusListDto) redisTemplate.boundListOps("secKillOrderQueue").rightPop();
        if (secKillOrderQueue == null) {
            return;
        }
        Long goodsId = secKillOrderQueue.getGoodsId();
        Long uid = secKillOrderQueue.getUid();
        //商品库存-1
        Long surplusCount = redisTemplate.boundHashOps("secKillGoodsIncrement").increment(goodsId, -1);//商品数量递减
        if (surplusCount == null) {
            return;
        }
        if (surplusCount <= 0) {
            //此商品没有库存
            //并且将商品数据同步到MySQL中
            //如果没有库存,则清空Redis缓存中该商品
            redisTemplate.boundHashOps("secKillGoods").delete(goodsId.toString());

            redisTemplate.boundHashOps("userQueueCount").delete(uid.toString());
            redisTemplate.boundHashOps("userQueueStatus").delete(uid.toString());
            return;
        }

        //查询秒杀 商品
        SecKillGoods secKillGoods = (SecKillGoods) redisTemplate.boundHashOps("secKillGoods").get(goodsId.toString());
        if (secKillGoods == null || secKillGoods.getStockCount() <= 0) {
            throw new RuntimeException("已经卖完");
        }
        SecKillOrder order = new SecKillOrder()
                .setId(IdWorker.getId())
                .setUserId(uid)
                .setSecKillId(goodsId)
                .setMoney(secKillGoods.getCostPrice())
                .setCreateTime(LocalDateTime.now())
                .setStatus("0");
        /**
         * 将订单对象存储起来
         * 1.一个用户只允许有一个未支付秒杀订单2.订单存入到Redis
         * Hash
         * namespace->secKillOrder
         *            uid : secKillOrder
         */
        //将秒杀订单存入到Redis中
        redisTemplate.boundHashOps("secKillOrder").put(uid.toString(), order);

        //判断当前商品是否还有库存
        //if (secKillGoods.getStockCount() <= 0) {
        //库存减少
        secKillGoods.setStockCount(surplusCount.intValue());
        //如果有库存，则直数据重置到Redis中
        redisTemplate.boundHashOps("secKillGoods").put(goodsId.toString(), secKillGoods);


        SecKillStatusListDto userQueueStatus = (SecKillStatusListDto) redisTemplate.boundHashOps("userQueueStatus").get(uid.toString());
        userQueueStatus.setOrderId(order.getId());
        userQueueStatus.setStatus(2);
        userQueueStatus.setMoney(order.getMoney());

        //用户抢单状态 更新
        redisTemplate.boundHashOps("userQueueStatus").put(uid.toString(), userQueueStatus);
    }

}