package com.justahmed99.authapp.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReturnDataDTO<T> {
  private T data;
  private List<String> messages;
}
