package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ImagePostgreAdapter implements ImageDatabase {
  private final R2DBCImageRepository repository;
  private final ImageConverter converter;

  public ImagePostgreAdapter(R2DBCImageRepository repository, ImageConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  @Override
  public Mono<Image> save(Image image) {
    return this.repository.save(converter.convertDomainToAdapter(image))
            .map(converter::convertAdapterToDomain);
  }

  @Override
  public Mono<Image> getImageById(String id) {
    return this.repository.findById(id)
            .map(converter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getImages(Integer size, Integer page, String sortBy) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAll()
            .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
            .take(pageRequest.getPageSize())
            .map(converter::convertAdapterToDomain);
  }

  @Override
  public Mono<Long> getTotalImagesCount() {
    return repository.count();
  }
}
