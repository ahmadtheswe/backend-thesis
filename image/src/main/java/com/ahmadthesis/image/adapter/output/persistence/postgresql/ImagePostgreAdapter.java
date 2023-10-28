package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImagePostgreAdapter implements ImageDatabase {
  private final R2DBCImageRepository repository;

  @Override
  public Mono<Image> save(final Image image) {
    return this.repository.save(ImageConverter.convertDomainToAdapter(image))
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Mono<Image> getImageById(final String id) {
    return this.repository.findById(id)
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getImages(final Integer size, final Integer page, final String sortBy) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAll()
            .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
            .take(pageRequest.getPageSize())
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getPublicImages(final Integer size, final Integer page, final String sortBy) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAllByIsPublicTrue()
            .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
            .take(pageRequest.getPageSize())
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getUserImagesCollection(
          final Integer size, final Integer page, final String sortBy, final String ownerId) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAllByOwnerId(ownerId)
            .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
            .take(pageRequest.getPageSize())
            .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Mono<Long> getTotalImagesCount() {
    return repository.count();
  }
}
