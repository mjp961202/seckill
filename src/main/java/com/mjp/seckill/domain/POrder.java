package com.mjp.seckill.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单表
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
@Data
@TableName("p_order")
public class POrder implements Serializable {
    public static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    /**
     * 客户名称
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 产品id
     */
    @TableField("product_id")
    private Long productId;
}
