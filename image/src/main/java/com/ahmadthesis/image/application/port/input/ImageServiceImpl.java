package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageDatabase database;
    public ImageServiceImpl(ImageDatabase database) {
        this.database = database;
    }

    @Override
    public Mono<Image> save(Image image) {
        return database.save(image);
    }

    @Override
    public Mono<Image> getImageById(String id) {
        return database.getImageById(id);
    }

    @Override
    public Flux<Image> getImagesPagination(Integer size, Integer page, String sortBy) {
        return database.getImages(size, page, sortBy);
    }

    @Override
    public Mono<Long> getImagesCount() {
        return database.getTotalImagesCount();
    }
}
