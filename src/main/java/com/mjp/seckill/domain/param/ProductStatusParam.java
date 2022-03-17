package com.mjp.seckill.domain.param;

import lombok.Data;

/**
 * 修改产品状态param
 * @version 1.0
 * @developDate 2022/1/11
 * @developAuthor MinJianPeng
 */
@Data
public class ProductStatusParam {
    /**
     * 编号
     */
    private Long id;

    /**
     * 状态（0：未开启，1：已开启，2：已结束）
     */
    private Integer status;
}
