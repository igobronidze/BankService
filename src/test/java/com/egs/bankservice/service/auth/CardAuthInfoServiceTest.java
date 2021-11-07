package com.egs.bankservice.service.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.egs.bankservice.entity.card.CardAuthInfo;
import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.repo.CardRepository;

@SpringBootTest(classes = CardAuthInfoServiceImpl.class)
public class CardAuthInfoServiceTest {

    @MockBean
    private CardRepository cardRepository;

    @Autowired
    private CardAuthInfoServiceImpl cardAuthInfoService;

    @Test
    public void testIncreaseFailedAttempts() {
        CardAuthInfo cardAuthInfo = new CardAuthInfo();
        cardAuthInfo.setFailedAttempts(2);
        CardEntity card = new CardEntity();
        card.setAuthInfo(cardAuthInfo);

        Mockito.when(cardRepository.save(Mockito.any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);

        CardEntity actualCard = cardAuthInfoService.increaseFailedAttempts(card);
        Assertions.assertEquals(3, actualCard.getAuthInfo().getFailedAttempts());
        Assertions.assertTrue(actualCard.getAuthInfo().isBlocked());
    }
}
