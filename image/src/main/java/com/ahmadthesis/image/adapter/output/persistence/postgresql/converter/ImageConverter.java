package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImagePostgre;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
  public Image convertAdapterToDomain(ImagePostgre postgre) {
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

  public ImagePostgre convertDomainToAdapter(Image domain) {
    ImagePostgre postgre = new ImagePostgre();
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
