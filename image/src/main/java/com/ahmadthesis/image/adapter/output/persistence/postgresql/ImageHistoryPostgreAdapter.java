package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageHistoryConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageHistoryRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.domain.entity.image.Image;
import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ImageHistoryPostgreAdapter implements ImageHistoryDatabase {
    private final R2DBCImageHistoryRepository repository;
    private final ImageHistoryConverter converter;
    public ImageHistoryPostgreAdapter(R2DBCImageHistoryRepository repository, ImageHistoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }


    @Override
    public Mono<ImageHistory> save(ImageHistory imageHistory) {
        return repository.save(converter.convertDomainToAdapter(imageHistory))
                .map(converter::convertAdapterToDomain);
    }

    @Override
    public Flux<ImageHistory> getHistoryByImageId(String imageId) {
        return repository.getImageHistoryPostgreByImageId(imageId)
                .map(converter::convertAdapterToDomain);
    }
}
