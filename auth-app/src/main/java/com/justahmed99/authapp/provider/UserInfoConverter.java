package com.justahmed99.authapp.provider;

import java.util.UUID;
import org.keycloak.representations.idm.UserRepresentation;

public class UserInfoConverter {

  public static UserInfoEntity toEntity(final UserInfo userInfo) {
    return UserInfoEntity.builder()
        .id(userInfo.getId() == null ? UUID.randomUUID().toString() : userInfo.getId())
        .username(userInfo.getUsername())
        .email(userInfo.getEmail())
        .role(userInfo.getRole())
        .createdAt(userInfo.getCreatedAt())
        .isActive(userInfo.getIsActive())
        .build();
  }

  public static UserInfo toDomain(final UserInfoEntity userInfoEntity) {
    return UserInfo.builder()
        .id(userInfoEntity.getId())
        .username(userInfoEntity.getUsername())
        .email(userInfoEntity.getEmail())
        .role(userInfoEntity.getRole())
        .createdAt(userInfoEntity.getCreatedAt())
        .isActive(userInfoEntity.getIsActive())
        .build();
  }

  public static UserInfo fromKeycloakUserRepresentation(
      final UserRepresentation userRepresentation) {
    return UserInfo.builder()
        .id(userRepresentation.getId())
        .username(userRepresentation.getUsername())
        .email(userRepresentation.getEmail())
        .role("REGULAR")
        .isActive(true)
        .build();
  }
}
