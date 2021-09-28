package br.com.dio.stockquotesapi;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class QuoteGenerator {
    private final QuoteRepository quoteRepository;

    @PostConstruct
    public void init() {
        Flux.generate(() -> {
            Quote initialQuote = initialQuote();
            return Tuples.of(initialQuote, createNewQuote(initialQuote));
        }, (state, sink) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.next(state.getT1());
            return Tuples.of(state.getT2(), createNewQuote(state.getT1()));
        }).delaySubscription(Duration.ofSeconds(3)).subscribe();
    }

    private Quote createNewQuote(Quote previosQuote) {
        var quote = Quote.builder()
                .openValue(previosQuote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .closeValue(previosQuote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .symbol(previosQuote.getSymbol())
                .timestamp(LocalDateTime.now())
                .build();
        quoteRepository.save(quote).subscribe();
        return quote;
    }

    private Quote initialQuote() {
        var quote = Quote.builder()
                .openValue(0.2)
                .closeValue(0.2)
                .symbol("TESTE")
                .timestamp(LocalDateTime.now())
                .build();
        quoteRepository.save(quote).subscribe(log::info);
        return quote;
    }
//    @PostConstruct
//    public void init() {
//        quoteRepository.save(Quote.builder()
//                .openValue(0.2)
//                .closeValue(0.2)
//                .symbol("TESTE")
//                .timestamp(LocalDateTime.now())
//                .build())
//                .delaySubscription(Duration.ofSeconds(1))
//                .subscribe(
//                        f -> quoteRepository.findAll()
//                                .subscribe(
//                                        q -> log.info("quote: {}", q),
//                                        ex -> log.error("erro: {}", ex.getMessage())
//                                ),
//                        ex -> log.error("erro: {}", ex.getMessage())
//                );
//    }
//    @PostConstruct
//    public void init() {
//        Flux.generate(() -> {
//                    log.info("Starting data insertion");
//                    return initialQuote();
//                }, (state, sink) -> {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    sink.next(state);
//                    return createNewQuote(state);
//                })
//                .delaySubscription(Duration.ofMillis(3000))
//                .subscribe();
//    }
//
//    private Quote createNewQuote(Quote previousQuote) {
//        var newQuote = Quote.builder()
//                .symbol(previousQuote.getSymbol())
//                .openValue(previousQuote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
//                .closeValue(previousQuote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
//                .timestamp(LocalDateTime.now())
//                .build();
//        repository.save(newQuote).subscribe(log::info);
//        return newQuote;
//    }
//
//    private Quote initialQuote() {
//        var quote = Quote.builder()
//                .openValue(0.2)
//                .closeValue(0.2)
//                .symbol("TESTE")
//                .timestamp(LocalDateTime.now())
//                .build();
//        repository.save(quote).subscribe(log::info);
//        return quote;
//    }
}