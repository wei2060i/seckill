package com.seckill.quartz.task;

import com.seckill.beans.po.LnScheduler;
import com.seckill.consts.SystemConstant;
import com.seckill.quartz.core.CircleList;
import com.seckill.service.ILnSchedulerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author lontten
 * <p>
 * push task线程
 */
@Component
public class PushTask extends Thread {

    private static ILnSchedulerService lnSchedulerService;

    @Resource
    public void setLnJobService(ILnSchedulerService lnSchedulerService) {
        PushTask.lnSchedulerService = lnSchedulerService;
    }

    @Override
    public void run() {
        for (LnScheduler scheduler : CircleList.noTaskSchedulers) {
            if (scheduler == null) {
                continue;
            }
            int seconds = (int) Duration.between(LocalDateTime.now(), scheduler.getExecuteTime()).getSeconds();
            //当要添加的scheduler执行时间正好是当前的这一秒，推迟到下一秒执行
            if (seconds < 1) {
                seconds = 1;
            }
            //数据添加到队列最后
            final int index = (CircleList.currentIndex + seconds) % SystemConstant.TASK_SCHEDULERS_CIRCLE_LIST_SIZE;
            
            CircleList.array[index].offer(scheduler);
        }
        //将noTaskJobs状态改为tasking放回mysql
        lnSchedulerService.transferList();
    }
}