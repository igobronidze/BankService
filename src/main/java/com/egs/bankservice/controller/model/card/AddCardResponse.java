package com.egs.bankservice.controller.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCardResponse {

    private String cardNumber;

    private String pin;
}
