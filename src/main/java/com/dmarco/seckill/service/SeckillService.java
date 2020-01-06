package com.dmarco.seckill.service;

import com.dmarco.seckill.domain.Goods;
import com.dmarco.seckill.domain.OrderInfo;
import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dmarco
 */
@Service
public class SeckillService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo seckill(SeckillUser user, GoodsVO goodsVO) {
        //减库存
        goodsService.reduceStock(goodsVO);

        //写入秒杀订单,更新order_info和seckill_order两张表
        return orderService.createOrder(user,goodsVO);


    }
}
