package net.javaguides.springboot.producers;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.dtos.ApproveRejectDto;

@Service
public class ModeratorResponseProducer {
  
  private KafkaTemplate<String, ApproveRejectDto> kafkaTemplate;

  public ModeratorResponseProducer(KafkaTemplate<String, ApproveRejectDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(ApproveRejectDto approveRejectDto){
    kafkaTemplate.send("change_approve_reject", approveRejectDto);
  }
}
