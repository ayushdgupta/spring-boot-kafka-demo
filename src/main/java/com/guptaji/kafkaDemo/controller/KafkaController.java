package com.guptaji.kafkaDemo.controller;

import com.guptaji.kafkaDemo.entity.Student;
import com.guptaji.kafkaDemo.service.StudentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/kafka")
public class KafkaController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private StudentProcessor studentProcessor;

    @PostMapping("/produceMessage")
    public ResponseEntity<?> publishMessage(@RequestBody Student student){
        Student savedStudent = studentProcessor.saveAndPublishStudentData(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.OK);
    }

    // here we created only one instance of consumer so only this will consume the data from all partitions
    // but if we want this code to consume the data from one partition only and other instances to consume from
    // other instances then we can simply copy-paste the same piece of code and paste it here again with different
    // function name
    @KafkaListener(topics = "${student.kafka.topic}")
    public void consumeMessage(String message){
        LOG.info("Consumer 1 Consumed the message {}", message);
    }

    // second instance which consume the message
    @KafkaListener(topics = "${student.kafka.topic}")
    public void consumeMessageSecondInstance(String message){
        LOG.info("Consumer 2 Consumed the message {}", message);
    }
}
