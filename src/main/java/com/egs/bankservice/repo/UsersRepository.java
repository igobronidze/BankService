package com.egs.bankservice.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.egs.bankservice.entity.user.UserEntity;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    boolean existsByPersonalId(String personalId);

    Optional<UserEntity> getUserEntityByPersonalId(String personalId);
}
