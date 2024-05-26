package com.ahmadthesis.image.adapter.input.rest.image.v1.mapper;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.ProductLevel;
import com.ahmadthesis.image.global.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ImageMapper {

  public static Image mapRequestToImage(final SaveImageRequest saveImageRequest,
      final String userId) {
    return Image.builder()
        .id(UUID.randomUUID().toString())
        .uploaderId(userId)
        .title(saveImageRequest.getTitle())
        .mediaType(saveImageRequest.getMediaType())
        .filename(saveImageRequest.getFilename())
        .originalImageDir(saveImageRequest.getUploadDir())
        .thumbnailImageDir(saveImageRequest.getThumbnailDir())
        .createdAt(DateUtils.now())
        .isPublic(saveImageRequest.getIsPublic())
        .productLevel(ProductLevel.valueOf(saveImageRequest.getProductLevel()))
        .latitude(saveImageRequest.getLatitude())
        .longitude(saveImageRequest.getLongitude())
        .build();
  }
}
