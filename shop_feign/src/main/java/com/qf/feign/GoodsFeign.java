package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */

/**
 * feign中写的方法是提供者的controller方法的接口
 */
@FeignClient("MICSERVICE-GOODS")
public interface GoodsFeign {
    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/goods/insert")
    int insertGoods(@RequestBody Goods goods);

    /**
     * 展示所有商品信息
     * @return
     */
    @RequestMapping("/goods/list")
    List<Goods> goodsList();

    /**
     * 查询对应时间内的秒杀商品信息
     * @param date
     * @return
     */
    @RequestMapping("/goods/queryKillList")
    List<Goods> queryKillList(@RequestBody Date date);
}
