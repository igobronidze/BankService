package com.egs.bankservice.controller.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalRequest {

    private String cardNumber;

    private long amount;
}
