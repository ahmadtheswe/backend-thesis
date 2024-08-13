package com.ahmadthesis.payment.usecase.preorder;

import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.controller.ChargeConverter;
import com.ahmadthesis.payment.controller.PersistPreOrder;
import com.ahmadthesis.payment.controller.PreOrderConverter;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.PreOrderTransactionDTO;
import com.ahmadthesis.payment.usecase.CopernicusPriceRetriever;
import com.ahmadthesis.payment.usecase.MidtransPersister;
import com.ahmadthesis.payment.usecase.MidtransRetriever;
import com.ahmadthesis.payment.usecase.PreOrderPersister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreOrderUseCaseApi implements PersistPreOrder {

  private final MidtransPersister midtransPersister;
  private final MidtransRetriever midtransRetriever;
  private final PreOrderPersister preOrderPersister;
  private final CopernicusPriceRetriever copernicusPriceRetriever;

  @Override
  public Mono<ChargeDTO> saveCharge(PreOrderTransactionDTO preOrderTransactionDTO, String userId) {
    return copernicusPriceRetriever.getCopernicusPriceById(preOrderTransactionDTO.getProbeType())
        .flatMap(copernicusPrice -> {
          return Mono.fromSupplier(() -> midtransPersister.preOrderCharge(
                  PreOrderConverter.toPreOrder(preOrderTransactionDTO), copernicusPrice.getPrice()))
              .flatMap(charge -> {
                final PreOrder payment = PreOrder.builder()
                    .id(charge.getOrderId())
                    .userId(userId)
                    .userEmail(preOrderTransactionDTO.getEmail())
                    .price(charge.getPrice())
                    .isPaid(false)
                    .redirectUrl(charge.getRedirectUrl())
                    .midtransToken(charge.getMidtransToken())
                    .build();

                return preOrderPersister.savePreOrder(payment, true).thenReturn(charge);
              });
        })
        .map(ChargeConverter::toDTO);
  }
}
