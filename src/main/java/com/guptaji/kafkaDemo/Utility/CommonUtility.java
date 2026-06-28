package com.guptaji.kafkaDemo.Utility;

import com.google.gson.Gson;
import com.guptaji.kafkaDemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtility {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtility.class);

    public static String convertStudentObject(Student student, Gson gson){
        String studnetString = gson.toJson(student);
        return studnetString;
    }
}
