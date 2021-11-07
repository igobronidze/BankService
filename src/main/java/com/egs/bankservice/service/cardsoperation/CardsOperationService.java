package com.egs.bankservice.service.cardsoperation;

public interface CardsOperationService {

    long checkBalance(String cardNumber);

    void deposit(String cardNumber, long amount);

    void withdrawal(String cardNumber, long amount);
}
