package com.ahmadthesis.payment.provider.preorder;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PreOrderRepository extends R2dbcRepository<PreOrderEntity, String> {

}
