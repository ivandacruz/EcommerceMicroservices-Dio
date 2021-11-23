package br.ivan.ecommerce.checkout.listener;

import br.ivan.ecommerce.checkout.enums.Status;
import br.ivan.ecommerce.checkout.repository.CheckoutRepository;
import br.ivan.ecommerce.checkout.entity.CheckoutEntity;
import br.ivan.ecommerce.checkout.streaming.PaymentPaidSink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j

public class PaymentPaidListener {

    private final CheckoutRepository checkoutRepository;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){
        log.info("PaymentCreatedEvent={}", event);
        final CheckoutEntity chekoutEntity = checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(Status.APPROVED);
        checkoutRepository.save(checkoutEntity);
    }
}
