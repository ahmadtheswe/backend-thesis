package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import com.justahmed99.authapp.business.Token;
import com.nimbusds.jose.util.Pair;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakAdminPersister {

  Pair<Boolean, Integer> createRegularUser(final UserRepresentation userRepresentation);

  Token login(final Login login);

  void logout(final String userId);
}
