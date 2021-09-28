package br.com.dio.stockquotesapi;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Slf4j
@Configuration
public class DatabaseConfig {

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory){
        log.info("creating schema");
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator compositeDatabasePopulator = new CompositeDatabasePopulator();
        compositeDatabasePopulator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        initializer.setDatabasePopulator(compositeDatabasePopulator);
        log.info("schema created");
        return initializer;
    }

}
