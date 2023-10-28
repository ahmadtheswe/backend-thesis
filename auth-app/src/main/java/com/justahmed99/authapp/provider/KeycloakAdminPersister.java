package com.justahmed99.authapp.provider;

import com.justahmed99.authapp.business.Login;
import java.util.Map;
import org.keycloak.representations.idm.UserRepresentation;
import reactor.core.publisher.Mono;

public interface KeycloakAdminPersister {
  Boolean createRegularUser(UserRepresentation userRepresentation);
  Map<String, String> login( Login login);
}
