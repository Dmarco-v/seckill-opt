package com.dmarco.seckill.controller;


import com.dmarco.seckill.domain.User;
import com.dmarco.seckill.redis.RedisService;
import com.dmarco.seckill.redis.UserKey;
import com.dmarco.seckill.result.CodeMsg;
import com.dmarco.seckill.result.Result;
import com.dmarco.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dmarco
 */

@Controller
@RequestMapping("/demo")
public class HelloController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","dmarco");
        return "hello";
    }

    @RequestMapping("/helloSuccess")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello Success");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloEroor(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        return Result.success(userService.getById(1));
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        return Result.success(userService.tx());
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user=new User();
        user.setId(1);
        user.setName("Tom");
        Boolean res=redisService.set(UserKey.getById,""+1,user);
        return Result.success(res);
    }

    @RequestMapping("/redis/exists")
    @ResponseBody
    public Result<Boolean> redisExists(){
        Boolean exists=redisService.exists(UserKey.getById,""+1);
        return Result.success(exists);
    }

}
