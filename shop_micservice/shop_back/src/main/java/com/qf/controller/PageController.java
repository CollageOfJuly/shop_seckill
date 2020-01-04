package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangjun
 * @Date 2019/12/4
 */
@Controller
@RequestMapping("/topage")
public class PageController {

    @RequestMapping("/{page}")
    public String page(@PathVariable String page){
        return page;
    }
}
