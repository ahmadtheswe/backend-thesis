package com.ahmadthesis.auth.oauthserver.repository;

import com.ahmadthesis.auth.oauthserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
}
