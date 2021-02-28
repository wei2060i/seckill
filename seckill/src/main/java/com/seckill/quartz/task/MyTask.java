package com.seckill.quartz.task;


import com.seckill.beans.po.LnScheduler;
import com.seckill.quartz.core.CircleList;
import com.seckill.service.ILnSchedulerService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author lontten
 * <p>
 * task执行线程
 */
@Component
public class MyTask extends Thread {

    private static ILnSchedulerService lnSchedulerService;

    @Resource
    public void setLnJobService(ILnSchedulerService lnSchedulerService) {
        MyTask.lnSchedulerService = lnSchedulerService;
    }

    @Override
    public void run() {
        ConcurrentLinkedQueue<LnScheduler> jobs = CircleList.array[CircleList.currentIndex];
        LnScheduler scheduler = jobs.poll();
        while (scheduler != null) {
            //scheduler execute
            lnSchedulerService.execute(scheduler);
            scheduler = jobs.poll();
        }
        lnSchedulerService.delSchedulers();
    }

}