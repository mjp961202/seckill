package com.mjp.seckill.domain.param;

import lombok.Data;

/**
 * 修改产品数量param
 * @version 1.0
 * @developDate 2022/1/11
 * @developAuthor MinJianPeng
 */
@Data
public class ProductNumberParam {
    /**
     * 编号
     */
    private Long id;

    /**
     * 数量
     */
    private Integer number;
}
