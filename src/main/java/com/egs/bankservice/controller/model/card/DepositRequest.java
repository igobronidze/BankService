package com.egs.bankservice.controller.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {

    private String cardNumber;

    private long amount;
}
