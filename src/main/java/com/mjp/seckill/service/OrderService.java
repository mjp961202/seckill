package com.mjp.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mjp.seckill.domain.POrder;
import com.mjp.seckill.utils.web.domain.AjaxResult;

/**
 * 订单service
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
public interface OrderService extends IService<POrder> {
    /**添加订单*/
    int addOrder(POrder POrder);

    /**查询指定产品订单数量*/
    int getOrderSum(Long pId);

    /**同步数据到数据库*/
    AjaxResult synData(Long pId);
}
