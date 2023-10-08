//package com.ahmadthesis.auth.oauthserver.service;
//
//import com.ahmadthesis.auth.oauthserver.entity.OAuthClientDetails;
//import com.ahmadthesis.auth.oauthserver.repository.OAuthClientDetailsRepository;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OAuthClientDetailsService implements RegisteredClientRepository {
//
//  private final OAuthClientDetailsRepository clientDetailsRepository;
//  private final PasswordEncoder passwordEncoder;
//
//  public OAuthClientDetailsService(OAuthClientDetailsRepository clientDetailsRepository,
//      PasswordEncoder passwordEncoder) {
//    this.clientDetailsRepository = clientDetailsRepository;
//    this.passwordEncoder = passwordEncoder;
//  }
//
//  @Override
//  public void save(RegisteredClient registeredClient) {
//    OAuthClientDetails oAuthClientDetails = convertToRegisteredClientEntity(registeredClient);
//    clientDetailsRepository.save(oAuthClientDetails);
//  }
//
//  @Override
//  public RegisteredClient findById(String id) {
//    return clientDetailsRepository.findById(id)
//        .map(this::convertToRegisteredClient)
//        .orElse(null);
//  }
//
//  @Override
//  public RegisteredClient findByClientId(String clientId) {
//    return clientDetailsRepository.findByClientId(clientId)
//        .map(this::convertToRegisteredClient)
//        .orElse(null);
//  }
//
//  private OAuthClientDetails convertToRegisteredClientEntity(RegisteredClient registeredClient) {
//    OAuthClientDetails entity = new OAuthClientDetails();
//    entity.setClientId(registeredClient.getClientId());
//    entity.setClientSecret(registeredClient.getClientSecret());
//    entity.setScope(String.join(",", registeredClient.getScopes()));
//    entity.setAuthorizedGrantTypes(
//        String.join(",", registeredClient.getAuthorizationGrantTypes().toString()));
//    entity.setWebServerRedirectUri(String.join(",", registeredClient.getRedirectUris()));
//    // Add other properties if you need
//
//    // Always encode the client secret when saving to the DB
//    entity.setClientSecret(passwordEncoder.encode(registeredClient.getClientSecret()));
//
//    return entity;
//  }
//
//  private RegisteredClient convertToRegisteredClient(OAuthClientDetails entity) {
//    return RegisteredClient.withId(entity.getClientId())
//        .clientId(entity.getClientId())
//        .clientSecret(entity.getClientSecret())  // this will be the encoded secret
//        .scopes(scopes -> scopes.addAll(parseScopes(entity.getScope())))
//        .authorizationGrantTypes(authorizationGrantTypes ->
//            authorizationGrantTypes.addAll(parseGrantTypes(entity.getAuthorizedGrantTypes())))
//        .redirectUris(redirectUri ->
//            redirectUri.addAll(parseRedirectUris(entity.getWebServerRedirectUri())))
//        // Add other properties if you need
//        .build();
//  }
//
//  private Set<String> parseScopes(String scope) {
//    return new HashSet<>(Arrays.asList(scope.split(",")));
//  }
//
//  private Set<AuthorizationGrantType> parseGrantTypes(String grantTypes) {
//    String[] types = grantTypes.split(",");
//    Set<AuthorizationGrantType> grantTypeSet = new HashSet<>();
//    for (String type : types) {
//      grantTypeSet.add(new AuthorizationGrantType(type.trim()));
//    }
//    return grantTypeSet;
//  }
//
//  private List<String> parseRedirectUris(String redirectUris) {
//    return Arrays.asList(redirectUris.split(","));
//  }
//
//}
