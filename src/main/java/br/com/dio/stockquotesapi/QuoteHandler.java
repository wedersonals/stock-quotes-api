package br.com.dio.stockquotesapi;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class QuoteHandler {
    private final QuoteRepository quoteRepository;

    public Mono<ServerResponse> getAll(ServerRequest req) {
        var quotes = quoteRepository.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(quotes, Quote.class);
    }

    public Mono<ServerResponse> getLastQuote(ServerRequest req) {
        var quote = quoteRepository.findAll().last();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(quote, Quote.class);
    }
}