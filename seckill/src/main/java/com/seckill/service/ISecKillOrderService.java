package com.seckill.service;

import com.seckill.beans.po.SecKillOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
public interface ISecKillOrderService extends IService<SecKillOrder> {
    /**
     * 下单
     *
     * @param goodsId
     * @param uid
     */
    void addOrder(Long goodsId, Long uid);

}
