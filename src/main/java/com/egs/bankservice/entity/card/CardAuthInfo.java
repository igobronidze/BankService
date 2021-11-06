package com.egs.bankservice.entity.card;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CardAuthInfo {

    @Column(nullable = false)
    private boolean blocked;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts;
}
