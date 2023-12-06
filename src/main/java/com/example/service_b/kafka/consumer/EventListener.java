package com.example.service_b.kafka.consumer;

import com.example.service_b.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListener {

  @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_3, partitions = {"0"}))
  public void partition_1(@Payload String message) {
    log.info("@D_LOG: topic 3 partition 1:" + message);
  }

  @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_3, partitions = {"1"}))
  public void partition_2(@Payload String message) {
    log.info("@D_LOG: topic 3 partition 2:" + message);
  }

  @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_3, partitions = {"2"}))
  public void partition_3(@Payload String message) {
    log.info("@D_LOG: topic 3 partition 3:" + message);
  }
}
