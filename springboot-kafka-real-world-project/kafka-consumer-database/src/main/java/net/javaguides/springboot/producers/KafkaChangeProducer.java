package net.javaguides.springboot.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.dtos.WikiTextChangeDto;

@Service
public class KafkaChangeProducer {
  
  private KafkaTemplate<String, WikiTextChangeDto> kafkaTemplate;

  public KafkaChangeProducer(KafkaTemplate<String, WikiTextChangeDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(WikiTextChangeDto wikiTextChangeDto){
    kafkaTemplate.send("wikimedia_change_data", wikiTextChangeDto);
  }

}
