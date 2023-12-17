package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import com.justahmed99.authapp.business.Token;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakAdminPersister {

  UserRepresentation createRegularUser(final UserRepresentation userRepresentation);

  Token login(final Login login);

  Token refresh(final String refreshToken);

  void logout(final String userId);
}
