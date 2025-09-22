package com.linkedin.postsService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    

    @Bean
    public NewTopic postCreated(){
        Map<String, String> configs = new HashMap<>();
        configs.put("retention.ms", "172800000"); // 2 days
        configs.put("cleanup.policy", "delete");
        return new NewTopic("post_created_topic", 3,(short)2).configs(configs);
    }
    @Bean
    public NewTopic postLiked(){
        Map<String, String> configs = new HashMap<>();
        configs.put("retention.ms", "43200000"); // 12 hours
        configs.put("cleanup.policy", "delete");
        return new NewTopic("post_liked_topic", 3,(short)2).configs(configs);
    }

}
