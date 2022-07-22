package com.scaffold.web.controller;

import com.alibaba.fastjson.JSON;
import com.scaffold.base.aop.PrintLog;
import com.scaffold.service.api.HelloWorldService;
import com.scaffold.web.vo.ModelCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping(value = "/helloWorld/{id}", method = RequestMethod.GET)
    public String helloWorld(@PathVariable("id") Integer id) {
        System.out.println("get" + id);
        return String.valueOf(helloWorldService.getHello());
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request, ModelCase modelCase) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        return JSON.toJSONString(modelCase);
    }

    @PrintLog(log = "成功")
    @RequestMapping("/logTest")
    public String logTest() {
        log.info("日志打印成功");
        return "success";
    }

    @RequestMapping("/requestParamTest")
    public String requestParamTest(@RequestParam("param") String param) {
        return param;
    }

    @RequestMapping("/requestBodyTest")
    public String requestBodyTest(@RequestBody ModelCase modelCase) {
        return modelCase.toString();
    }

    @RequestMapping("/dbTest")
    public String dbTest(int pk) {
        return JSON.toJSONString(helloWorldService.getHello(pk));
    }
}
