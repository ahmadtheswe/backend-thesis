package com.ahmadthesis.image.adapter.input.rest.image.v1.mapper;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.global.utils.dates.DateUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ImageMapper {
  public static Image mapRequestToImage(SaveImageRequest saveImageRequest) {
    return Image.builder()
            .id(UUID.randomUUID().toString())
            .uploaderId(UUID.randomUUID().toString())
            .title(saveImageRequest.getTitle())
            .mediaType(saveImageRequest.getMediaType())
            .filename(saveImageRequest.getFilename())
            .originalImageDir(saveImageRequest.getUploadDir())
            .createdAt(DateUtils.now())
            .isPublic(saveImageRequest.getIsPublic())
            .build();
  }
}
