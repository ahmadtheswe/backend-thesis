package com.ahmadthesis.auth.oauthclient.event;

import com.ahmadthesis.auth.oauthclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

  private final User user;
  private final String applicationUrl;

  public RegistrationCompleteEvent(User user, String applicationUrl) {
    super(user);
    this.user = user;
    this.applicationUrl = applicationUrl;
  }
}
