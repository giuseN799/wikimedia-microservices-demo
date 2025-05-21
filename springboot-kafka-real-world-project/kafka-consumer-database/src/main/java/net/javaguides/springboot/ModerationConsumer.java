package net.javaguides.springboot;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dtos.ApproveRejectDto;
import net.javaguides.springboot.dtos.Note;
import net.javaguides.springboot.dtos.NoteRequest;
import net.javaguides.springboot.dtos.WikiChangeDto;
import net.javaguides.springboot.entities.TransactionStatus;
import net.javaguides.springboot.entities.WikiChange;
import net.javaguides.springboot.repository.WikiChangeRepository;

@Service
@AllArgsConstructor
public class ModerationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
    
    private WikiChangeRepository wikiChangeRepository;

    @KafkaListener(topics = "change_approve_reject", groupId = "myGroup")
    public void consumer(ApproveRejectDto modDecision){
        LOGGER.info(String.format("Moderation Decision Received -> %s", modDecision.toString()));
        
        // Update the wiki_change db
        Optional<WikiChange> change = wikiChangeRepository.findById(modDecision.getUuid());
        if(!change.isPresent()) return;
        change.get().setStatus(TransactionStatus.valueOf(modDecision.getStatus().toUpperCase()));
        wikiChangeRepository.save(change.get());
        // Send a POST To Notes Microservice
        NoteClient webClient = new NoteClient(WebClient.builder());
        NoteRequest noteRequest = new NoteRequest();
        Note note = new Note();
        note.setAuthor(change.get().getUserName());
        note.setContent(change.get().getComment());
        noteRequest.setNote(note);
        noteRequest.setUuid(change.get().getUuid());
        webClient.sendNoteWithRetry(noteRequest);
    }
}
