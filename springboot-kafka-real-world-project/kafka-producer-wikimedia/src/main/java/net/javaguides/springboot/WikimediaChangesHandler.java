package net.javaguides.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

import net.javaguides.springboot.dtos.WikiChangeDto;

public class WikimediaChangesHandler implements BackgroundEventHandler{

  private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
  private KafkaTemplate<String, WikiChangeDto> kafkaTemplate;
  private String topic;
  
  public WikimediaChangesHandler(KafkaTemplate<String, WikiChangeDto> kafkaTemplate, String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
  }

  @Override
  public void onClosed() throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onComment(String comment) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onError(Throwable t) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onMessage(String event, MessageEvent messageEvent) throws Exception {
    LOGGER.info(String.format("event data -> %s", messageEvent.getData()));
    // messageEvent.
    WikiChangeDto data = JsonConverter.convert(messageEvent.getData());
    WikiChangeFull wikiChangeFull = new WikiChangeFull();
    wikiChangeFull.setChange(data);
    wikiChangeFull.setEvent(messageEvent.getEventName());
    // wikiChangeFull.setId(messageEvent.getLastEventId());
    kafkaTemplate.send(topic, data);
  }

  @Override
  public void onOpen() throws Exception {
    // TODO Auto-generated method stub
    
  }
  
  
}
