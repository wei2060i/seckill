package com.seckill.beans.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SecKillStatusListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //秒杀用户
    private Long uid;

    //创建时间
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    //秒杀状态  1:排队中，2:秒杀等待支付,3:支付超时，4:秒杀失败,5:支付完成
    private Integer status;
    //秒杀的商品ID
    private Long goodsId;
    //应付金额
    private BigDecimal money;
    //订单号
    private Long orderId;

    public SecKillStatusListDto(Long uid, LocalDateTime createTime, Integer status, Long goodsId) {
        this.uid = uid;
        this.createTime = createTime;
        this.status = status;
        this.goodsId = goodsId;
    }
}
