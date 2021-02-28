package com.seckill.service.impl;

import com.seckill.beans.po.LnScheduler;
import com.seckill.dao.LnSchedulerDao;
import com.seckill.quartz.core.CircleList;
import com.seckill.service.ILnSchedulerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@Service
public class LnSchedulerServiceImpl extends ServiceImpl<LnSchedulerDao, LnScheduler> implements ILnSchedulerService {

    @Override
    @Transactional
    public void execute(LnScheduler scheduler) {

    }

    @Override
    @Transactional
    public void delSchedulers() {
        final List<LnScheduler> list = new ArrayList<>();
        for (LnScheduler noTaskScheduler : CircleList.noTaskSchedulers) {
            list.add(new LnScheduler().setId(noTaskScheduler.getId()).setDel(true));
        }
        updateBatchById(list);
    }

    @Override
    @Transactional
    public void transferList() {
        final List<LnScheduler> list = new ArrayList<>();
        for (LnScheduler noTaskScheduler : CircleList.noTaskSchedulers) {
            list.add(new LnScheduler().setId(noTaskScheduler.getId()).setTasking(true));
        }
        updateBatchById(list);
    }

}
