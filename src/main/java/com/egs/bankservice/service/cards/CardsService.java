package com.egs.bankservice.service.cards;

import com.egs.bankservice.controller.model.card.AddCardRequest;
import com.egs.bankservice.controller.model.card.AddCardResponse;
import com.egs.bankservice.controller.model.card.CardResponse;
import com.egs.bankservice.controller.model.card.SetAuthMethodRequest;

public interface CardsService {

    AddCardResponse addCard(AddCardRequest addCardRequest);

    CardResponse getCardById(long id);

    CardResponse getCardByNumber(String number);

    void deleteCard(long id);

    void setAuthMethodByCardNumber(SetAuthMethodRequest setAuthMethodRequest);

    void unblockCard(String cardNumber);
}
