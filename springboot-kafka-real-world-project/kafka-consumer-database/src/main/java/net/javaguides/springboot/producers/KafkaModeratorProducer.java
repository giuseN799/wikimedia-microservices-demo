package net.javaguides.springboot.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.dtos.ModerationRequestDto;

@Service
public class KafkaModeratorProducer {
  private final String topic = "wikimedia_moderator_queue";
  
  private KafkaTemplate<String, ModerationRequestDto> kafkaTemplate;

  public KafkaModeratorProducer(KafkaTemplate<String, ModerationRequestDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(ModerationRequestDto wikiTextChangeDto){
    kafkaTemplate.send(topic, wikiTextChangeDto);
  }
}
