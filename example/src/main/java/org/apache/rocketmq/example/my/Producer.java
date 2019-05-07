package org.apache.rocketmq.example.my;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {

	private final static String namesrvAddr = "127.0.0.1:9876";

	public static void main(String[] args) throws MQClientException, InterruptedException {

		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		producer.setNamesrvAddr(namesrvAddr);

		producer.start();

		for (int i = 0; i < 100; i++) {
			try {

				Message msg = new Message("TopicTest_1", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

				SendResult sendResult = producer.send(msg);

				System.out.printf("%s%n", sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}

		for (int i = 0; i < 10; i++) {
			try {

				Message msg = new Message("TopicTest_2", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

				SendResult sendResult = producer.send(msg);

				System.out.printf("%s%n", sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}

		producer.shutdown();
	}
}
