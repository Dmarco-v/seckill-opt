package com.dmarco.seckill.dao;

import com.dmarco.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Dmarco
 */
@Mapper
public interface SeckillUserDao {

    @Select("select * from seckill_user where id= #{id}")
    SeckillUser getById(long id);

}
