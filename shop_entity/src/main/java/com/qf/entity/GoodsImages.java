package com.qf.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@Data
@Accessors(chain = true)
public class GoodsImages extends BaseEntity{
    private Integer gid;
    private String info;
    private String url;
    private Integer isfengmian;
}
