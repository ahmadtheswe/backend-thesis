package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.domain.entity.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImagePostgreAdapter implements ImageDatabase {
  private final R2DBCImageRepository repository;

  @Override
  public Mono<Image> save(Image image) {
    return this.repository.save(ImageConverter.convertDomainToAdapter(image))
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Mono<Image> getImageById(String id) {
    return this.repository.findById(id)
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getImages(Integer size, Integer page, String sortBy) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAll()
            .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
            .take(pageRequest.getPageSize())
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Mono<Long> getTotalImagesCount() {
    return repository.count();
  }
}
