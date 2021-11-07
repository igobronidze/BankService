package com.egs.bankservice.service.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.egs.bankservice.controller.model.user.UserAddRequest;
import com.egs.bankservice.entity.user.UserEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.UsersRepository;

@SpringBootTest(classes = UsersServiceImpl.class)
public class UsersServiceTest {

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private UsersServiceImpl usersService;

    @Test
    public void testAddUserException() {
        String personalId = "1234567";
        Mockito.when(usersRepository.existsByPersonalId(personalId)).thenReturn(true);

        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setPersonalId(personalId);
        Assertions.assertThrows(BankException.class, () -> usersService.addUser(userAddRequest));
    }

    @Test
    public void testAddUserSuccessful() {
        long userId = 1;
        UserEntity user = new UserEntity();
        user.setId(userId);

        String personalId = "1234567";
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setPersonalId(personalId);

        Mockito.when(usersRepository.existsByPersonalId(personalId)).thenReturn(false);
        Mockito.when(usersRepository.save(Mockito.any())).thenReturn(user);

        Assertions.assertEquals(userId, usersService.addUser(userAddRequest));
    }
}
