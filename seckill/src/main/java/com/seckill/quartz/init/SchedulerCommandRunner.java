package com.seckill.quartz.init;

import com.seckill.beans.po.LnScheduler;
import com.seckill.consts.SystemConstant;
import com.seckill.quartz.core.CircleList;
import com.seckill.quartz.task.ForCircleList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author lontten
 * <p>
 * 初始化，创建 CircleList
 */
@Component
public class SchedulerCommandRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        ConcurrentLinkedQueue<LnScheduler>[] array = CircleList.array;
        //初始化,添加空数组
        for (int i = 0; i < SystemConstant.TASK_SCHEDULERS_CIRCLE_LIST_SIZE; i++) {
            array[i] = new ConcurrentLinkedQueue<>();
        }
        //启动环形遍历
        new ForCircleList().start();
    }
}
