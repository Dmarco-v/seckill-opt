package com.dmarco.seckill.service;

import com.dmarco.seckill.dao.UserDao;
import com.dmarco.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dmarco
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(Integer id){
        return  userDao.getById(id);
    }

    @Transactional
    public Boolean tx(){
        User user1=new User();
        user1.setId(2);
        user1.setName("Jack");
        userDao.insert(user1);
        User user2=new User();
        user2.setId(1);
        user2.setName("Jerry");
        userDao.insert(user2);
        return true;
    }
}
