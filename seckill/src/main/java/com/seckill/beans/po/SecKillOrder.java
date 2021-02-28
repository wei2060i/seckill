package com.seckill.beans.po;

import java.math.BigDecimal;
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
@TableName("t_sec_kill_order")
public class SecKillOrder extends Model<SecKillOrder> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 秒杀商品ID
     */
    @TableField("sec_kill_id")
    private Long secKillId;

    /**
     * 支付金额
     */
    @TableField("money")
    private BigDecimal money;

    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 状态，0未支付，1已支付
     */
    @TableField("status")
    private String status;

    /**
     * 交易流水
     */
    @TableField("transaction_id")
    private String transactionId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
