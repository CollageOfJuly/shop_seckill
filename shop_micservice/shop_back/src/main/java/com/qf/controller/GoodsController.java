package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.entity.GoodsSecondkill;
import com.qf.entity.ResultData;
import com.qf.feign.GoodsFeign;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 商品管理控制器
 *
 * @author zhangjun
 * @Date 2019/12/4
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
//    /**
//     * 展示商品列表
//     * @return
//     */
//    @Reference
//    private IGoodsService goodsService;
//
//    //注入fastdfs对象
//    @Autowired
//    private FastFileStorageClient fastFileStorageClient;
//
    @Autowired
    private GoodsFeign goodsFeign;

    @RequestMapping("/list")
    public String goodsList(Model model){
        //调用商品服务，查看所有商品
        List<Goods> list = goodsFeign.goodsList();
        System.out.println("查询所有的商品信息："+list);
        model.addAttribute("goodsList",list);
        return "goodslist";
    }
//
    //上传文件
    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file) {
        System.out.println("需要上传的文件为" + file.getOriginalFilename());
        //设置上传成功后文件的名称
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        //设置上传路径
        String path = "C:/Users/ZJ/Pictures/uploadimages/" + filename;
        try (
                //此处为jdk7.0的新特性，可实现自动关流
                InputStream input = file.getInputStream();
                OutputStream out = new FileOutputStream(path);
        ) {
            IOUtils.copy(input, out);
            return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return new ResultData<String>().setCode(ResultData.ResultCodeList.ERROR);
    }

//
    //上传图片回显
    @RequestMapping("/showimg")
    public void showImg(String path, HttpServletResponse response){

        try(
                InputStream input=new FileInputStream(path);
                OutputStream output = response.getOutputStream();
                ){
            IOUtils.copy(input,output);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //添加商品
    @RequestMapping("/insert")
    public String insert(Goods goods, GoodsSecondkill goodsSecondkill){
        goods.setGoodsSecondkill(goodsSecondkill);
        System.out.println(goods);
        goodsFeign.insertGoods(goods);
        return "redirect:list";
    }
//    //验证ajax异常
////    @RequestMapping("/ajax")
////    @ResponseBody
////    public ResultData<String> handleAjax(){
////        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setMsg("无异常");
////    }
}
