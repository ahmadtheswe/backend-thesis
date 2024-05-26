package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.controller.dto.PreOrderTransactionDTO;

public class PreOrderConverter {
  public static PreOrder toPreOrder(PreOrderTransactionDTO preOrderTransactionDTO) {
    return PreOrder.builder()
        .userEmail(preOrderTransactionDTO.getEmail())
        .imageSize(preOrderTransactionDTO.getImageSize())
        .build();
  }
}
