package com.egs.bankservice.service.cards;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.egs.bankservice.controller.model.card.CardResponse;
import com.egs.bankservice.entity.card.CardAuthMethod;
import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.entity.user.UserEntity;
import com.egs.bankservice.repo.CardRepository;
import com.egs.bankservice.repo.UsersRepository;

@SpringBootTest(classes = CardsServiceImpl.class)
public class CardsServiceTest {

    private static final long TEST_ID = 1L;
    private static final String TEST_CARD_NUMBER = "t12";
    private static final CardAuthMethod TEST_AUTH_METHOD = CardAuthMethod.PIN;
    private static final String TEST_PERSONAL_ID = "123";
    private static final long TEST_AMOUNT = 1000;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private CardsServiceImpl cardsService;

    @Test
    public void testGetCardById() {
        Optional<CardEntity> optionalCardEntity = Optional.of(getTestCardEntity());
        Mockito.when(cardRepository.getCardEntityByCardNumber(TEST_CARD_NUMBER)).thenReturn(optionalCardEntity);
        CardResponse expectedCardResponse = getTestCardResponse();
        CardResponse actualCardResponse = cardsService.getCardByNumber(TEST_CARD_NUMBER);
        assertCardResponse(expectedCardResponse, actualCardResponse);
    }

    private void assertCardResponse(CardResponse expected, CardResponse actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getCardNumber(), actual.getCardNumber());
        Assertions.assertEquals(expected.getAuthMethod(), actual.getAuthMethod());
        Assertions.assertEquals(expected.getUserPersonalId(), actual.getUserPersonalId());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
    }

    private CardResponse getTestCardResponse() {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(TEST_ID);
        cardResponse.setCardNumber(TEST_CARD_NUMBER);
        cardResponse.setAuthMethod(TEST_AUTH_METHOD.name());
        cardResponse.setUserPersonalId(TEST_PERSONAL_ID);
        cardResponse.setAmount(TEST_AMOUNT);
        return cardResponse;
    }

    private CardEntity getTestCardEntity() {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(TEST_ID);
        cardEntity.setCardNumber(TEST_CARD_NUMBER);
        cardEntity.setAuthMethod(TEST_AUTH_METHOD);
        UserEntity user = new UserEntity();
        user.setPersonalId(TEST_PERSONAL_ID);
        cardEntity.setUser(user);
        cardEntity.setAmount(TEST_AMOUNT);
        return cardEntity;
    }
}
