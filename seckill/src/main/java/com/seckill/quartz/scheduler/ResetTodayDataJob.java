package com.seckill.quartz.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seckill.beans.po.LnScheduler;
import com.seckill.beans.po.SecKillGoods;
import com.seckill.quartz.core.CircleList;
import com.seckill.quartz.task.PushTask;
import com.seckill.service.ILnSchedulerService;
import com.seckill.service.ISecKillGoodsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class ResetTodayDataJob {

    @Resource
    private ILnSchedulerService lnSchedulerService;
    @Resource
    private ISecKillGoodsService seckillGoodsService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 每4秒执行一次
     */
    //@Scheduled(cron = "0/4 * * * * ?")
    public void pushTask() {
        //从mysql获取 [0-7]秒 内的所有scheduler
        CircleList.noTaskSchedulers = lnSchedulerService.list(new QueryWrapper<LnScheduler>()
                .lambda()
                .eq(LnScheduler::getDel, false)
                .eq(LnScheduler::getTasking, false)
                .lt(LnScheduler::getExecuteTime, LocalDateTime.now().plusSeconds(8)));
        //启动push task 线程
        new PushTask().start();
    }

    //@Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodPushRedis() {
        /**
         * 审核通过  剩余库存大于0  时间 等等
         */
        //排除已经存在的key
        Set<Object> keys = redisTemplate.boundHashOps("秒杀商品0").keys();

        List<SecKillGoods> list = seckillGoodsService.list(new QueryWrapper<SecKillGoods>().lambda()
                .eq(SecKillGoods::getStatus, 1)
                .gt(SecKillGoods::getStockCount, 0)
                .notIn(keys != null && keys.size() > 0, SecKillGoods::getId, keys));

        for (SecKillGoods seckillGoods : list) {
            redisTemplate.boundHashOps("secKillGoods").put(seckillGoods.getId().toString(), seckillGoods);
            //给每个秒杀商品做队列
//            redisTemplate.boundListOps("secKillGoodsQueue_" + seckillGoods.getId())
//                    .leftPushAll(getGoodsAllIds(seckillGoods.getStockCount(), seckillGoods.getId()));
            //自增计数器
            redisTemplate.boundHashOps("secKillGoodsIncrement").increment(seckillGoods.getId().toString(), seckillGoods.getStockCount());
        }
    }

    /**
     * 商品 id 集合
     *
     * @param num
     * @param id
     * @return
     */
    public Long[] getGoodsAllIds(Integer num, Long id) {
        Long[] ids = new Long[num];
        for (int i = 0; i < num; i++) {
            ids[i] = id;
        }
        return ids;
    }
}
