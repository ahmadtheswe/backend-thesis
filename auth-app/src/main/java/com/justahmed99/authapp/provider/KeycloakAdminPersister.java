package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import java.util.Map;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakAdminPersister {
  Boolean createRegularUser(final UserRepresentation userRepresentation);
  Map<String, String> login(final Login login);
  void logout(final String userId);
}
