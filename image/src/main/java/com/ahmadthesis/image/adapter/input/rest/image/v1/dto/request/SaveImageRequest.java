package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request;

import lombok.*;
import org.springframework.http.codec.multipart.FilePart;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
@Builder
public class SaveImageRequest {
    private FilePart image;
    private String title;
    private Boolean isPublic;
    private String filename;
    private String mediaType;
}
