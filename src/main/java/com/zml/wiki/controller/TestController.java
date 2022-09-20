package com.zml.wiki.controller;

import com.zml.wiki.domain.Test;
import com.zml.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController//一般用来返回字符串
//@Controller用来返回页面比如返回hello world页面
public class TestController {
    @Value("${test.hello:TEST}")
    private String testHello;
    @Resource
    private TestService testService;
    /*
    * GET,POST,PUT,DELETE常用
    *
    *
    *
    * */
    @GetMapping ("/hello")//支持所有的请求方式
    public String hello(){
        return "Hello World!" + testHello;
    }
    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "Hello World! Post," + name;
    }
    @GetMapping ("/test/list")
    public List<Test> list(){
        return testService.list();
    }
}
