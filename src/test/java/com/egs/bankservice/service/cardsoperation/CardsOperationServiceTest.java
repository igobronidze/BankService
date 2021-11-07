package com.egs.bankservice.service.cardsoperation;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.CardRepository;

@SpringBootTest(classes = CardsOperationServiceImpl.class)
public class CardsOperationServiceTest {

    @MockBean
    private CardRepository cardRepository;

    @Autowired
    private CardsOperationServiceImpl cardsOperationService;

    @Test
    public void testWithdrawalException() {
        String cardNumber = "123";
        CardEntity card = new CardEntity();
        card.setAmount(100);
        Optional<CardEntity> optionalCardEntity = Optional.of(card);
        Mockito.when(cardRepository.getCardEntityByCardNumber(cardNumber)).thenReturn(optionalCardEntity);

        Assertions.assertThrows(BankException.class, () -> cardsOperationService.withdrawal(cardNumber, 200));
    }
}
