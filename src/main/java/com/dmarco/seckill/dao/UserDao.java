package com.dmarco.seckill.dao;

import com.dmarco.seckill.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Dmarco
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id} ")
    public User getById(@Param("id")Integer id);

    @Update("insert into user (id,name) values (#{id}, #{name})")
    public void insert(User user);
}
