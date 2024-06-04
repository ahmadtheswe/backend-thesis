package com.ahmadthesis.payment.provider.image;

import reactor.core.publisher.Mono;

public interface ImageWebClient {
  Mono<Boolean> callPreorderImageCallBack(PreOrderCallBackRequest preOrderCallBackRequest);
}
