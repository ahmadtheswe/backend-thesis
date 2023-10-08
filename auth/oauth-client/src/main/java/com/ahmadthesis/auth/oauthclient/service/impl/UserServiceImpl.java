package com.ahmadthesis.auth.oauthclient.service.impl;

import com.ahmadthesis.auth.oauthclient.entity.PasswordResetToken;
import com.ahmadthesis.auth.oauthclient.entity.User;
import com.ahmadthesis.auth.oauthclient.entity.VerificationToken;
import com.ahmadthesis.auth.oauthclient.model.UserRequest;
import com.ahmadthesis.auth.oauthclient.repository.PasswordResetTokenRepository;
import com.ahmadthesis.auth.oauthclient.repository.UserRepository;
import com.ahmadthesis.auth.oauthclient.repository.VerificationTokenRepository;
import com.ahmadthesis.auth.oauthclient.service.UserService;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository,
      VerificationTokenRepository verificationTokenRepository,
      PasswordResetTokenRepository passwordResetTokenRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.verificationTokenRepository = verificationTokenRepository;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User registerUser(UserRequest userModel) {
    User user = new User();
    user.setEmail(userModel.getEmail());
    user.setFirstName(userModel.getFirstName());
    user.setLastName(userModel.getLastName());
    user.setRole("USER");
    user.setPassword(passwordEncoder.encode(userModel.getPassword()));

    userRepository.save(user);
    return user;
  }

  @Override
  public void saveVerificationTokenForUser(String token, User user) {
    VerificationToken verificationToken
        = new VerificationToken(user, token);

    verificationTokenRepository.save(verificationToken);
  }

  @Override
  public String validateVerificationToken(String token) {
    VerificationToken verificationToken
        = verificationTokenRepository.findByToken(token);

    if (verificationToken == null) {
      return "invalid";
    }

    User user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();

    if ((verificationToken.getExpirationTime().getTime()
        - cal.getTime().getTime()) <= 0) {
      verificationTokenRepository.delete(verificationToken);
      return "expired";
    }

    user.setEnabled(true);
    userRepository.save(user);
    return "valid";
  }

  @Override
  public VerificationToken generateNewVerificationToken(String oldToken) {
    VerificationToken verificationToken
        = verificationTokenRepository.findByToken(oldToken);
    verificationToken.setToken(UUID.randomUUID().toString());
    verificationTokenRepository.save(verificationToken);
    return verificationToken;
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void createPasswordResetTokenForUser(User user, String token) {
    PasswordResetToken passwordResetToken
        = new PasswordResetToken(user,token);
    passwordResetTokenRepository.save(passwordResetToken);
  }

  @Override
  public String validatePasswordResetToken(String token) {
    PasswordResetToken passwordResetToken
        = passwordResetTokenRepository.findByToken(token);

    if (passwordResetToken == null) {
      return "invalid";
    }

    User user = passwordResetToken.getUser();
    Calendar cal = Calendar.getInstance();

    if ((passwordResetToken.getExpirationTime().getTime()
        - cal.getTime().getTime()) <= 0) {
      passwordResetTokenRepository.delete(passwordResetToken);
      return "expired";
    }

    return "valid";
  }

  @Override
  public Optional<User> getUserByPasswordResetToken(String token) {
    return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
  }

  @Override
  public void changePassword(User user, String newPassword) {
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  @Override
  public boolean checkIfValidOldPassword(User user, String oldPassword) {
    return passwordEncoder.matches(oldPassword, user.getPassword());
  }
}
