package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.entity.ResultData;
import com.qf.feign.GoodsFeign;
import com.qf.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangjun
 * @Date 2020/1/3
 */
@RestController
@RequestMapping("/kill")
public class KillController {
    @Autowired
    private GoodsFeign goodsFeign;
    /**
     * 查询当前的秒杀场次
     * @return
     */
    @RequestMapping("/queryKillTimes")
    public ResultData<List<Date>> queryKillTimes(){
        List<Date> list=new ArrayList<>();
        //获取当前时间整点
        Date now = DateUtil.getNextNDate(0);
        //获取下一个小时的时间整点
        Date next1 = DateUtil.getNextNDate(1);
        //获得下两个小时的时间整点
        Date next2 = DateUtil.getNextNDate(2);
        list.add(now);
        list.add(next1);
        list.add(next2);
        return new ResultData<List<Date>>().setCode(ResultData.ResultCodeList.OK).setMsg("跳转成功").setData(list);
    }

    @RequestMapping("/queryKillList")
    public ResultData<List<Goods>> queryKillList(Integer n){
        Date time = DateUtil.getNextNDate(n);
        List<Goods> goodsList = goodsFeign.queryKillList(time);
        System.out.println("整点秒杀商品："+goodsList);
        return new ResultData<List<Goods>>().setCode(ResultData.ResultCodeList.OK).setMsg("获取秒杀信息成功").setData(goodsList);
    }
}
