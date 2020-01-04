package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@Data
@Accessors(chain = true)
public class Goods extends BaseEntity {
    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;
    //商品类型---1表示普通商品，2表示秒杀商品
    private Integer type=1;

    //非数据库中属性，用于关联图片、秒杀的实体类
    @TableField(exist = false)
    private String fmUrl;
    @TableField(exist = false)
    private List<String> otherUrl=new ArrayList<>();
    @TableField(exist = false)
    private GoodsSecondkill goodsSecondkill;

    public void addOtherUrl(String url){
        otherUrl.add(url);
    }
}
