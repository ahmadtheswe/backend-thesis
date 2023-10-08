package com.ahmadthesis.auth.oauthclient.service;

import com.ahmadthesis.auth.oauthclient.entity.User;
import com.ahmadthesis.auth.oauthclient.entity.VerificationToken;
import com.ahmadthesis.auth.oauthclient.model.UserRequest;
import java.util.Optional;

public interface UserService {
  User registerUser(UserRequest userModel);

  void saveVerificationTokenForUser(String token, User user);

  String validateVerificationToken(String token);

  VerificationToken generateNewVerificationToken(String oldToken);

  User findUserByEmail(String email);

  void createPasswordResetTokenForUser(User user, String token);

  String validatePasswordResetToken(String token);

  Optional<User> getUserByPasswordResetToken(String token);

  void changePassword(User user, String newPassword);

  boolean checkIfValidOldPassword(User user, String oldPassword);
}
