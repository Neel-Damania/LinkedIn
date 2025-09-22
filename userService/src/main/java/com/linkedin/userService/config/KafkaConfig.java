package com.linkedin.userService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userCreated(){
        Map<String, String> configs = new HashMap<>();
        configs.put("retention.ms", "172800000"); // 2 days
        configs.put("cleanup.policy", "delete");
        return new NewTopic("user_created_topic", 3,(short)1).configs(configs);
    }

}
