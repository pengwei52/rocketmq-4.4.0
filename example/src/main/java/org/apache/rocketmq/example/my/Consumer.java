package org.apache.rocketmq.example.my;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

	private final static String namesrvAddr = "127.0.0.1:9876";

	public static void main(String[] args) throws InterruptedException, MQClientException {

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
		consumer.setNamesrvAddr(namesrvAddr);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		consumer.subscribe("TopicTest_1", "*");

		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt m : msgs) {
					System.out.println("Consumer1 receive msg : " + new String(m.getBody()));
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();

		System.out.printf("Consumer Started.%n");

		start2();
		System.out.printf("Consumer2 Started.%n");
	}

	private static void start2() throws InterruptedException, MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
		consumer.setNamesrvAddr(namesrvAddr);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		consumer.subscribe("TopicTest_2", "*");

		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt m : msgs) {
					System.out.println("Consumer2 receive msg : " + new String(m.getBody()));
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();
	}
}
