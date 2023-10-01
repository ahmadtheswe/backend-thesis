package com.ahmadthesis.auth.keycloak_client.dao;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {
  private T data;
  private List<String> messages = new ArrayList<>();
}
