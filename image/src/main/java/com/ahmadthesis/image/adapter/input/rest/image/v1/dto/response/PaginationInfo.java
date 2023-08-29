package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Generated
@NoArgsConstructor
public class PaginationInfo {
  private int currentPage;
  private int pageSize;
  private String sortBy;
  private long totalPages;
  private long totalItems;
}
