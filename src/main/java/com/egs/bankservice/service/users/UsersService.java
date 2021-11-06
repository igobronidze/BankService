package com.egs.bankservice.service.users;

import com.egs.bankservice.controller.model.user.UserAddRequest;
import com.egs.bankservice.controller.model.user.UserResponse;

public interface UsersService {

    long addUser(UserAddRequest userAddRequest);

    UserResponse getUserById(long id);

    UserResponse getUserByPersonalId(String personalId);

    void deleteUser(long id);
}
