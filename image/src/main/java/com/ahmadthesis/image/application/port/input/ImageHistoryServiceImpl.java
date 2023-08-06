package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.application.usecase.ImageHistoryService;
import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ImageHistoryServiceImpl implements ImageHistoryService {
    private final ImageHistoryDatabase database;
    public ImageHistoryServiceImpl(ImageHistoryDatabase database) {
        this.database = database;
    }

    @Override
    public Mono<ImageHistory> save(ImageHistory imageHistory) {
        return database.save(imageHistory);
    }

    @Override
    public Flux<ImageHistory> getHistoryByImageId(String imageId) {
        return database.getHistoryByImageId(imageId);
    }
}
