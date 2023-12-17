package com.ahmadthesis.payment.config;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransCoreApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfig {
  @Value("${midtrans.server-key}")
  private String serverKey;

  @Value("${midtrans.client-key}")
  private String clientKey;

  @Bean
  public MidtransCoreApi midtransCoreApi() {
    final Config config = Config.builder()
        .setServerKey(serverKey)
        .setClientKey(clientKey)
        .setIsProduction(false)
        .build();

    return new ConfigFactory(config).getCoreApi();
  }
}
