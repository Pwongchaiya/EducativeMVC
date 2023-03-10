package com.example.springmicro.reactive.repository;

import com.example.springmicro.reactive.entity.User;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    Mono<User> findByName(String name);
}