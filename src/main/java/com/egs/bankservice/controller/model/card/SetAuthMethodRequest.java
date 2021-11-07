package com.egs.bankservice.controller.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetAuthMethodRequest {

    @JsonProperty(required = true)
    private String cardNumber;

    @JsonProperty(required = true)
    private String authMethod;
}
