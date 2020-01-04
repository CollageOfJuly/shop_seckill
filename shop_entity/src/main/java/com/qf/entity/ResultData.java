package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zhangjun
 * @Date 2020/1/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultData<T>{
    private String code;
    private String msg;
    private T data;

    public static interface ResultCodeList{
        String OK = "200";
        String ERROR="500";
    }
}
