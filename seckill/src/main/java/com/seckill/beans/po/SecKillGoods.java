package com.seckill.beans.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
@TableName("t_sec_kill_goods")
public class SecKillGoods extends Model<SecKillGoods> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;

    /**
     * 标题
     */
    @TableField("name")
    private String name;

    /**
     * 商品图片
     */
    @TableField("small_pic")
    private String smallPic;

    /**
     * 原价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 秒杀价格
     */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     * 添加日期
     */
    @TableField("create_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 审核日期
     */
    @TableField("check_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime checkTime;

    /**
     * 审核状态，0未审核，1审核通过，2审核不通过
     */
    @TableField("status")
    private String status;

    /**
     * 开始时间
     */
    @TableField("start_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;

    /**
     * 秒杀商品数
     */
    @TableField("num")
    private Integer num;

    /**
     * 剩余库存数
     */
    @TableField("stock_count")
    private Integer stockCount;

    /**
     * 描述
     */
    @TableField("introduction")
    private String introduction;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
