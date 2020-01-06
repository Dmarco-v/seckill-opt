package com.dmarco.seckill.controller;

import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.service.GoodsService;
import com.dmarco.seckill.service.SeckillUserService;
import com.dmarco.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dmarco
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private SeckillUserService userService;
    @Autowired
    private GoodsService goodsService;


    @RequestMapping("/to_list")
    public String toList(Model model,SeckillUser user
/*                         @CookieValue(value=SeckillUserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                         @RequestParam(value=SeckillUserService.COOKIE_NAME_TOKEN,required = false) String paramToken*/){
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVO> goodsList= goodsService.listGoodsVO();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, SeckillUser user, @PathVariable("goodsId") long goodsId ){
        model.addAttribute("user",user);
        GoodsVO goodsVO = goodsService.getGoodsVOByGoodsId(goodsId);
        model.addAttribute("goods",goodsVO);

        //判断秒杀活动状态
        long startTime=goodsVO.getStartDate().getTime();
        long endTime=goodsVO.getEndDate().getTime();
        long now=System.currentTimeMillis();

        int seckillStatus=0;
        int remainSeconds=0;
        if(now<startTime){
            seckillStatus=0;
            remainSeconds= (int) ((startTime-now)/1000);
        }else if(now>endTime){
            seckillStatus=2;
            remainSeconds=-1;
        }else{
            seckillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }


}