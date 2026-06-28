package com.guptaji.kafkaDemo.config;

import com.google.gson.Gson;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.resilience.annotation.EnableResilientMethods;

@Configuration
@EnableResilientMethods
public class CodeConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }

    // config to create a new topic in spring-boot
    @Bean
    public NewTopic createTopic(){
        return new NewTopic("custom-topic", 5, (short) 2);
    }
}
