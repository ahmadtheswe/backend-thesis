package com.ahmadthesis.auth.oauthserver.repository;

import com.ahmadthesis.auth.oauthserver.entity.OAuthClientDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsRepository extends JpaRepository<OAuthClientDetails, String> {
  Optional<OAuthClientDetails> findByClientId(String clientId);
}
