package br.ivan.ecommerce.payment.listener;

import br.ivan.ecommerce.payment.entity.PaymentEntity;
import br.ivan.ecommerce.payment.service.PaymentService;
import br.ivan.ecommerce.payment.streaming.CheckoutProcessor;
import br.ivan.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.ivan.ecommerce.payment.event.PaymentCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {

        /* Vai processar o pagamento Gateway
        * e salvar os dados de pagamentos no
        * bd e enviara o pagamento  processado. */
        log.info("checkoutCreatedEvent={}", checkoutCreatedEvent);
        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
