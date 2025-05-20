package net.javaguides.springboot.producers;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
  
  @Bean
  public NewTopic sendApproveRejectTopic(){
    return TopicBuilder.name("change_approve_reject").build();
  }
  
}
