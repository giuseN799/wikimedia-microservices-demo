package net.javaguides.springboot;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
  
  @Bean
  public NewTopic sendToDatabaseTopic(){
    return TopicBuilder.name("wikimedia_change_data").build();
  }

  @Bean
  public NewTopic putIntoModeratorQueueTopic(){
    return TopicBuilder.name("wikimedia_moderator_queue").build();
  }
  
}
