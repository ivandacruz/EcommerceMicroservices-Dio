package br.ivan.ecommerce.checkout.resource.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CheckoutRequest implements Serializable {

    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String complement;
    private String country;
    private String state;
    private String cep;
    private String saveAddress;
    private String saveInfo;
    private String paymentMethod;
    private String cardNone;
    private String cardNumber;
    private String cardDate;
    private String cardCvv;
    private List<String> products;
}
