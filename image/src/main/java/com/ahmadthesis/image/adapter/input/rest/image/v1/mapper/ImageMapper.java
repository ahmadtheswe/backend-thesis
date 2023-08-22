package com.ahmadthesis.image.adapter.input.rest.image.v1.mapper;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.entity.image.Image;
import com.ahmadthesis.image.global.utils.dates.DateUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImageMapper {
    private final DateUtils dateUtils;

    public ImageMapper(
            DateUtils dateUtils
    ) {
        this.dateUtils = dateUtils;
    }

    public Image mapRequestToImage(SaveImageRequest saveImageRequest) {
        return Image.builder()
                .id(UUID.randomUUID().toString())
                .uploaderId(UUID.randomUUID().toString())
                .title(saveImageRequest.getTitle())
                .mediaType(saveImageRequest.getMediaType())
                .filename(saveImageRequest.getFilename())
//                                .originalImageDir()
                .createdAt(dateUtils.now())
                .isPublic(saveImageRequest.getIsPublic())
                .build();
    }
}
