package com.rasha.travel_assistant.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OpenAiClient {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1")
                .build();
    }

    @Retry(name = "openAiApi")
    @CircuitBreaker(name = "openAiApi", fallbackMethod = "openAiFallback")
    public Mono<String> getTravelAdvice(String city, String weatherSummary) {

        String requestBody = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a helpful travel assistant."
                    },
                    {
                      "role": "user",
                      "content": "Give short travel advice for %s based on this weather: %s"
                    }
                  ]
                }
                """.formatted(city, weatherSummary);

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.out.println(error.getMessage()))
                .onErrorResume(error ->
                        Mono.just(
                                "AI travel advice is currently unavailable. " +
                                        error.getMessage()
                        )
                );
    }

    public Mono<String> openAiFallback(
            String city,
            String weatherSummary,
            Throwable throwable
    ) {
        return Mono.just(
                "AI travel advice is currently unavailable. " +
                        "Please check local travel recommendations for " + city
        );
    }
}