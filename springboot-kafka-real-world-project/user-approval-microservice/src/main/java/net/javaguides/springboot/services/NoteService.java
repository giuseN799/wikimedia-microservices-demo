package net.javaguides.springboot.services;

import java.time.Duration;
import java.util.Random;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.dtos.NoteRequest;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class NoteService {

    private final Random random = new Random();

    public Mono<Void> saveNoteWithRetry(NoteRequest request) {
        return Mono.<Void>defer(() -> simulateRemoteSave(request))
            .retryWhen(jitteredBackoffRetry(5, Duration.ofSeconds(1)))
            .then();
    }

    private Retry jitteredBackoffRetry(int maxRetries, Duration baseDelay) {
        return Retry.backoff(maxRetries, baseDelay)
            .jitter(0.3) // 30% random +/- variation on each backoff
            .doBeforeRetry(retrySignal -> 
                System.out.println("Retrying due to: " + retrySignal.failure().getMessage())
            );
    }

    // Simulate a remote failure-prone operation
    private Mono<Void> simulateRemoteSave(NoteRequest request) {
        if (random.nextDouble() < 0.7) { // 70% failure rate
            return Mono.error(new RuntimeException("Simulated failure"));
        }
        System.out.println("Saved note: " + request);
        return Mono.empty();
    }
}

