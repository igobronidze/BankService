package com.egs.bankservice.service.users;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egs.bankservice.controller.model.user.UserAddRequest;
import com.egs.bankservice.controller.model.user.UserResponse;
import com.egs.bankservice.entity.user.UserEntity;
import com.egs.bankservice.exception.BankException;
import com.egs.bankservice.repo.UsersRepository;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public long addUser(UserAddRequest userAddRequest) {
        if (usersRepository.existsByPersonalId(userAddRequest.getPersonalId())) {
            logger.warn(String.format("User with personalId %s already exists", userAddRequest.getPersonalId()));
            throw new BankException(String.format("User with personalId %s already exists", userAddRequest.getPersonalId()));
        }
        UserEntity user = usersRepository.save(getUserEntity(userAddRequest));
        return user.getId();
    }

    @Override
    @Transactional
    public UserResponse getUserById(long id) {
        Optional<UserEntity> optionalUserEntity = usersRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return getUserResponse(optionalUserEntity.get());
        } else {
            logger.warn("Can't find user By id: " + id);
            throw new BankException("Can't find user By id: " + id);
        }
    }

    @Override
    public UserResponse getUserByPersonalId(String personalId) {
        Optional<UserEntity> optionalUserEntity = usersRepository.getUserEntityByPersonalId(personalId);
        if (optionalUserEntity.isPresent()) {
            return getUserResponse(optionalUserEntity.get());
        } else {
            logger.warn("Can't find user By personalId: " + personalId);
            throw new BankException("Can't find user By personalId: " + personalId);
        }
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        if (!usersRepository.existsById(id)) {
            logger.warn("There is no user with id: " + id);
            throw new BankException("There is no user with id: " + id);
        }
        usersRepository.deleteById(id);
    }

    private UserResponse getUserResponse(UserEntity user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPersonalId(user.getPersonalId());
        return userResponse;
    }

    private UserEntity getUserEntity(UserAddRequest userAddRequest) {
        UserEntity user = new UserEntity();
        user.setFirstName(userAddRequest.getFirstName());
        user.setLastName(userAddRequest.getLastName());
        user.setPersonalId(userAddRequest.getPersonalId());
        return user;
    }
}
