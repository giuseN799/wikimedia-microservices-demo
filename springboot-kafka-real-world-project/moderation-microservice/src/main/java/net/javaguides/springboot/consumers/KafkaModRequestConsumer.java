package net.javaguides.springboot.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dtos.ModerationRequestDto;
import net.javaguides.springboot.entities.ModerationRequest;
import net.javaguides.springboot.repository.ModerationRequestRepository;

@Service
@RequiredArgsConstructor
public class KafkaModRequestConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaModRequestConsumer.class);

    private final ModerationRequestRepository moderationRequestRepository;

    @KafkaListener(topics = "wikimedia_moderator_queue", groupId = "myGroup")
    public void consume(ModerationRequestDto moderationRequestDto){
        LOGGER.info(String.format("Mod request incoming -> %s", moderationRequestDto.toString()));
        ModerationRequest modRequest = new ModerationRequest();
        modRequest.setDiffURL(moderationRequestDto.getDiffURL());

        modRequest.setStatus("PENDING");

        modRequest.setUuid(moderationRequestDto.getUuid());

        modRequest.setTextChange(moderationRequestDto.getTextChange());
        moderationRequestRepository.save(modRequest);
        LOGGER.info(String.format("Saved mod request -> %s", modRequest.toString()));
    }
}
