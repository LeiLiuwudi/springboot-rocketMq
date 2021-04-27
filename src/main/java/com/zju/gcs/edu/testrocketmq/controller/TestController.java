package com.zju.gcs.edu.testrocketmq.controller;


import com.zju.gcs.edu.testrocketmq.service.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/writeMessage")
    public void writeMessage() throws InterruptedException {
        Producer.sendMessage("topic", "tag", "hello, RocketMq");
    }
}
