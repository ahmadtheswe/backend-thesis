package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Payment;
import java.util.Map;

public interface MidtransPersister {
  Map<String, String> charge(Payment payment);
}
