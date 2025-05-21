package net.javaguides.springboot;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import net.javaguides.springboot.dtos.NoteRequest;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class NoteClient {

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public NoteClient(WebClient.Builder builder, CircuitBreaker circuitBreaker) {
        this.webClient = builder.baseUrl("http://localhost:8082").build();
        this.circuitBreaker = circuitBreaker;
    }

    public Mono<Void> sendNoteWithRetry(NoteRequest noteRequest) {
        return webClient.post()
                .uri("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(noteRequest)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker)) // <- Circuit breaker here
                .retryWhen(jitteredBackoffRetry(5, Duration.ofSeconds(1)))
                .doOnNext(body -> System.out.println("Response: " + body))
                .onErrorResume(e -> {
                    System.err.println("Final failure or circuit open: " + e.getMessage());
                    return fallbackMono(); // Fallback logic
                })
                .then();
    }

    private Mono<String> fallbackMono() {
        return Mono.just("Fallback: service unavailable");
    }



    private Retry jitteredBackoffRetry(int maxRetries, Duration baseDelay) {
        return Retry.backoff(maxRetries, baseDelay)
                .jitter(0.4) // 40% ± jitter
                .doBeforeRetry(retrySignal -> 
                    System.out.println("Retrying request due to: " + retrySignal.failure().getMessage())
                );
    }
}
