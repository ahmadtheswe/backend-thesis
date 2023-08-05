package com.ahmadthesis.image.domain.entity.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Data
public class Image {
    private String id;
    private String uploaderId;
    private String title;
    private String format;
    private String filename;
    private String originalImageDir;
    private String mediaType;
    private Long createdAt;
    private Long latestAccess;
    private Boolean isPublic;

}
