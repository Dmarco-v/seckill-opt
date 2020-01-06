package com.dmarco.seckill.controller;

import com.dmarco.seckill.domain.OrderInfo;
import com.dmarco.seckill.domain.SeckillOrder;
import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.result.CodeMsg;
import com.dmarco.seckill.service.GoodsService;
import com.dmarco.seckill.service.OrderService;
import com.dmarco.seckill.service.SeckillService;
import com.dmarco.seckill.service.SeckillUserService;
import com.dmarco.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Dmarco
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {


    @Autowired
    private SeckillUserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/do_seckill")
    public String toList(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        //判断库存
        GoodsVO goodsVO=goodsService.getGoodsVOByGoodsId(goodsId);
        int stock=goodsVO.getStockCount();
        if(stock<=0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }
        //判断是否已秒杀
        SeckillOrder order=orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order!=null){
            model.addAttribute("errmsg", CodeMsg.SECKILL_REPEAT.getMsg());
            return "seckill_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo=seckillService.seckill(user,goodsVO);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVO);
        return "order_detail";
    }


}