package com.egs.bankservice.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egs.bankservice.controller.model.card.DepositRequest;
import com.egs.bankservice.controller.model.card.WithdrawalRequest;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.service.cardsoperation.CardsOperationService;
import com.egs.bankservice.service.cardsoperation.CardsOperationServiceImpl;

@RestController
@RequestMapping("api/cards/operation")
public class CardsOperationController {

    private final CardsOperationService cardsOperationService;

    @Autowired
    public CardsOperationController(CardsOperationServiceImpl cardsOperationService) {
        this.cardsOperationService = cardsOperationService;
    }

    @GetMapping("checkBalance")
    public long checkBalance(HttpSession httpSession, @RequestParam(value = "cardNumber") String cardNumber) {
        checkSession(httpSession, cardNumber);
        return cardsOperationService.checkBalance(cardNumber);
    }

    @PostMapping("deposit")
    public void deposit(HttpSession httpSession, @RequestBody DepositRequest depositRequest) {
        checkSession(httpSession, depositRequest.getCardNumber());
        cardsOperationService.deposit(depositRequest.getCardNumber(), depositRequest.getAmount());
    }

    @PostMapping("withdrawal")
    public void withdrawal(HttpSession httpSession, @RequestBody WithdrawalRequest withdrawalRequest) {
        checkSession(httpSession, withdrawalRequest.getCardNumber());
        cardsOperationService.withdrawal(withdrawalRequest.getCardNumber(), withdrawalRequest.getAmount());
    }

    private void checkSession(HttpSession httpSession, String cardNumber) {
        if (!cardNumber.equals(httpSession.getAttribute(CardAuthorizationController.HTTP_SESSION_CARD_NUMBER_KEY))) {
            throw new BankException("Session is not initialized");
        }
    }
}
