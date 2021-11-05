package com.egs.bankservice.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.egs.bankservice.entity.card.CardEntity;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, Long> {

    Optional<CardEntity> getCardEntityByCardNumber(String cardNumber);
}
