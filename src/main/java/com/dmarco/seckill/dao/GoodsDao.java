package com.dmarco.seckill.dao;

import com.dmarco.seckill.domain.Goods;
import com.dmarco.seckill.domain.SeckillGoods;
import com.dmarco.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Dmarco
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,sg.stock_count,sg.start_date,sg.end_date,sg.seckill_price from seckill_goods sg left join goods g on sg.goods_id = g.id ")
    public List<GoodsVO> listGoodsVO();

    @Select("select g.*,sg.stock_count,sg.start_date,sg.end_date,sg.seckill_price from seckill_goods sg left join goods g on sg.goods_id = g.id where g.id= #{goodsId}")
    public GoodsVO getGoodsVOByGoodsId(@Param("goodsId")long goodsId);

    @Update("update seckill_goods set stock_count =stock_count-1 where goods_id=#{goodsId}")
    public int reduceStick(SeckillGoods goods);
}
