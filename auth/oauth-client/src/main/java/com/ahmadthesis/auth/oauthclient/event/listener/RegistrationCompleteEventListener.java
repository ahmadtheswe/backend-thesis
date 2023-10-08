package com.ahmadthesis.auth.oauthclient.event.listener;

import com.ahmadthesis.auth.oauthclient.entity.User;
import com.ahmadthesis.auth.oauthclient.event.RegistrationCompleteEvent;
import com.ahmadthesis.auth.oauthclient.service.UserService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
    ApplicationListener<RegistrationCompleteEvent> {

  private final UserService userService;

  public RegistrationCompleteEventListener(
      UserService userService
  ) {
    this.userService = userService;
  }

  @Override
  public void onApplicationEvent(RegistrationCompleteEvent event) {
    //Create the Verification Token for the User with Link
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.saveVerificationTokenForUser(token, user);
    //Send Mail to user
    String url =
        event.getApplicationUrl()
            + "/verify-registration?token="
            + token;

    //sendVerificationEmail()
    log.info("Click the link to verify your account: {}",
        url);
  }
}
