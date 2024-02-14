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
    basePackages = "com.ahmadthesis.image.adapter.output.persistence.postgresql.payment",
    entityOperationsRef = "paymentEntityTemplate")
public class PaymentDatabaseConfiguration {

  @Value("${spring.db-connection.payment}")
  private String paymentDbUrl;

  @Bean
  @Qualifier("paymentConnectionFactory")
  public ConnectionFactory paymentConnectionFactory() {
    return ConnectionFactories.get(paymentDbUrl);
  }

  @Bean
  @Qualifier("paymentEntityTemplate")
  public R2dbcEntityOperations paymentEntityTemplate(
      @Qualifier("paymentConnectionFactory") ConnectionFactory connectionFactory) {

    DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(
        PostgresDialect.INSTANCE);
    DatabaseClient databaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .bindMarkers(PostgresDialect.INSTANCE.getBindMarkersFactory())
        .build();

    return new R2dbcEntityTemplate(databaseClient, strategy);
  }

}
