package com.yn.tools.producer.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by yangnan on 2017/2/24.
 */
public class RocketMQProducer {

    public static void main(String[] args) {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("10.211.55.6:9876");
//        producer.setInstanceName("TT");
        try {
            producer.start();
            String pushMsg = "杨楠测试呢a aaaa";
            Message msg = new Message("TestTopic", "Tag1", pushMsg.getBytes("UTF-8"));
            SendResult result = producer.send(msg);
            System.out.println("id=" + result.getMsgId() + ",result="+result.getSendStatus());


            pushMsg = "海量日志1";
            msg = new Message("TestTopic", "push", "2", pushMsg.getBytes("UTF-8"));
            result = producer.send(msg);
            System.out.println("id=" + result.getMsgId() + ",result="+result.getSendStatus());

            pushMsg = "海量日志3";
            msg = new Message("TestTopic", "pull", "1", pushMsg.getBytes("UTF-8"));
            result = producer.send(msg);
            System.out.println("id=" + result.getMsgId() + ",result="+result.getSendStatus());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
