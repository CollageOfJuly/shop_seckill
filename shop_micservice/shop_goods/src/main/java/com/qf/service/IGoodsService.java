package com.qf.service;

import com.qf.entity.Goods;

import java.util.Date;
import java.util.List;

/**
 * 商品业务接口
 * @author zhangjun
 * @Date 2020/1/2
 */

public interface IGoodsService {
    //添加商品
    int insertGoods(Goods goods);
    //获取所有商品信息
    List<Goods> goodsList();
    //查询秒杀商品
    List<Goods> queryKillList(Date date);
}
