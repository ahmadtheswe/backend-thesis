package com.ahmadthesis.image.utils.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcRepositories(
    basePackages = "com.ahmadthesis.image.adapter.output.persistence.postgresql.image",
    entityOperationsRef = "imageEntityTemplate")
public class ImageDatabaseConfiguration {


  @Value("${spring.db-connection.image}")
  private String imageDbUrl;

  @Bean
  @Qualifier("imageConnectionFactory")
  public ConnectionFactory imageConnectionFactory() {
    return ConnectionFactories.get(imageDbUrl);
  }

  @Bean
  @Qualifier("imageEntityTemplate")
  public R2dbcEntityOperations imageEntityTemplate(
      @Qualifier("imageConnectionFactory") ConnectionFactory connectionFactory) {

    DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(
        PostgresDialect.INSTANCE);
    DatabaseClient databaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .bindMarkers(PostgresDialect.INSTANCE.getBindMarkersFactory())
        .build();

    return new R2dbcEntityTemplate(databaseClient, strategy);
  }
}
