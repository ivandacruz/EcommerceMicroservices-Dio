package br.ivan.ecommerce.checkout.service;

import br.ivan.ecommerce.checkout.entity.CheckoutEntity;
import br.ivan.ecommerce.checkout.enums.Status;
import br.ivan.ecommerce.checkout.repository.CheckoutRepository;
import br.ivan.ecommerce.checkout.resource.checkout.CheckoutRequest;
import br.ivan.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.ivan.ecommerce.checkout.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {

        log.info("M=create, checkoutRequest={}", checkoutRequest);
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .status(Status.CREATED)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
//                .shipping(ShippingEntity.builder()
//                        .address(checkoutRequest.getAddress())
//                        .complement(checkoutRequest.getComplement())
//                        .country(checkoutRequest.getCountry())
//                        .state(checkoutRequest.getState())
//                        .cep(checkoutRequest.getCep())
//                        .build())
                .build();
//        checkoutEntity.setItems(checkoutRequest.getProducts()
//                .stream()
//                .map(product -> CheckoutItemEntity.builder()
//                        .checkout(checkoutEntity)
//                        .product(product)
//                        .build())
//                .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}

