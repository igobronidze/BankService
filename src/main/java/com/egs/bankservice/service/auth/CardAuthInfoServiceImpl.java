package com.egs.bankservice.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.repo.CardRepository;

@Service
public class CardAuthInfoServiceImpl implements CardAuthInfoService {

    private final CardRepository cardRepository;

    @Autowired
    public CardAuthInfoServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseFailedAttempts(CardEntity card) {
        int failedAttempts = card.getAuthInfo().getFailedAttempts();
        if (failedAttempts < 3) {
            card.getAuthInfo().setFailedAttempts(failedAttempts + 1);
        }
        if (failedAttempts == 2) {
            card.getAuthInfo().setBlocked(true);
        }
        cardRepository.save(card);
    }
}
