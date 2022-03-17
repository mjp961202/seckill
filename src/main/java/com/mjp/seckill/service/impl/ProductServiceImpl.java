package com.mjp.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjp.seckill.domain.POrder;
import com.mjp.seckill.domain.Product;
import com.mjp.seckill.domain.param.BuyProductParam;
import com.mjp.seckill.domain.param.ProductNumberParam;
import com.mjp.seckill.domain.param.ProductStatusParam;
import com.mjp.seckill.mapper.ProductMapper;
import com.mjp.seckill.service.OrderService;
import com.mjp.seckill.service.ProductService;
import com.mjp.seckill.utils.RedisConstant;
import com.mjp.seckill.utils.exception.CustomException;
import com.mjp.seckill.utils.redis.RedisService;
import com.mjp.seckill.utils.web.domain.AjaxResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 产品impl
 *
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private RedisService redisService;
    @Resource
    private OrderService orderService;

    /**
     * 查询全部产品
     */
    @Override
    public List<Product> getProductList() {
        return this.baseMapper.selectList(new QueryWrapper<Product>());
    }

    /**
     * 查询产品详情
     */
    @Override
    public Product getProductById(Long pId) {
        return this.baseMapper.selectById(pId);
    }

    /**
     * 修改产品状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult setProductStatus(ProductStatusParam param) {
        try {
            Product product = this.getProductById(param.getId());
            if (product.getStatus().equals(param.getStatus())) {
                return AjaxResult.error("修改状态失败！请刷新重试！");
            }
            product.setStatus(param.getStatus());
            return AjaxResult.toAjax(this.baseMapper.updateById(product));
        } catch (Exception e) {
            throw new CustomException("修改产品状态发生异常！");
        }
    }

    /**
     * 产品数量减一
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult setProductReduce(Long pId) {
        try {
            Product product = this.getProductById(pId);
            if (product.getStatus() == 0) {
                return AjaxResult.error("活动还没开始！");
            }
            if (product.getStatus() == 2) {
                return AjaxResult.error("活动已结束！");
            }
            if (product.getPNumber() <= 0) {
                return AjaxResult.error("库存数量不足！");
            }
            product.setPNumber(product.getPNumber() - 1);
            return AjaxResult.toAjax(this.baseMapper.updateById(product));
        } catch (Exception e) {
            throw new CustomException("购买产品发生异常！");
        }
    }

    /**
     * 修改产品数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setProductNumber(ProductNumberParam param) {
        try {
            Product p = new Product();
            p.setId(param.getId());
            p.setPNumber(param.getNumber());
            return this.baseMapper.updateById(p);
        } catch (Exception e) {
            throw new CustomException("修改产品数量发生异常！");
        }
    }

    /**
     * 启动活动
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult startActivity(Long pId) {
        Product product = this.getProductById(pId);
        if (product.getStatus() != 0) {
            return AjaxResult.error("启动活动失败！活动已启动或已结束！");
        }
        product.setStatus(1);
        this.baseMapper.updateById(product);
        for (int i = 0; i < product.getPNumber(); i++) {
            redisService.lpush(RedisConstant.PRODUCT_LIST + product.getId(), "1");
        }
        boolean flag = redisService.setIfAbsent(RedisConstant.PRODUCT + pId, product, product.getActivityDate(), TimeUnit.SECONDS);
        return flag ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 购买产品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult buyProduct(BuyProductParam param) {
        if (!redisService.exists(RedisConstant.PRODUCT + param.getProductId())) {
            return AjaxResult.error("活动已结束！");
        }
        Object str = redisService.lPop(RedisConstant.PRODUCT_LIST + param.getProductId());
        if (Objects.isNull(str)) {
            return AjaxResult.error("商品已卖完！");
        }
        POrder pOrder = new POrder();
        BeanUtils.copyProperties(param, pOrder);
        redisService.lpush(RedisConstant.ORDER_LIST + param.getProductId(), pOrder);
        return AjaxResult.success("购买成功！");
    }
}
