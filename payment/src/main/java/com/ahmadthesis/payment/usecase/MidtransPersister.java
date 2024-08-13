package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Charge;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.business.PreOrderCharge;
import java.math.BigInteger;

public interface MidtransPersister {
  Charge paymentCharge(Payment payment);
  PreOrderCharge preOrderCharge(PreOrder preOrder, BigInteger copernicusPrice);
}
