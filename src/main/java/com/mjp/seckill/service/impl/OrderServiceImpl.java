package com.mjp.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjp.seckill.domain.POrder;
import com.mjp.seckill.domain.Product;
import com.mjp.seckill.mapper.OrderMapper;
import com.mjp.seckill.service.OrderService;
import com.mjp.seckill.service.ProductService;
import com.mjp.seckill.utils.RedisConstant;
import com.mjp.seckill.utils.exception.CustomException;
import com.mjp.seckill.utils.redis.RedisService;
import com.mjp.seckill.utils.web.domain.AjaxResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单impl
 *
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, POrder> implements OrderService {
    @Resource
    private RedisService redisService;
    @Resource
    private ProductService productService;

    /**
     * 添加订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrder(POrder pOrder) {
        try {
            return this.baseMapper.insert(pOrder);
        } catch (Exception e) {
            throw new CustomException("添加订单发生错误！");
        }
    }

    /**
     * 查询指定产品订单数量
     */
    @Override
    public int getOrderSum(Long pId) {
        QueryWrapper<POrder> qw = new QueryWrapper<>();
        qw.eq("product_id", pId);
        return this.baseMapper.selectCount(qw);
    }

    /**
     * 同步数据到数据库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult synData(Long pId) {
        List<POrder> list = redisService.getCacheList(RedisConstant.ORDER_LIST + pId);
        List<String> plist = redisService.getCacheList(RedisConstant.PRODUCT_LIST + pId);
        redisService.remove(RedisConstant.PRODUCT_LIST + pId);
        redisService.remove(RedisConstant.ORDER_LIST + pId);
        int num = CollectionUtils.isEmpty(plist) ? 0 : plist.size();
        if (!CollectionUtils.isEmpty(list)) {
            System.out.println("---------------------------------------------");
            System.out.println("产生订单" + list.size() + "条！");
            System.out.println("剩余产品" + num + "件！");
            System.out.println("---------------------------------------------");
            this.saveBatch(list);
        }
        Product p = new Product();
        p.setId(pId);
        p.setPNumber(num);
        p.setStatus(2);
        return AjaxResult.success(productService.updateById(p));
    }
}
