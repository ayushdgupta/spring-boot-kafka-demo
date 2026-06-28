package com.guptaji.kafkaDemo.service;

import com.guptaji.kafkaDemo.entity.Student;
import com.guptaji.kafkaDemo.repository.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class StudentDbHandler {

    private static final Logger LOG = LoggerFactory.getLogger(StudentDbHandler.class);

    @Autowired
    private StudentRepo studentRepo;

    @Retryable(maxRetries = 3, delay = 1000)
    public Student persistData(Student student) {
        Student savedStudent = studentRepo.save(student);
        LOG.info("Saved student {} in db", student);
        return savedStudent;
    }
}
