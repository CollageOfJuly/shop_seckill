package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;
    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/insert")
    public int insertGoods(@RequestBody Goods goods){

        return goodsService.insertGoods(goods);
    }

    /**
     * 展示所有商品信息
     * @return
     */
    @RequestMapping("/list")
    public List<Goods> goodsList(){
        return goodsService.goodsList();
    }

    /**
     * 查询对应时间内的秒杀商品信息
     * @param date
     * @return
     */
    @RequestMapping("/queryKillList")
    public List<Goods> queryKillList(@RequestBody Date date){
        return goodsService.queryKillList(date);
    }
}
