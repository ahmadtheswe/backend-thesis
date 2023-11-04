package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import com.justahmed99.authapp.business.Token;
import com.justahmed99.authapp.business.TokenConverter;
import com.nimbusds.jose.util.Pair;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakAdminProvider implements KeycloakAdminPersister {

  @Value("${keycloak-admin.server-url}")
  private String serverUrl;

  @Value("${keycloak-admin.realm}")
  private String realm;

  @Value("${keycloak-admin.admin-client-id}")
  private String adminClientId;

  @Value("${keycloak-admin.admin-client-secret}")
  private String adminClientSecret;

  @Value("${keycloak-admin.client-id}")
  private String clientId;

  @Value("${keycloak-admin.client-secret}")
  private String clientSecret;

  @Override
  public Pair<Boolean, Integer> createRegularUser(
      final UserRepresentation userRepresentation) {
    Keycloak keycloak = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(adminClientId)
        .clientSecret(adminClientSecret)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
        .build();
    final RealmResource realmResource = keycloak.realm(realm);
    final UsersResource usersResource = realmResource.users();
    final Response response = usersResource.create(userRepresentation);

    if (response.getStatus() == 409) {
      return Pair.of(false, 409);
    } else {
      final String userId = getUserId(response.getLocation());
      if (response.getStatus() == 201) {
        final RoleRepresentation regularRole =
            realmResource.roles().get("regular").toRepresentation();

        usersResource.get(userId).roles().realmLevel().add(List.of(regularRole));

        return Pair.of(true, 201);
      } else {
        keycloak.realm(realm).users().get(userId).remove();
        return Pair.of(false, 500);
      }
    }
  }

  private String getUserId(final URI location) {
    final String path = location.getPath();
    return path.substring(path.lastIndexOf("/") + 1);
  }

  @Override
  public Token login(final Login login) {
    Keycloak keycloak = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .username(login.getUsername())
        .password(login.getPassword())
        .grantType(OAuth2Constants.PASSWORD)
        .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
        .build();

    return TokenConverter.fromKeycloakToToken(keycloak);
  }

  @Override
  public void logout(final String userId) {
    Keycloak keycloak = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(adminClientId)
        .clientSecret(adminClientSecret)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
        .build();
    keycloak.realm(realm).users().get(userId).logout();
  }
}

