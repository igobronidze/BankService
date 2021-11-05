package com.egs.bankservice.controller.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private long id;

    private String firstName;

    private String lastName;

    private String personalId;

    private String email;
}
