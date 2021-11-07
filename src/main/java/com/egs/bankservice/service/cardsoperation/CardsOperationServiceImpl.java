package com.egs.bankservice.service.cardsoperation;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.CardRepository;

@Service
@Transactional(readOnly = true)
public class CardsOperationServiceImpl implements CardsOperationService {

    private final Logger logger = LoggerFactory.getLogger(CardsOperationServiceImpl.class);

    private final CardRepository cardRepository;

    @Autowired
    public CardsOperationServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public long checkBalance(String cardNumber) {
        return getCardByNumberIfCanFound(cardNumber).getAmount();
    }

    @Override
    @Transactional
    public void deposit(String cardNumber, long amount) {
        CardEntity card = getCardByNumberIfCanFound(cardNumber);
        card.setAmount(card.getAmount() + amount);
    }

    @Override
    @Transactional
    public void withdrawal(String cardNumber, long amount) {
        CardEntity card = getCardByNumberIfCanFound(cardNumber);
        if (card.getAmount() < amount) {
            logger.warn("Card amount is less then withdrawal amount");
            throw new BankException("Card amount is less then withdrawal amount");
        }
        card.setAmount(card.getAmount() - amount);
    }

    private CardEntity getCardByNumberIfCanFound(String cardNumber) {
        Optional<CardEntity> optionalCardEntity = cardRepository.getCardEntityByCardNumber(cardNumber);
        if (!optionalCardEntity.isPresent()) {
            logger.warn("Can't find card By number: " + cardNumber);
            throw new BankException("Can't find card By number: " + cardNumber);
        }
        return optionalCardEntity.get();
    }
}
