package com.inomind.modelo.springmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.inomind.modelo.springmongo.domain.User;

@Transactional
public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String username);
}
