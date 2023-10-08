package com.ahmadthesis.auth.oauthclient.controller;

import com.ahmadthesis.auth.oauthclient.dto.response.DataResponse;
import com.ahmadthesis.auth.oauthclient.entity.User;
import com.ahmadthesis.auth.oauthclient.entity.VerificationToken;
import com.ahmadthesis.auth.oauthclient.event.RegistrationCompleteEvent;
import com.ahmadthesis.auth.oauthclient.model.PasswordRequest;
import com.ahmadthesis.auth.oauthclient.model.UserRequest;
import com.ahmadthesis.auth.oauthclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegistrationController {

  private final UserService userService;

  private final ApplicationEventPublisher publisher;

  public RegistrationController(
      UserService userService,
      ApplicationEventPublisher publisher
  ) {
    this.userService = userService;
    this.publisher = publisher;

  }

  @PostMapping("/register")
  public ResponseEntity<DataResponse<String>> registerUser(@RequestBody UserRequest userRequest,
      final HttpServletRequest request) {
    User user = userService.registerUser(userRequest);
    publisher.publishEvent(new RegistrationCompleteEvent(
        user,
        applicationUrl(request)
    ));
    return new ResponseEntity<>(
        new DataResponse<>(List.of("Register success"), null), HttpStatus.OK
    );
  }

  @GetMapping("/verify-registration")
  public ResponseEntity<DataResponse<String>> verifyRegistration(
      @RequestParam("token") String token) {
    String result = userService.validateVerificationToken(token);
    if (result.equalsIgnoreCase("valid")) {
      return new ResponseEntity<>(
          new DataResponse<>(List.of("User Verified Successfully"), null), HttpStatus.OK
      );
    }
    return new ResponseEntity<>(
        new DataResponse<>(List.of("Bad User"), null), HttpStatus.UNAUTHORIZED
    );
  }


  @GetMapping("/resend-verify-token")
  public String resendVerificationToken(@RequestParam("token") String oldToken,
      HttpServletRequest request) {
    VerificationToken verificationToken
        = userService.generateNewVerificationToken(oldToken);
    User user = verificationToken.getUser();
    resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
    return "Verification Link Sent";
  }

  @PostMapping("/resetPassword")
  public String resetPassword(@RequestBody PasswordRequest passwordRequest,
      HttpServletRequest request) {
    User user = userService.findUserByEmail(passwordRequest.getEmail());
    String url = "";
    if (user != null) {
      String token = UUID.randomUUID().toString();
      userService.createPasswordResetTokenForUser(user, token);
      url = passwordResetTokenMail(user, applicationUrl(request), token);
    }
    return url;
  }

  @PostMapping("/save-password")
  public String savePassword(@RequestParam("token") String token,
      @RequestBody PasswordRequest passwordRequest) {
    String result = userService.validatePasswordResetToken(token);
    if (!result.equalsIgnoreCase("valid")) {
      return "Invalid Token";
    }
    Optional<User> user = userService.getUserByPasswordResetToken(token);
    if (user.isPresent()) {
      userService.changePassword(user.get(), passwordRequest.getNewPassword());
      return "Password Reset Successfully";
    } else {
      return "Invalid Token";
    }
  }

  @PostMapping("/change-password")
  public String changePassword(@RequestBody PasswordRequest passwordRequest) {
    User user = userService.findUserByEmail(passwordRequest.getEmail());
    if (!userService.checkIfValidOldPassword(user, passwordRequest.getOldPassword())) {
      return "Invalid Old Password";
    }
    //Save New Password
    userService.changePassword(user, passwordRequest.getNewPassword());
    return "Password Changed Successfully";
  }

  private String passwordResetTokenMail(User user, String applicationUrl, String token) {
    String url =
        applicationUrl
            + "/savePassword?token="
            + token;

    //sendVerificationEmail()
    log.info("Click the link to Reset your Password: {}",
        url);
    return url;
  }


  private void resendVerificationTokenMail(User user, String applicationUrl,
      VerificationToken verificationToken) {
    String url =
        applicationUrl
            + "/verify-registration?token="
            + verificationToken.getToken();

    //sendVerificationEmail()
    log.info("Click the link to verify your account: {}",
        url);
  }


  private String applicationUrl(HttpServletRequest request) {
    return "http://" +
        request.getServerName() +
        ":" +
        request.getServerPort() +
        request.getContextPath();
  }
}
