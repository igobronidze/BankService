package com.egs.bankservice.controller.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetAuthMethodRequest {

    private String cardNumber;

    private String authMethod;
}
