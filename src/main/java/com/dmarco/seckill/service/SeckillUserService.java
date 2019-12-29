package com.dmarco.seckill.service;

import com.dmarco.seckill.dao.SeckillUserDao;
import com.dmarco.seckill.domain.SeckillUser;
import com.dmarco.seckill.exception.GlobalException;
import com.dmarco.seckill.result.CodeMsg;
import com.dmarco.seckill.util.MD5Util;
import com.dmarco.seckill.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dmarco
 */
@Service
public class SeckillUserService {

    @Autowired
    private SeckillUserDao seckillUserDao;

    public SeckillUser getById(long id){
        return seckillUserDao.getById(id);
    }

    public boolean login(LoginVO loginVO){
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
        String saltDB=user.getSalt();
        String calcPass= MD5Util.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
        return true;
    }

}
