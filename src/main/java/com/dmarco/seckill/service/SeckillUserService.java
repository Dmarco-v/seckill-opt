package com.dmarco.seckill.service;

import com.dmarco.seckill.dao.SeckillUserDao;
import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.exception.GlobalException;
import com.dmarco.seckill.redis.RedisService;
import com.dmarco.seckill.redis.SeckillUserKey;
import com.dmarco.seckill.result.CodeMsg;
import com.dmarco.seckill.util.MD5Util;
import com.dmarco.seckill.util.UUIDUtil;
import com.dmarco.seckill.vo.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dmarco
 */
@Service
public class SeckillUserService {

    public static final String COOKIE_NAME_TOKEN= "token";


    @Autowired
    private SeckillUserDao seckillUserDao;

    @Autowired
    private RedisService redisService;

    public SeckillUser getById(long id){
        return seckillUserDao.getById(id);
    }

    public SeckillUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        SeckillUser user=redisService.get(SeckillUserKey.token,token,SeckillUser.class);
        //延长有效期
        if(user!=null){
            addCookie(response,token,user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response,LoginVO loginVO){
        if(loginVO ==null ){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile=loginVO.getMobile();
        String formPass=loginVO.getPassword();
        //判断手机号是否存在
        SeckillUser user=getById(new Long(mobile));
        if(user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass=user.getPassword();
        String saltDB =user.getSalt();
        String calcPass= MD5Util.formPassToDBPass(formPass,saltDB );
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);

        return true;
    }

    private void addCookie(HttpServletResponse response,String token,SeckillUser user){
        // 生成token用于标识用户，将其存入redis并写到cookie中;
        // 用户随后的请求中上传cookie，服务端根据拿到的token在redis中拿到用户信息。
        redisService.set(SeckillUserKey.token,token,user);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
