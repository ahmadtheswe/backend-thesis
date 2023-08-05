package com.ahmadthesis.image.adapter.output.persistence.postgresql.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
@Table("public.\"image\"")
public class ImagePostgre implements Persistable<String> {
    @Id
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

    @Override
    public boolean isNew() {
        return true;
    }
}
