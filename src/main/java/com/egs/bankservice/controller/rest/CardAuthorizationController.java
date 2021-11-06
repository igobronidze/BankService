package com.egs.bankservice.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egs.bankservice.controller.model.auth.CardAuthRequest;
import com.egs.bankservice.controller.model.auth.CardAuthResponse;
import com.egs.bankservice.service.auth.CardAuthService;
import com.egs.bankservice.service.auth.CardAuthServiceImpl;

@RestController
@RequestMapping("api/cardAuth")
public class CardAuthorizationController {

    private final CardAuthService cardAuthService;

    @Autowired
    public CardAuthorizationController(CardAuthServiceImpl cardAuthService) {
        this.cardAuthService = cardAuthService;
    }

    @PostMapping
    public CardAuthResponse cardAuth(@RequestBody CardAuthRequest cardAuthRequest) {
        return cardAuthService.cardAuth(cardAuthRequest);
    }
}
