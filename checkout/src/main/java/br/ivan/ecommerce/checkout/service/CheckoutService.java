package br.ivan.ecommerce.checkout.service;

import br.ivan.ecommerce.checkout.entity.CheckoutEntity;
import br.ivan.ecommerce.checkout.enums.Status;
import br.ivan.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
    Optional<CheckoutEntity> updateStatus(String checkoutCode, Status status);
}
