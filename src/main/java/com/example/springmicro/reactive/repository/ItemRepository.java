package com.example.springmicro.reactive.repository;

import com.example.springmicro.reactive.entity.Item;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

//write your code here
public interface ItemRepository extends ReactiveCrudRepository<Item, String>{
    
    Mono<Item> findByName(String name);

}