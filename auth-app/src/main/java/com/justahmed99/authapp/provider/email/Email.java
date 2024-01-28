package com.justahmed99.authapp.provider.email;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Email {
  private String to;
  private String subject;
  private String content;
}
