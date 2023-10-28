package com.justahmed99.authapp.dto.converter;

import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import java.util.List;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import reactor.core.publisher.Mono;

public final class RegistrationConverter {
  public static Mono<UserRepresentation> fromRegistrationRequestDTO(final RegistrationRequestDTO dto) {
    final UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(dto.getUsername());
    userRepresentation.setFirstName(dto.getFirstName());
    userRepresentation.setLastName(dto.getLastName());
    userRepresentation.setEmail(dto.getEmail());
    userRepresentation.setEmailVerified(true);
    userRepresentation.setEnabled(true);

    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue(dto.getPassword());
    credentialRepresentation.setTemporary(false);
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

    userRepresentation.setCredentials(List.of(credentialRepresentation));

    return Mono.just(userRepresentation);
  }
}
