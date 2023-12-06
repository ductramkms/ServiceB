package com.example.service_b.kafka.consumer;

import com.example.service_b.constant.Constant;
import com.example.service_b.exception.custom.InvalidDataException;
import com.example.service_b.exception.custom.ItemAlreadyExistsException;
import com.example.service_b.payload.common.EmployeeBody;
import com.example.service_b.payload.request.KafkaRequestBody;
import com.example.service_b.service.EmployeeService;
import com.example.service_b.util.Helper;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeListener {

  private final EmployeeService employeeService;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public EmployeeListener(EmployeeService employeeService,
      KafkaTemplate<String, String> kafkaTemplate) {
    this.employeeService = employeeService;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Timed(value = "kafka.listener.topic1.partition1")
  // Partition 1
  @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_2, partitions = {"0"}))
  public void listenGroupTopic_1_P1(
      @Payload String message) throws ItemAlreadyExistsException, InvalidDataException {

    KafkaRequestBody body = Helper.jsonDeserialize(message, KafkaRequestBody.class);
    log.info("Received Message in P1: " + message);

    switch (body.getRequestType()) {
      // UPDATE
      case Constant.UPDATE_EMPLOYEE:
        EmployeeBody data = Helper.jsonDeserialize(body.getData(),
            EmployeeBody.class);
        // if employee existed => update to db
        if (employeeService.existed(data.getEmpId())) {
          data.setNote("update by kafka p1");
          employeeService.update(data);
          log.info("update by kafka p1");
        }
        // if not exist => create new employee
        else {
          data.setNote("create by kafka p1");
          employeeService.create(data);
          log.info("create by kafka p1");
        }
        break;
      // DELETE
      case Constant.DELETE_EMPLOYEE:
        deleteEmployee(body.getData());
        log.info("delete by kafka p1");
        break;
      default:
        break;
    }
  }

  // Partition 2
  @Timed(value = "kafka.listener.topic1.partition2")
  @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_1, partitions = {"1"}))
  public void listenGroupTopic_1_P2(@Payload String message) {
    KafkaRequestBody body = Helper.jsonDeserialize(message, KafkaRequestBody.class);
    log.info("Received Message in P2: " + message);

    switch (body.getRequestType()) {
      // UPDATE
      case Constant.UPDATE_EMPLOYEE:
        EmployeeBody data = Helper.jsonDeserialize(body.getData(), EmployeeBody.class);
        // if existed => update to db
        if (employeeService.existed(data.getEmpId())) {
          data.setNote("update by kafka p2");
          employeeService.update(data);
          log.info("update by kafka p2");
        }
        // if not existed => publish to topic 2
        else {
          kafkaTemplate.send(Constant.TOPIC_2,
              "Topic 1 Partition 2: can't update the employee " + data
                  .getEmpId() + " because it isn't existed");
        }
        break;
      // DELETE
      case Constant.DELETE_EMPLOYEE:
        deleteEmployee(body.getData());
        log.info("delete by kafka p2");
        break;
      default:
        break;
    }
  }

  private void deleteEmployee(String value) {
    Integer id = Integer.valueOf(value);
    // Delete if existed
    if (employeeService.existed(id)) {
      employeeService.delete(id);
      log.info("Deleted");
      return;
    }

    // Publish message to topic 2
    log.info("Publish message to topic 2");
    kafkaTemplate.send(Constant.TOPIC_2, "Can't delete employee " + value
        + ", because it is not existed");
  }
}