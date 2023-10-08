package com.ahmadthesis.auth.oauthserver.repository;

import com.ahmadthesis.auth.oauthserver.entity.OAuthRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRefreshTokenRepository extends JpaRepository<OAuthRefreshToken, String> {}
