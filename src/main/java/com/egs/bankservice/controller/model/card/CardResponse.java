package com.egs.bankservice.controller.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {

    private long id;

    private String cardNumber;

    private String authMethod;

    private String userPersonalId;

    private long amount;
}
