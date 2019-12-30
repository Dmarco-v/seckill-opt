package com.dmarco.seckill.controller;

import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.service.SeckillUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Dmarco
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private SeckillUserService userService;


    @RequestMapping("/to_list")
    public String toList(Model model,SeckillUser user
/*                         @CookieValue(value=SeckillUserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                         @RequestParam(value=SeckillUserService.COOKIE_NAME_TOKEN,required = false) String paramToken*/){

        //都为空返回登录页面
        model.addAttribute("user",user);
        return "goods_list";
    }



}