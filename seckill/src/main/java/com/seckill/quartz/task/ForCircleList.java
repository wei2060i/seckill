package com.seckill.quartz.task;

import com.seckill.consts.SystemConstant;
import com.seckill.quartz.core.CircleList;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @author lontten
 * <p>
 * task执行线程
 */
@Component
public class ForCircleList extends Thread {
    @Override
    public void run() {
        //启动 环形遍历，每秒执行一格环形消息
        while (CircleList.currentIndex < CircleList.array.length) {
            //启动task执行线程
            new MyTask().start();
            if (CircleList.currentIndex == SystemConstant.TASK_SCHEDULERS_CIRCLE_LIST_SIZE - 1) {
                CircleList.currentIndex = 0;
            } else {
                CircleList.currentIndex++;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}