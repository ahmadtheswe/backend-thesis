package com.ahmadthesis.auth.oauthserver.config;

import com.ahmadthesis.auth.oauthserver.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

  private final CustomAuthenticationProvider customAuthenticationProvider;

  public DefaultSecurityConfig(
      CustomAuthenticationProvider customAuthenticationProvider) {
    this.customAuthenticationProvider = customAuthenticationProvider;
  }

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests.anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .build();
  }

  @Autowired
  public void bindAuthenticationProvider(
      AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder
        .authenticationProvider(customAuthenticationProvider);
  }
}
