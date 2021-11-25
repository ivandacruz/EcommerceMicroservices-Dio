package br.ivan.ecommerce.payment.service;

import br.ivan.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.ivan.ecommerce.payment.entity.PaymentEntity;
import br.ivan.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Optional<PaymentEntity> created(CheckoutCreatedEvent checkoutCreatedEvent) {
        final PaymentEntity paymentEntity = PaymentEntity.builder()
                .checkout(checkoutCreatedEvent.getCheckoutCode())
                .code(UUID.randomUUID().toString())
                .builder();

        paymentRepository.save(paymentEntity);
        return Optional.of(paymentEntity);
    }
}
