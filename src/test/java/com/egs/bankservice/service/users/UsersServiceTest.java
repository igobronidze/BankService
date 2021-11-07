package com.egs.bankservice.service.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.egs.bankservice.repo.UsersRepository;

@SpringBootTest(classes = UsersServiceImpl.class)
public class UsersServiceTest {

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private UsersServiceImpl usersService;

    @Test
    public void testAddUser() {

    }
}
