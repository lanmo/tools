package com.yn.tools.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by yangnan on 2017/2/27.
 */
public class RocketMQConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Consumer");
        consumer.setNamesrvAddr("10.211.55.6:9876");

        try {
            consumer.subscribe("TestTopic", "*");

            /**
             * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
             * 如果非第一次启动，那么按照上次消费的位置继续消费
             */

            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently(){
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext
                        context) {
                    Message message = list.get(0);
                    String recString = null;
                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + list + "%n");
                    try {
                        recString = new String(message.getBody(), "UTF-8");
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("recString=" + recString);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
