package com.zju.gcs.edu.testrocketmq.service;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Producer {

    @Value("${rocketmq.producer.group}")
    private String groupName;
    @Value("${rocketmq.name-server}")
    private String nameServerAddress;
    private static DefaultMQProducer producer = null;
    private static final Integer DEFAULT_TIMEOUT = 500;
    @PostConstruct
    public void init(){
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameServerAddress);
        producer.setInstanceName("TestForProducer");
        try {
            producer.start();
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                //钩子函数随jvm销毁关闭mq
                @Override
                public void run() {
                    producer.shutdown();
                }
            }));
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    public static String sendMessage(String topic, String tag, String mes){
        byte[] messageByte = mes.getBytes();
        Message message = new Message(topic, tag, messageByte);
        SendResult sendResult = null;
        try {
            sendResult = producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return sendResult.getMsgId();
    }

}
