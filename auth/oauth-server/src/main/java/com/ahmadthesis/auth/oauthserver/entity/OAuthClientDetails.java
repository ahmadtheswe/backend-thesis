package com.ahmadthesis.auth.oauthserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "oauth_client_details", schema = "public")
@Data
public class OAuthClientDetails {

  @Id
  @Column(name = "client_id")
  private String clientId;

  private String resourceIds;
  private String clientSecret;
  private String scope;
  private String authorizedGrantTypes;
  private String webServerRedirectUri;
  private String authorities;
  private Integer accessTokenValidity;
  private Integer refreshTokenValidity;
  private String additionalInformation;
  private String autoapprove;
}

