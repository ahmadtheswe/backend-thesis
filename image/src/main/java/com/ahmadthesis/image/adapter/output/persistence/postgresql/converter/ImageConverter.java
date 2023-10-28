package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import com.ahmadthesis.image.domain.image.Image;

public final class ImageConverter {
  public static Image convertAdapterToDomain(ImageEntity postgre) {
    Image image = new Image();
    image.setId(postgre.getId());
    image.setUploaderId(postgre.getUploaderId());
    image.setTitle(postgre.getTitle());
    image.setOriginalImageDir(postgre.getOriginalImageDir());
    image.setCreatedAt(postgre.getCreatedAt());
    image.setLatestAccess(postgre.getLatestAccess());
    image.setMediaType(postgre.getMediaType());
    image.setFilename(postgre.getFilename());
    image.setIsPublic(postgre.getIsPublic());

    return image;
  }

  public static ImageEntity convertDomainToAdapter(Image domain) {
    ImageEntity postgre = new ImageEntity();
    postgre.setId(domain.getId());
    postgre.setUploaderId(domain.getUploaderId());
    postgre.setTitle(domain.getTitle());
    postgre.setOriginalImageDir(domain.getOriginalImageDir());
    postgre.setCreatedAt(domain.getCreatedAt());
    postgre.setLatestAccess(domain.getLatestAccess());
    postgre.setMediaType(domain.getMediaType());
    postgre.setFilename(domain.getFilename());
    postgre.setIsPublic(domain.getIsPublic());

    return postgre;
  }
}
