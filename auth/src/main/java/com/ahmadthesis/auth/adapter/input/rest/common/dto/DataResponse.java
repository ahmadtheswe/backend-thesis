package com.ahmadthesis.auth.adapter.input.rest.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse<T> {
  private T data;
  private List<String> messages;
}
