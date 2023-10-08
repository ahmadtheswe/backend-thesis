package com.ahmadthesis.auth.oauthclient.repository;

import com.ahmadthesis.auth.oauthclient.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}
