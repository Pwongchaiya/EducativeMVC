package com.example.springmicro.reactive.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.example.springmicro.reactive.entity.CartItem;
import org.springframework.data.annotation.Id;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	//write your code here
    private @Id String id;
    private List<CartItem> cartItems;

    public Cart(String id){
        this(id, new ArrayList<>());
    }

    
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cart cart = (Cart) o;
		return Objects.equals(id, cart.id) && Objects.equals(cartItems, cart.cartItems);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cartItems);
	}

	@Override
	public String toString() {
		return "Cart{" + "id='" + id + '\'' + ", cartItems=" + cartItems + '}';
	}
}
