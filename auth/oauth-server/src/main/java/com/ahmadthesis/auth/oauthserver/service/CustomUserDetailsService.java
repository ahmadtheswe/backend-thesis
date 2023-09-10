package com.ahmadthesis.auth.oauthserver.service;

import com.ahmadthesis.auth.oauthserver.entity.UserEntity;
import com.ahmadthesis.auth.oauthserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found!");
    }
    return new User(
        user.getEmail(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities(List.of(user.getRole()))
    );
  }

  private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority>  authorities = new ArrayList<>();
    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
    return authorities;
  }
}
