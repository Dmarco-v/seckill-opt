package com.dmarco.seckill.service;

import com.dmarco.seckill.dao.GoodsDao;
import com.dmarco.seckill.domain.Goods;
import com.dmarco.seckill.domain.SeckillGoods;
import com.dmarco.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dmarco
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVO(){
        return goodsDao.listGoodsVO();
    }

    public GoodsVO getGoodsVOByGoodsId(long goodsId) {
        return goodsDao.getGoodsVOByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVO goodsVO) {
        SeckillGoods goods=new SeckillGoods();
        goods.setGoodsId(goodsVO.getId());
        goodsDao.reduceStick(goods);
    }
}
