
package com.example.springmicro.reactive.entity;

import java.util.Objects;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CartItem {

    private Item item;
    private int quantity;

    public CartItem(Item item){
        this.item = item;
        this.quantity = 1;
    }

    public void increment(){
        quantity++;
    }

    public void decrement(){
        quantity--;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CartItem cartItem = (CartItem) o;
		return quantity == cartItem.quantity && Objects.equals(item, cartItem.item);
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, quantity);
	}

	@Override
	public String toString() {
		return "CartItem{" + "item=" + item + ", quantity=" + quantity + '}';
	}
}
