package com.example.springmicro.reactive.repository;

import com.example.springmicro.reactive.entity.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

}
