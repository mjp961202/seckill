package com.mjp.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mjp.seckill.domain.Product;
import com.mjp.seckill.domain.param.BuyProductParam;
import com.mjp.seckill.domain.param.ProductNumberParam;
import com.mjp.seckill.domain.param.ProductStatusParam;
import com.mjp.seckill.utils.web.domain.AjaxResult;

import java.util.List;

/**
 * 产品service
 *
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
public interface ProductService extends IService<Product> {

    /**
     * 查询全部产品
     */
    List<Product> getProductList();

    /**
     * 查询产品详情
     */
    Product getProductById(Long pId);

    /**
     * 修改产品状态
     */
    AjaxResult setProductStatus(ProductStatusParam param);

    /**
     * 产品数量减一
     */
    AjaxResult setProductReduce(Long pId);

    /**
     * 修改产品数量
     */
    int setProductNumber(ProductNumberParam param);

    /**
     * 启动活动
     */
    AjaxResult startActivity(Long pId);

    /**
     * 购买产品
     */
    AjaxResult buyProduct(BuyProductParam param);
}
