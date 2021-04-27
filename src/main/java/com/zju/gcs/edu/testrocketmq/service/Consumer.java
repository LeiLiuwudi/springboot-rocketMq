package com.zju.gcs.edu.testrocketmq.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.zju.gcs.edu.testrocketmq.handler.TestHandler;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Consumer {

    @Value("${rocketmq.producer.group}")
    private String groupName;
    @Value("${rocketmq.name-server}")
    private String nameServerAddress;
    @Autowired
    private TestHandler handler;

    @PostConstruct
    public void init(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameServerAddress);
        consumer.registerMessageListener(handler);
        consumer.setInstanceName("TestForConsumer");
        try {
            consumer.subscribe("topic", "tag");
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
