package com.egs.bankservice.service.auth;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egs.bankservice.controller.model.auth.CardAuthRequest;
import com.egs.bankservice.controller.model.auth.CardAuthResponse;
import com.egs.bankservice.entity.card.CardAuthMethod;
import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.CardRepository;
import com.egs.bankservice.util.PasswordHashUtils;

@Service
@Transactional(readOnly = true)
public class CardAuthServiceImpl implements CardAuthService {

    private final Logger logger = LoggerFactory.getLogger(CardAuthServiceImpl.class);

    private final CardRepository cardRepository;

    private final CardAuthInfoService cardAuthInfoService;

    @Autowired
    public CardAuthServiceImpl(CardRepository cardRepository, CardAuthInfoServiceImpl cardAuthInfoService) {
        this.cardRepository = cardRepository;
        this.cardAuthInfoService = cardAuthInfoService;
    }

    @Override
    @Transactional
    public CardAuthResponse cardAuth(CardAuthRequest cardAuthRequest) {
        Optional<CardEntity> optionalCardEntity = cardRepository.getCardEntityByCardNumber(cardAuthRequest.getCardNumber());
        if (!optionalCardEntity.isPresent()) {
            logger.warn("Can't find card By number: " + cardAuthRequest.getCardNumber());
            throw new BankException("Can't find card By number: " + cardAuthRequest.getCardNumber());
        }
        CardEntity card = optionalCardEntity.get();
        boolean correctPassword;
        if (card.getAuthMethod() == CardAuthMethod.PIN) {
            correctPassword = PasswordHashUtils.validateMD5PasswordHash(card.getPin(), cardAuthRequest.getCode());
        } else {
            correctPassword = PasswordHashUtils.validateMD5PasswordHash(card.getFingerprint(), cardAuthRequest.getCode());
        }

        if (!correctPassword) {
            cardAuthInfoService.increaseFailedAttempts(card);
            logger.warn("Password is incorrect");
            throw new BankException("Password is incorrect");
        }

        if (card.getAuthInfo().isBlocked()) {
            logger.warn("Card is blocked");
            throw new BankException("Card is blocked");
        }

        card.getAuthInfo().setFailedAttempts(0);

        CardAuthResponse cardAuthResponse = new CardAuthResponse();
        cardAuthResponse.setCardNumber(card.getCardNumber());
        cardAuthResponse.setAmount(card.getAmount());
        cardAuthResponse.setAllowedActions(Stream.of(CardAllowedAction.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
        return cardAuthResponse;
    }

    private enum CardAllowedAction {
        DEPOSIT,
        WITHDRAWAL,
        CHECK_BALANCE
    }
}
