package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsImagesMapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodsSecondkillMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.entity.GoodsSecondkill;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@Service
@CacheConfig(cacheNames = "goods")
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImagesMapper goodsImagesMapper;
    @Autowired
    private GoodsSecondkillMapper goodsSecondkillMapper;
    /**
     * 添加商品
     * @param goods
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(key = "'kill_'+#goods.goodsSecondkill.startTime.time",condition = "#goods.type == 2")
    public int insertGoods(Goods goods) {
        //添加商品信息到数据库
        goodsMapper.insert(goods);
        //添加商品封面图片信息到数据库
        if (goods.getFmUrl()!=null){
            GoodsImages goodsImages=new GoodsImages()
                    .setUrl(goods.getFmUrl())
                    .setGid(goods.getId())
                    .setIsfengmian(1);
            goodsImagesMapper.insert(goodsImages);
        }
        //添加商品其他图片信息到数据库
        if (goods.getOtherUrl()!=null){
            for (String url : goods.getOtherUrl()) {
                GoodsImages goodsImages=new GoodsImages()
                        .setUrl(url)
                        .setGid(goods.getId())
                        .setIsfengmian(0);
                goodsImagesMapper.insert(goodsImages);
            }
        }
        //添加秒杀商品到数据库中
        if (goods.getType()==2) {
            GoodsSecondkill goodsSecondkill = goods.getGoodsSecondkill();
            goodsSecondkill.setGid(goods.getId());
            goodsSecondkillMapper.insert(goodsSecondkill);
        }
        return 1;
    }

    /**
     * 获取商品所有信息
     * @return
     */
    @Override
    @Transactional(readOnly = true) //通过此注解可以保证所有步骤为一个线程完成（未加注解是有多个线程完成此方法）
    public List<Goods> goodsList() {
        List<Goods> goodsList = goodsMapper.selectList(null);
        for (Goods goods : goodsList) {
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("gid",goods.getId());
            List<GoodsImages> goodsImagesList = goodsImagesMapper.selectList(queryWrapper);
            for (GoodsImages goodsImages : goodsImagesList) {
                //是封面
                if (goodsImages.getIsfengmian()==1){
                    goods.setFmUrl(goodsImages.getUrl());
                }else {
                    //其他图片
                    goods.addOtherUrl(goodsImages.getUrl());
                }
            }
            //处理秒杀商品
            if (goods.getType()==2) {
                GoodsSecondkill goodsSecondkill = goodsSecondkillMapper.selectOne(queryWrapper);
                goods.setGoodsSecondkill(goodsSecondkill);
            }
        }
        return goodsList;
    }

    /**
     * 查询对应时间内的秒杀商品信息
     * @param date
     * @return
     */
    @Cacheable(key = "'kill_'+#date.time") //date.time表示date数据的毫秒数
    public List<Goods> queryKillList(Date date){
        //先查询出秒杀商品对应的gid
        QueryWrapper queryWrapper=new QueryWrapper();
                 //对应格式才能从数据库中查询出相同格式的时间
        queryWrapper.eq("start_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        List<GoodsSecondkill> goodsSecondkillList = goodsSecondkillMapper.selectList(queryWrapper);
        //再通过gid查询出对应的商品信息
        List<Goods> goodsList=new ArrayList<>();
        for (GoodsSecondkill goodsSecondkill : goodsSecondkillList) {
            Goods goods = goodsMapper.selectById(goodsSecondkill.getGid());
            goods.setGoodsSecondkill(goodsSecondkill);
            //查询图片信息
            QueryWrapper queryWrapper2=new QueryWrapper();
            queryWrapper2.eq("gid",goods.getId());
            List<GoodsImages> goodsImagesList = goodsImagesMapper.selectList(queryWrapper2);
            for (GoodsImages goodsImages : goodsImagesList) {
                //是封面
                if (goodsImages.getIsfengmian()==1){
                    goods.setFmUrl(goodsImages.getUrl());
                }else {
                    //其他图片
                    goods.addOtherUrl(goodsImages.getUrl());
                }
            }
            goodsList.add(goods);
        }
        return goodsList;
    }
}
