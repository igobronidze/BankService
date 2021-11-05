package com.egs.bankservice.service.auth;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final CardRepository cardRepository;

    @Autowired
    public CardAuthServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public CardAuthResponse cardAuth(CardAuthRequest cardAuthRequest) {
        Optional<CardEntity> optionalCardEntity = cardRepository.getCardEntityByCardNumber(cardAuthRequest.getCardNumber());
        if (!optionalCardEntity.isPresent()) {
            throw new BankException("Can't find card By number: " + cardAuthRequest.getCardNumber());
        }
        CardEntity card = optionalCardEntity.get();
        boolean correctPassword;
        if (card.getAuthMethod() == CardAuthMethod.PIN) {
            correctPassword = PasswordHashUtils.validatePBKDF2HashPassword(card.getPin(), cardAuthRequest.getCardNumber());
        } else {
            correctPassword = PasswordHashUtils.validatePBKDF2HashPassword(card.getFingerprint(), cardAuthRequest.getCardNumber());
        }

        card.getAuthInfo().setLastAuthAttemptDate(LocalDate.now());
        if (!correctPassword) {
            int failedAttempts = card.getAuthInfo().getFailedAttempts();
            if (failedAttempts < 3) {
                card.getAuthInfo().setFailedAttempts(failedAttempts + 1);
            }
            if (failedAttempts == 2) {
                card.getAuthInfo().setBlocked(true);
            }
            throw new BankException("Password is incorrect");
        }

        if (card.getAuthInfo().isBlocked()) {
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
