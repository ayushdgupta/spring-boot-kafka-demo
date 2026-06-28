package com.guptaji.kafkaDemo.publisher;

import com.google.gson.Gson;
import com.guptaji.kafkaDemo.Utility.CommonUtility;
import com.guptaji.kafkaDemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class StudentPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(StudentPublisher.class);

    @Autowired
    private Gson gson;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${student.kafka.topic}")
    private String topic;

    public boolean publishStudent(Student student){
        LOG.info("publishing student data");
        String studentData = CommonUtility.convertStudentObject(student, gson);
        CompletableFuture<SendResult<String, String>> send =
                kafkaTemplate.send(topic, student.getRollNo().toString(), studentData);

        CompletableFuture<Boolean> resultIsSent = send
                .thenApply((result) -> {
                    long offset = result.getRecordMetadata().offset();
                    int partition = result.getRecordMetadata().partition();
                    LOG.info("Result sent to offset {} of partition {}", offset, partition);
                    return true;
                })
                .exceptionally(e -> {
                    LOG.error("error occurred while publishing message {}", e.getMessage(), e);
                    return false;
                });

        return resultIsSent.join();
    }
}
