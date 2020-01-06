package com.dmarco.seckill.service;

import com.dmarco.seckill.dao.OrderDao;
import com.dmarco.seckill.domain.OrderInfo;
import com.dmarco.seckill.domain.SeckillOrder;
import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Dmarco
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getSeckillOrderByUserIdGoodsId(userId,goodsId);
    }


    @Transactional
    public OrderInfo createOrder(SeckillUser user, GoodsVO goodsVO) {
        //更新orderInfo
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        //更新seckillOrder
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setGoodsId(goodsVO.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;

    }
}
