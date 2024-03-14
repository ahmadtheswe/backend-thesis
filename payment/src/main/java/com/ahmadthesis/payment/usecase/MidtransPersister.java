package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Charge;
import com.ahmadthesis.payment.business.Payment;

public interface MidtransPersister {
  Charge charge(Payment payment);
}
