package com.boot.ins.controller;

import com.boot.ins.logger.LoggerDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1")

public class TestController {

    @Autowired
    LoggerDispatcher loggerWritter;


    @PostMapping("/post")
    public @ResponseBody
    PostDataRequest doPost(@RequestBody PostDataRequest postDataRequest) {


        loggerWritter.addMetric("post", "to be or not to be ");
        loggerWritter.logtrackTrace();
        return postDataRequest;

    }


    @GetMapping("/rest")
    public String hello() {

        loggerWritter.addMetric("Hi", "Hello");

        loggerWritter.addMetric("get", "GETT");
        loggerWritter.logtrackTrace();
        return "Hi from rest !!!";
    }


    @GetMapping("/rest2")
    public String hello2() {

        loggerWritter.addMetric("Hi", "Hello");

        loggerWritter.addMetric("get", "GETT");
        loggerWritter.logtrackTrace();
        return "Hi from rest !!!";
    }
}