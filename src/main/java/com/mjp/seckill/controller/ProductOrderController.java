package com.mjp.seckill.controller;

import com.mjp.seckill.domain.POrder;
import com.mjp.seckill.domain.param.BuyProductParam;
import com.mjp.seckill.domain.param.ProductNumberParam;
import com.mjp.seckill.domain.param.ProductStatusParam;
import com.mjp.seckill.service.OrderService;
import com.mjp.seckill.service.ProductService;
import com.mjp.seckill.utils.web.controller.BaseController;
import com.mjp.seckill.utils.web.domain.AjaxResult;
import com.mjp.seckill.utils.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 产品订单controller
 * @version 1.0
 * @developDate 2022/1/10
 * @developAuthor MinJianPeng
 */
@RestController
@RequestMapping("/po")
public class ProductOrderController extends BaseController {
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;

    /**
     * 名称：查询全部产品
     * <p/>
     * @version 1.0
     * @return TableDataInfo
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @GetMapping("/getProductList")
    public TableDataInfo getProductList(){
        startPage();
        return getDataTable(productService.getProductList());
    }

    /**
     * 名称：查询产品详情
     * <p/>
     * @version 1.0
     * @param pId=产品id
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @GetMapping("/getProductById")
    public AjaxResult getProductById(@RequestParam("pId")Long pId){
        return AjaxResult.success(productService.getProductById(pId));
    }

    /**
     * 名称：修改产品状态
     * <p/>
     * @version 1.0
     * @param productStatusParam=参数类
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @PostMapping("/setProductStatus")
    public AjaxResult setProductStatus(@RequestBody ProductStatusParam productStatusParam){
        return productService.setProductStatus(productStatusParam);
    }

    /**
     * 名称：购买产品
     * <p/>
     * 说明：产品数量减一
     * @version 1.0
     * @param pId=产品id
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @PostMapping("/setProductReduce")
    public AjaxResult setProductReduce(Long pId){
        return productService.setProductReduce(pId);
    }

    /**
     * 名称：修改产品数量
     * <p/>
     * @version 1.0
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @PostMapping("/setProductNumber")
    public AjaxResult setProductNumber(@RequestBody ProductNumberParam productNumberParam){
        return toAjax(productService.setProductNumber(productNumberParam));
    }

    /**
     * 名称：添加订单
     * <p/>
     * @version 1.0
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @PostMapping("/addOrder")
    public AjaxResult addOrder(@RequestBody POrder POrder){
        return toAjax(orderService.addOrder(POrder));
    }

    /**
     * 名称：查询指定产品订单数量
     * <p/>
     * @version 1.0
     * @param pId=产品id
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @GetMapping("/getOrderSum")
    public AjaxResult getOrderSum(@RequestParam("pId")Long pId){
        return AjaxResult.success(orderService.getOrderSum(pId));
    }

    /**
     * 名称：启动活动
     * <p/>
     * 说明：判断状态，不等于0则提示启动失败；反之则修改状态，更新数据库
     * 说明：保存n个记录到redis的list中；保存一条商品记录，并设置超时时间
     * @version 1.0
     * @param pId=产品id
     * @return AjaxResult
     * @developDate 2022/1/11
     * @developAuthor MinJianPeng
     */
    @PostMapping("/startActivity")
    public AjaxResult startActivity(Long pId){
        return productService.startActivity(pId);
    }

    /**
     * 名称：购买产品
     * <p/>
     * @version 1.0
     * @return AjaxResult
     * @developDate 2022/1/12
     * @developAuthor MinJianPeng
     */
    @PostMapping("/buyProduct")
    public AjaxResult buyProduct(Long productId,String customerName){
        BuyProductParam param=new BuyProductParam();
        param.setProductId(productId);
        param.setCustomerName(customerName);
        return productService.buyProduct(param);
    }
}
