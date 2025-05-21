package net.javaguides.springboot;

import java.time.Duration;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import net.javaguides.springboot.dtos.NoteRequest;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class NoteClient {

    private final WebClient webClient;

    public NoteClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8082").build();
    }

    public Mono<Void> sendNoteWithRetry(NoteRequest noteRequest) {
        return webClient.post()
                .uri("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(noteRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(body -> System.out.println("Response: " + body))
                .retryWhen(jitteredBackoffRetry(5, Duration.ofSeconds(1)))
                .then();
    }

    private Retry jitteredBackoffRetry(int maxRetries, Duration baseDelay) {
        return Retry.backoff(maxRetries, baseDelay)
                .jitter(0.4) // 40% ± jitter
                .doBeforeRetry(retrySignal -> 
                    System.out.println("Retrying request due to: " + retrySignal.failure().getMessage())
                );
    }
}
