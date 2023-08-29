package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Generated
@NoArgsConstructor
public class PaginationRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
}
