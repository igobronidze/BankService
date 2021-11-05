package com.egs.bankservice.entity.card;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.egs.bankservice.entity.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "card")
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_number", length = 30, unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CardAuthMethod authMethod;

    @Column(length = 10)
    private String pin;

    @Column(length = 20)
    private String fingerprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    private long amount;

    @Embedded
    private CardAuthInfo authInfo;
}
