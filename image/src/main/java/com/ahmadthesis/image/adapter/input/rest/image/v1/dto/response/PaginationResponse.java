package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Generated
@NoArgsConstructor
public class PaginationResponse <T>{
    private List<T> data;
    private PaginationInfo paginationInfo;
    private List<String> messages;
}
