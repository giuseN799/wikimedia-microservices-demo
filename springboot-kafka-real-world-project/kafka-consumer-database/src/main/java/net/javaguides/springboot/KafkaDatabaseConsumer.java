package net.javaguides.springboot;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dtos.ModerationRequestDto;
import net.javaguides.springboot.dtos.WikiChangeDto;
import net.javaguides.springboot.entities.TransactionStatus;
import net.javaguides.springboot.entities.WikiChange;
import net.javaguides.springboot.producers.KafkaModeratorProducer;
import net.javaguides.springboot.repository.WikiChangeRepository;

@Service
@AllArgsConstructor
public class KafkaDatabaseConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

  // private KafkaTemplate<String, WikiTextChangeDto> kafkaTemplate;

  private KafkaTemplate<String, ModerationRequestDto> kafkaModTemplate;

  private WikiChangeRepository wikiChangeRepository;
  @KafkaListener(topics = "wikimedia_recentchange_json", groupId = "myGroup")
  public void consumer(WikiChangeDto wikiChangeDto){
    LOGGER.info(String.format("Event Message received -> %s", wikiChangeDto.toString()));

    UUID uuid = UUID.randomUUID();
    // Start Transaction

    WikiChange newChange = buildWikiChange(wikiChangeDto, uuid.toString());
    
    try { wikiChangeRepository.save(newChange);
    // Turn the complex super Json into orchestrated API JSON loads
    // Invoke the two producers create the WikiTextChangeDto
    // KafkaChangeProducer kafkaChangeProducer = new KafkaChangeProducer(kafkaTemplate);
    KafkaModeratorProducer kafkaModeratorProducer = new KafkaModeratorProducer(kafkaModTemplate);
    // WikiTextChangeDto wikiTextChangeDto = new WikiTextChangeDto(wikiChangeDto, uuid.toString());
    ModerationRequestDto modReviewRequestDto = new ModerationRequestDto(wikiChangeDto, uuid.toString());
    // kafkaChangeProducer.sendMessage(wikiTextChangeDto);
    kafkaModeratorProducer.sendMessage(modReviewRequestDto);    

    // // Save 
    newChange.setStatus(TransactionStatus.PENDING);
    wikiChangeRepository.saveAndFlush(newChange);
    } catch (Exception e){
      // Ignore Exceptions.
    }
    
  }
  private WikiChange buildWikiChange(WikiChangeDto wikiChangeDto, String uuid ) {
    return WikiChange.builder().status(TransactionStatus.STARTED)
                        .bot(wikiChangeDto.bot)
                        .comment(wikiChangeDto.comment)
                        .notifyUrl(wikiChangeDto.notifyUrl)
                        .userName(wikiChangeDto.user)
                        .id(wikiChangeDto.id)
                        .uuid(uuid)
                        .build();
                        
  }
}
