package com.guptaji.kafkaDemo.service;

import com.guptaji.kafkaDemo.entity.Student;
import com.guptaji.kafkaDemo.publisher.StudentPublisher;
import com.guptaji.kafkaDemo.repository.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(StudentProcessor.class);

    @Autowired
    private StudentDbHandler studentDbHandler;

    @Autowired
    private StudentPublisher studentPublisher;
    
    public Student saveAndPublishStudentData(Student student){
        Student persistedStudent = studentDbHandler.persistData(student);
        boolean isPublished = studentPublisher.publishStudent(student);
        if (persistedStudent != null){
            if (isPublished){
                LOG.info("Student saved into db and sent to kafka successfully");
            } else {
                LOG.info("Student saved into db but not sent to kafka");
            }
        } else {
            LOG.info("Student not saved into db");
            throw new RuntimeException("Student not saved into DB");
        }
        return persistedStudent;
    }
}
