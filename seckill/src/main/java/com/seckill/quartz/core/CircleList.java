package com.seckill.quartz.core;


import com.seckill.beans.po.LnScheduler;
import com.seckill.consts.SystemConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CircleList {
    public static ConcurrentLinkedQueue<LnScheduler>[] array = new ConcurrentLinkedQueue[SystemConstant.TASK_SCHEDULERS_CIRCLE_LIST_SIZE];
    public static Integer currentIndex = 0;
    public static List<LnScheduler> noTaskSchedulers = new ArrayList<>();
}
