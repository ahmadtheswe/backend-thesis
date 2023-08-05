package com.ahmadthesis.image.adapter.output.persistence.postgresql.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
@Table("public.\"image_history\"")
public class ImageHistoryPostgre implements Persistable<String> {
    @Id
    private String id;
    @Column("image_id")
    private String imageId;
    private String accessorId;
    private String activity;
    private Long createdAt;
    @Transient
    private ImagePostgre imagePostgre;

    @Override
    public boolean isNew() {
        return true;
    }
}
