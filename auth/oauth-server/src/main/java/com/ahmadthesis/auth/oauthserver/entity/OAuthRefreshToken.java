package com.ahmadthesis.auth.oauthserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "oauth_refresh_token", schema = "public")
@Data
public class OAuthRefreshToken {

  @Id
  private String tokenId;

  private byte[] token;
  private byte[] authentication;
}

