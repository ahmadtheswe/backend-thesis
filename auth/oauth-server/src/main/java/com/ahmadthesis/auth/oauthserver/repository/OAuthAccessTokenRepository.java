package com.ahmadthesis.auth.oauthserver.repository;

import com.ahmadthesis.auth.oauthserver.entity.OAuthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthAccessTokenRepository extends JpaRepository<OAuthAccessToken, String> {}