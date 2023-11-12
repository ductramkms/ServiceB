package com.example.ServiceB.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.ServiceB.constant.Constant;
import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.request.KafkaRequestBody;
import com.example.ServiceB.service.EmployeeService;
import com.example.ServiceB.util.ColorLog;
import com.example.ServiceB.util.Helper;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeListener {

    @Autowired
    private EmployeeService employeeService;

    @Timed(value = "kafka.listener.topic1.partition1")
    // Partition 1
    @KafkaListener(topicPartitions = @TopicPartition(topic = Constant.TOPIC_1, partitions = {"0"}))
    public void listenGroupTopic_1_P1(
            @Payload String message) throws ItemAlreadyExistsException, InvalidDataException {

        KafkaRequestBody body = Helper.jsonDeserialize(message, KafkaRequestBody.class);
        log.info(ColorLog.getLog("Received Message in P1: " + message));

        switch (body.getRequestType()) {
            // UPDATE
            case Constant.UPDATE_EMPLOYEE:
                EmployeeBody data = Helper.jsonDeserialize(body.getData(),
                        EmployeeBody.class);
                // if employee existed => update to db
                if (employeeService.existed(data.getEmpId())) {
                    data.setNote("update by kafka p1");
                    employeeService.update(data);
                    log.info(ColorLog.getLog("update by kafka p1"));
                }
                // if not exist => create new employee
                else {
                    data.setNote("create by kafka p1");
                    employeeService.create(data);
                    log.info(ColorLog.getLog("create by kafka p1"));
                }
                break;
            // DELETE
            case Constant.DELETE_EMPLOYEE:
                deleteEmployee(body.getData());
                log.info(ColorLog.getLog("delete by kafka p1"));
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
        log.info(ColorLog.getLog("Received Message in P2: " + message));

        switch (body.getRequestType()) {
            // UPDATE
            case Constant.UPDATE_EMPLOYEE:
                EmployeeBody data = Helper.jsonDeserialize(body.getData(), EmployeeBody.class);
                // if existed => update to db
                if (employeeService.existed(data.getEmpId())) {
                    employeeService.update(data);
                    data.setNote("update by kafka p2");
                    log.info(ColorLog.getLog("update by kafka p2"));
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
                log.info(ColorLog.getLog("delete by kafka p2"));
                break;
            default:
                break;
        }
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private void deleteEmployee(String value) {
        Integer id = Integer.valueOf(value);
        // Delete if existed
        if (employeeService.existed(id)) {
            employeeService.delete(id);
            log.info(ColorLog.getLog("Deleted"));
            return;
        }

        // Publish message to topic 2
        log.info(ColorLog.getLog("Publish message to topic 2"));
        kafkaTemplate.send(Constant.TOPIC_2, "Can't delete employee " + value
                + ", because it is not existed");
    }

}
