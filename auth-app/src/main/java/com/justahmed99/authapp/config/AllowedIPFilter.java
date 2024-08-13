package com.justahmed99.authapp.config;


import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
public class AllowedIPFilter implements WebFilter {

  @Value("${allowed.api.domain.list}")
  private String allowedDomainList;
  private List<String> allowedIps;

  @PostConstruct
  public void init() {
    List<String> allowedDomains = Arrays.asList(allowedDomainList.split(","));
    allowedIps = DomainResolver.resolveDomainsToIps(allowedDomains);
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String remoteAddr = String.valueOf(exchange.getRequest().getRemoteAddress().getAddress());

    if (allowedIps.contains(remoteAddr)) {
      return chain.filter(exchange);
    } else {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
  }
}
