package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request;

import java.math.BigDecimal;
import lombok.*;
import org.springframework.http.codec.multipart.FilePart;

@AllArgsConstructor
@Builder
@Data
@Generated
@NoArgsConstructor
public class SaveImageRequest {
  private String id;
  private FilePart image;
  private String title;
  private Boolean isPublic;
  private String filename;
  private String mediaType;
  private String uploadDir;
  private Long priceIDR;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
