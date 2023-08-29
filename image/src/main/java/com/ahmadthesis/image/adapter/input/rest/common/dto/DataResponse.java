package com.ahmadthesis.image.adapter.input.rest.common.dto;

import lombok.*;

import java.util.List;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse<T> {
  private T data;
  private List<String> messages;
}
