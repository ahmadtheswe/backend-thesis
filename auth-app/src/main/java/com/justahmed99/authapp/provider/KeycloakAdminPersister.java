package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import com.justahmed99.authapp.business.Token;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakAdminPersister {

  UserRepresentation createRegularUser(UserRepresentation userRepresentation);

  UserRepresentation activateUser(String userId);

  Token login(Login login);

  Token refresh(String refreshToken);

  void logout(String userId);
}
