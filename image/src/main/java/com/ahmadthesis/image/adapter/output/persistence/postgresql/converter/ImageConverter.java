package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.ProductLevel;

public final class ImageConverter {

  public static Image convertAdapterToDomain(ImageEntity postgre) {
    return Image.builder()
        .id(postgre.getId())
        .uploaderId(postgre.getUploaderId())
        .title(postgre.getTitle())
        .originalImageDir(postgre.getOriginalImageDir())
        .thumbnailImageDir(postgre.getThumbnailImageDir())
        .createdAt(postgre.getCreatedAt())
        .latestAccess(postgre.getLatestAccess())
        .mediaType(postgre.getMediaType())
        .filename(postgre.getFilename())
        .isPublic(postgre.getIsPublic())
        .productLevel(ProductLevel.valueOf(postgre.getProductLevel()))
        .latitude(postgre.getLatitude())
        .longitude(postgre.getLongitude())
        .build();
  }

  public static ImageEntity convertDomainToAdapter(Image domain) {
    return ImageEntity.builder()
        .id(domain.getId())
        .uploaderId(domain.getUploaderId())
        .title(domain.getTitle())
        .originalImageDir(domain.getOriginalImageDir())
        .thumbnailImageDir(domain.getThumbnailImageDir())
        .createdAt(domain.getCreatedAt())
        .latestAccess(domain.getLatestAccess())
        .mediaType(domain.getMediaType())
        .filename(domain.getFilename())
        .isPublic(domain.getIsPublic())
        .productLevel(domain.getProductLevel().name())
        .latitude(domain.getLatitude())
        .longitude(domain.getLongitude())
        .build();
  }
}
