package com.egs.bankservice.controller.rest;

import javax.servlet.http.HttpSession;

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

    public static final String HTTP_SESSION_CARD_NUMBER_KEY = "cardNumber";

    private final CardAuthService cardAuthService;

    @Autowired
    public CardAuthorizationController(CardAuthServiceImpl cardAuthService) {
        this.cardAuthService = cardAuthService;
    }

    @PostMapping("auth")
    public CardAuthResponse cardAuth(HttpSession httpSession, @RequestBody CardAuthRequest cardAuthRequest) {
        CardAuthResponse cardAuthResponse = cardAuthService.cardAuth(cardAuthRequest);
        httpSession.setAttribute(HTTP_SESSION_CARD_NUMBER_KEY, cardAuthResponse.getCardNumber());
        return cardAuthResponse;
    }

    @PostMapping("closeSession")
    public void closeSession(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
