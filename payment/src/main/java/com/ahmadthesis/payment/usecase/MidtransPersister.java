package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Charge;
import com.ahmadthesis.payment.business.Payment;
import java.util.Map;

public interface MidtransPersister {
  Charge charge(Payment payment);
}
