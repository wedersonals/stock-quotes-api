package br.com.dio.stockquotesapi;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends ReactiveCrudRepository<Quote, Long> {
}