package com.seckill.service;

import com.seckill.beans.po.LnScheduler;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
public interface ILnSchedulerService extends IService<LnScheduler> {

    /**
     * 执行 任务调度
     *
     * @param scheduler
     */
    void execute(LnScheduler scheduler);

    /**
     * 将完成的scheduler删除
     *
     */
    void delSchedulers();

    /**
     * 将放入task中的scheduler设为tasking
     *
     */
    void transferList();

}
