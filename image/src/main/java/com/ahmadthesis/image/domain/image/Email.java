package com.ahmadthesis.image.domain.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Email {
  private String to;
  private String subject;
  private String content;
}
