package net.javaguides.springboot;


import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;

import net.javaguides.springboot.dtos.WikiChangeDto;

@Service
public class WikimediaChangesProducer {

  private KafkaTemplate<String, WikiChangeDto> kafkaTemplate;

  public WikimediaChangesProducer(KafkaTemplate<String, WikiChangeDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage() throws InterruptedException{
    String topic = "wikimedia_recentchange_json";
    //To read real time stream data from wikimedia, we use event source
    BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
    String url = "https://stream.wikimedia.org/v2/stream/recentchange";

    BackgroundEventSource bes = new BackgroundEventSource.Builder(eventHandler,
      new EventSource.Builder(
        ConnectStrategy.http(URI.create(url))
          .connectTimeout(5, TimeUnit.SECONDS)
          // connectTimeout and other HTTP options are now set through
          // HttpConnectStrategy
        )
    )
    .threadPriority(Thread.MAX_PRIORITY)
      // threadPriority, and other options related to worker threads,
      // are now properties of BackgroundEventSource
    .build();
     bes.start();

    TimeUnit.SECONDS.sleep(3);
  }

}
