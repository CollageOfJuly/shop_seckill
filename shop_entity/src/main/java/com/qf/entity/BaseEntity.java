package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@Data
public class BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    protected Integer id;
    protected Date createTime=new Date();
    protected Integer status=0;
}
