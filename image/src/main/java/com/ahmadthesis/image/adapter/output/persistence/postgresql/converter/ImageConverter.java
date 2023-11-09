package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import com.ahmadthesis.image.domain.image.Image;

public final class ImageConverter {

  public static Image convertAdapterToDomain(ImageEntity postgre) {
    return Image.builder()
        .id(postgre.getId())
        .uploaderId(postgre.getUploaderId())
        .title(postgre.getTitle())
        .originalImageDir(postgre.getOriginalImageDir())
        .createdAt(postgre.getCreatedAt())
        .latestAccess(postgre.getLatestAccess())
        .mediaType(postgre.getMediaType())
        .filename(postgre.getFilename())
        .isPublic(postgre.getIsPublic())
        .priceIDR(postgre.getPriceIDR())
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
        .createdAt(domain.getCreatedAt())
        .latestAccess(domain.getLatestAccess())
        .mediaType(domain.getMediaType())
        .filename(domain.getFilename())
        .isPublic(domain.getIsPublic())
        .priceIDR(domain.getPriceIDR())
        .latitude(domain.getLatitude())
        .longitude(domain.getLongitude())
        .build();
  }
}
