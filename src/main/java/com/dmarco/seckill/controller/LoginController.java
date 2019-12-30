package com.dmarco.seckill.controller;

import com.dmarco.seckill.result.Result;
import com.dmarco.seckill.service.SeckillUserService;
import com.dmarco.seckill.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Dmarco
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private SeckillUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVO loginVO){
        logger.info(loginVO.toString());
        /*//参数校验
        String mobile=loginVO.getMobile();
        String passInput=loginVO.getPassword();
        if(StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        userService.login(response,loginVO);
        return Result.success(true);
    }

}
