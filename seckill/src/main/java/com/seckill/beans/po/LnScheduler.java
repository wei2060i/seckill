package com.seckill.beans.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Wei
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_ln_scheduler")
public class LnScheduler extends Model<LnScheduler> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * job名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * job执行时间
     */
    @TableField("execute_time")
    private LocalDateTime executeTime;

    /**
     * 目标主键
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 是否 task
     */
    @TableField("tasking")
    private Boolean tasking;

    @TableField("del")
    private Boolean del;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
