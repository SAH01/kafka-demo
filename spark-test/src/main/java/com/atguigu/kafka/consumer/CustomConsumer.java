package com.atguigu.kafka.consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
public class CustomConsumer {
	public static void main(String[] args) {
// 1.创建消费者的配置对象
		Properties properties = new Properties();
// 2.给消费者配置对象添加参数
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"hadoop102:9092");
// 配置序列化 必须
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
// 配置消费者组（组名任意起名） 必须
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
// 创建消费者对象
		KafkaConsumer<String, String> kafkaConsumer = new
				KafkaConsumer<String, String>(properties);
// 注册要消费的主题（可以消费多个主题）
		ArrayList<String> topics = new ArrayList<>();
		topics.add("first");
		kafkaConsumer.subscribe(topics);
// 拉取数据打印
		while (true) {
// 设置 1s 中消费一批数据
			ConsumerRecords<String, String> consumerRecords =
					kafkaConsumer.poll(Duration.ofSeconds(1));
// 打印消费到的数据
			for (ConsumerRecord<String, String> consumerRecord :
					consumerRecords) {
				System.out.println(consumerRecord);
			}
		}
	}
}