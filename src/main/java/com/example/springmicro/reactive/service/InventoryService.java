package com.example.springmicro.reactive.service;

import java.util.stream.Collectors;

import com.example.springmicro.reactive.entity.Cart;
import com.example.springmicro.reactive.entity.CartItem;
import com.example.springmicro.reactive.entity.Item;
import com.example.springmicro.reactive.repository.CartRepository;
import com.example.springmicro.reactive.repository.ItemRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public
class InventoryService {

    private final ItemRepository itemRepo;
    private final CartRepository cartRepo;

 //write code for task 7,8,9 here
    public Mono<Cart> getCart(String cartId){
        return cartRepo.findById(cartId);
    }

    public Flux<Item> getInventory(){
        return itemRepo.findAll();
    }

    public Mono<Item> saveItem(Item newItem){
        return itemRepo.save(newItem);
    }

    public Mono<Void> deleteItem(String id){
        return itemRepo.deleteById(id);
    }

    public Mono<Cart> removeOnceFromCart(String cartId, String itemId){
        return cartRepo.findById(cartId)
            .defaultIfEmpty(new Cart(cartId))
            .flatMap(cart -> cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .map(cartItem -> {
                    cartItem.decrement();
                    return Mono.just(cart);
                })
                .orElse(Mono.empty()))
            .map(cart -> new Cart(cart.getId(), cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getQuantity() > 0)
                .collect(Collectors.toList())))
            .flatMap(cartRepo::save);
    }

    public Mono<Cart> addItemToCart(String cartId, String itemId){
        return cartRepo.findById(cartId)
        .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart -> cart.getCartItems().stream()
            .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
            .findAny()
            .map(cartItem -> {
                cartItem.increment();
                return Mono.just(cart);
            })
            .orElseGet(()-> itemRepo.findById(itemId)
            .map(CartItem::new)
            .map(cartItem -> {
                cart.getCartItems().add(cartItem);
                return cart;
            })))
        .flatMap(cartRepo::save);
    }
}
