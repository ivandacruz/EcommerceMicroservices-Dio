package br.ivan.ecommerce.checkout.config;

import br.ivan.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.ivan.ecommerce.checkout.streaming.PaymentPaidSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        CheckoutCreatedSource.class,
        PaymentPaidSink.class

})

public class StreamingConfig {
}
