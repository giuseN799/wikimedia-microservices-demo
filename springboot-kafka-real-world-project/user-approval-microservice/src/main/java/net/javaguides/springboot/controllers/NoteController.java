package net.javaguides.springboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.dtos.NoteRequest;
import net.javaguides.springboot.services.NoteService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createNote(@RequestBody NoteRequest request) {
        System.out.println("Post endpoints hit.");
        return noteService.saveNoteWithRetry(request)
            .map(result -> ResponseEntity.ok("Note saved"))
            .onErrorResume(ex -> Mono.just(ResponseEntity.status(500).body("Failed to save note")));
    }
}
