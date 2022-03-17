package com.mjp.seckill.domain.param;

import lombok.Data;

/**
 * 购买产品param
 * @version 1.0
 * @developDate 2022/1/12
 * @developAuthor MinJianPeng
 */
@Data
public class BuyProductParam {
    private Long productId;
    private String customerName;
}
