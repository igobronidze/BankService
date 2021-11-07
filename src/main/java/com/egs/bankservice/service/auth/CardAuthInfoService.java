package com.egs.bankservice.service.auth;

import com.egs.bankservice.entity.card.CardEntity;

public interface CardAuthInfoService {

    CardEntity increaseFailedAttempts(CardEntity card);
}
