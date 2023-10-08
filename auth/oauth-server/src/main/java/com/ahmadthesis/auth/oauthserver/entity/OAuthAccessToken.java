package com.ahmadthesis.auth.oauthserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "oauth_access_token", schema = "public")
@Data
public class OAuthAccessToken {

  @Id
  private String tokenId;

  private byte[] token;
  private String authenticationId;
  private String userName;
  private String clientId;
  private byte[] authentication;
  private String refreshToken;
}
