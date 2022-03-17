package com.mjp.seckill.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品表
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
@Data
@TableName("product")
public class Product implements Serializable {
    public static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    /**
     * 名称
     */
    @TableField("p_name")
    private String pName;

    /**
     * 单价
     */
    @TableField("p_price")
    private BigDecimal pPrice;

    /**
     * 库存数量
     */
    @TableField("p_number")
    private Integer pNumber;

    /**
     * 活动持续时间（秒）
     */
    @TableField("activity_date")
    private Integer activityDate;

    /**
     * 状态（0：未开启，1：已开启，2：已结束）
     */
    @TableField("status")
    private Integer status;
}
