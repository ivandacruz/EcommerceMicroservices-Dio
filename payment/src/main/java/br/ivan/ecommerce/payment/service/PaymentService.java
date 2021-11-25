package br.ivan.ecommerce.payment.service;

import br.ivan.ecommerce.payment.entity.PaymentEntity;
import br.ivan.ecommerce.checkout.event.CheckoutCreatedEvent;

import java.util.Optional;

public interface PaymentService {
    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
