package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.application.usecase.ImageHistoryService;
import com.ahmadthesis.image.domain.image.ImageHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageHistoryServiceImpl implements ImageHistoryService {
  private final ImageHistoryDatabase database;

  @Override
  public Mono<ImageHistory> save(ImageHistory imageHistory) {
    return database.save(imageHistory);
  }

  @Override
  public Flux<ImageHistory> getHistoryByImageId(String imageId) {
    return database.getHistoryByImageId(imageId);
  }
}
