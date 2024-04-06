package com.ahmadthesis.image.adapter.output.persistence.postgresql.image;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter.ImageConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.ImageEntity;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql.ImageRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.domain.image.Image;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImagePostgreAdapter implements ImageDatabase {

  private final ImageRepository repository;

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
  public Mono<Image> getPublicImageById(final String id) {
    return this.repository.findByIdAndIsPublicIsTrue(id)
        .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getImages(final Integer size, final Integer page, final String sortBy,
      final String title, final BigDecimal latitude, final BigDecimal longitude, Double radius) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    final Flux<ImageEntity> imageEntityFlux = title == null ?
        this.repository.findAll() : this.repository.findAllByTitleLikeIgnoreCase(title);
    return imageEntityFlux
        .filter(imageEntity -> {
          if (!Objects.equals(latitude, BigDecimal.ZERO) && !Objects.equals(longitude,
              BigDecimal.ZERO)) {
            return isWithinRadius(latitude.doubleValue(), longitude.doubleValue(),
                imageEntity.getLatitude().doubleValue(), imageEntity.getLongitude().doubleValue(),
                radius);
          } else {
            return true;
          }
        })
//        .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
//        .take(pageRequest.getPageSize())
        .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getPublicImages(final Integer size, final Integer page, final String sortBy,
      final BigDecimal latitude, final BigDecimal longitude, Double radius) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAllByIsPublicTrue()
        .filter(imageEntity -> {
          if (!Objects.equals(latitude, BigDecimal.ZERO) && !Objects.equals(longitude,
              BigDecimal.ZERO)) {
            return isWithinRadius(latitude.doubleValue(), longitude.doubleValue(),
                imageEntity.getLatitude().doubleValue(), imageEntity.getLongitude().doubleValue(),
                radius);
          } else {
            return true;
          }
        })
//        .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
//        .take(pageRequest.getPageSize())
        .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Flux<Image> getUserImagesCollection(
      final Integer size, final Integer page, final String sortBy, final String ownerId,
      final BigDecimal latitude, final BigDecimal longitude) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
    return this.repository.findAllByOwnerId(ownerId)
        .filter(imageEntity -> {
          if (!Objects.equals(latitude, BigDecimal.ZERO) && !Objects.equals(longitude,
              BigDecimal.ZERO)) {
            return isWithinRadius(latitude.doubleValue(), longitude.doubleValue(),
                imageEntity.getLatitude().doubleValue(), imageEntity.getLongitude().doubleValue(),
                5.0);
          } else {
            return true;
          }
        })
        .skip((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
        .take(pageRequest.getPageSize())
        .map(ImageConverter::convertAdapterToDomain);
  }

  @Override
  public Mono<Long> getTotalImagesCount() {
    return repository.count();
  }

  private boolean isWithinRadius(
      final double lat1,
      final double lon1,
      final double lat2,
      final double lon2,
      final double radius) {
    return haversine(lat1, lon1, lat2, lon2) <= radius;
  }

  // Haversine formula method
  private double haversine(
      final double lat1,
      final double lon1,
      final double lat2,
      final double lon2) {
    final int EARTH_RADIUS_KM = 6371;

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS_KM * c;
  }
}
