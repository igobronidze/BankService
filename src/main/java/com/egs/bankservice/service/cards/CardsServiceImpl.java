package com.egs.bankservice.service.cards;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egs.bankservice.controller.model.card.AddCardRequest;
import com.egs.bankservice.controller.model.card.AddCardResponse;
import com.egs.bankservice.controller.model.card.CardResponse;
import com.egs.bankservice.controller.model.card.SetAuthMethodRequest;
import com.egs.bankservice.entity.card.CardAuthMethod;
import com.egs.bankservice.entity.card.CardEntity;
import com.egs.bankservice.entity.user.UserEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.CardRepository;
import com.egs.bankservice.repo.UsersRepository;
import com.egs.bankservice.util.PasswordHashUtils;
import com.egs.bankservice.util.RandomGenerator;

@Service
@Transactional(readOnly = true)
public class CardsServiceImpl implements CardsService {

    private final Logger logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Value("${card.number.length}")
    private int cardNumberLength;

    private final CardRepository cardRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public CardsServiceImpl(CardRepository cardRepository, UsersRepository usersRepository) {
        this.cardRepository = cardRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public AddCardResponse addCard(AddCardRequest addCardRequest) {
        Optional<UserEntity> optionalUserEntity = usersRepository.getUserEntityByPersonalId(addCardRequest.getUserPersonalId());
        if (!optionalUserEntity.isPresent()) {
            logger.warn(String.format("User whit personalId %s doesn't exists", addCardRequest.getUserPersonalId()));
            throw new BankException(String.format("User whit personalId %s doesn't exists", addCardRequest.getUserPersonalId()));
        }

        CardEntity cardEntity = new CardEntity();
        cardEntity.setAuthMethod(getCardAuthMethod(addCardRequest.getAuthMethod()));
        cardEntity.setFingerprint(PasswordHashUtils.getMD5PasswordHash(addCardRequest.getFingerprint()));
        cardEntity.setUser(optionalUserEntity.get());
        String pin = RandomGenerator.getRandomPin();
        cardEntity.setPin(PasswordHashUtils.getMD5PasswordHash(pin));
        cardEntity.setCardNumber(RandomGenerator.getRandomAlphanumericString(cardNumberLength));

        cardRepository.save(cardEntity);

        AddCardResponse response = new AddCardResponse();
        response.setId(cardEntity.getId());
        response.setCardNumber(cardEntity.getCardNumber());
        response.setPin(pin);
        return response;
    }

    @Override
    public CardResponse getCardById(long id) {
        Optional<CardEntity> optionalCardEntity = cardRepository.findById(id);
        if (optionalCardEntity.isPresent()) {
            return getCardResponse(optionalCardEntity.get());
        } else {
            logger.warn("Can't find card By id: " + id);
            throw new BankException("Can't find card By id: " + id);
        }
    }

    @Override
    public CardResponse getCardByNumber(String number) {
        Optional<CardEntity> optionalCardEntity = cardRepository.getCardEntityByCardNumber(number);
        if (optionalCardEntity.isPresent()) {
            return getCardResponse(optionalCardEntity.get());
        } else {
            logger.warn("Can't find card By number: " + number);
            throw new BankException("Can't find card By number: " + number);
        }
    }

    @Override
    @Transactional
    public void deleteCard(long id) {
        if (!cardRepository.existsById(id)) {
            logger.warn("There is no card with id: " + id);
            throw new BankException("There is no card with id: " + id);
        }
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void setAuthMethodByCardNumber(SetAuthMethodRequest setAuthMethodRequest) {
        Optional<CardEntity> optionalCardEntity = cardRepository.getCardEntityByCardNumber(setAuthMethodRequest.getCardNumber());
        if (!optionalCardEntity.isPresent()) {
            logger.warn("Can't find card By number: " + setAuthMethodRequest.getCardNumber());
            throw new BankException("Can't find card By number: " + setAuthMethodRequest.getCardNumber());
        }
        CardAuthMethod cardAuthMethod = getCardAuthMethod(setAuthMethodRequest.getAuthMethod());
        optionalCardEntity.get().setAuthMethod(cardAuthMethod);
    }

    private CardResponse getCardResponse(CardEntity cardEntity) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(cardEntity.getId());
        cardResponse.setCardNumber(cardEntity.getCardNumber());
        cardResponse.setAuthMethod(cardEntity.getAuthMethod().name());
        cardResponse.setUserPersonalId(cardEntity.getUser().getPersonalId());
        cardResponse.setAmount(cardEntity.getAmount());
        return cardResponse;
    }

    private CardAuthMethod getCardAuthMethod(String authMethod) {
        CardAuthMethod cardAuthMethod;
        try {
            cardAuthMethod = CardAuthMethod.valueOf(authMethod);
        } catch (IllegalArgumentException ex) {
            logger.warn("Not supported card auth method: " + authMethod);
            throw new BankException("Not supported card auth method: " + authMethod);
        }
        return cardAuthMethod;
    }
}
