package br.com.dio.stockquotesapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public CorsWebFilter addCorsMappings() {
        return new CorsWebFilter(exchange -> new CorsConfiguration().applyPermitDefaultValues());
    }

    @Bean
    public RouterFunction<ServerResponse> routeQuotes(QuoteHandler quoteHandler) {
        return route(GET("/quotes"), quoteHandler::getAll)
                .andRoute(GET("/last-quote"), quoteHandler::getLastQuote);
    }
}