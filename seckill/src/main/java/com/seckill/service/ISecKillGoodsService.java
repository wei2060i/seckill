package com.seckill.service;

import com.seckill.beans.po.SecKillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
public interface ISecKillGoodsService extends IService<SecKillGoods> {

    List<SecKillGoods> getSecKillGoods();

}
