package com.example.springmicro.reactive.controller;

import com.example.springmicro.reactive.entity.Cart;
import com.example.springmicro.reactive.entity.Item;
import com.example.springmicro.reactive.service.InventoryService;
import reactor.core.publisher.Mono;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;


@Controller
public class HomeController {

	private final InventoryService inventoryService;

	public HomeController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping
    Mono<Rendering> home(
        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient auth,
        @AuthenticationPrincipal OAuth2User user){
            return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", inventoryService.getInventory())
                .modelAttribute("cart", inventoryService.getCart(cartName(user))
                    .defaultIfEmpty(new Cart(cartName(user))))
                .modelAttribute("userName", user.getName())
                .modelAttribute("authorities", user.getAuthorities())
                .modelAttribute("clientName", auth.getClientRegistration().getClientName())
                .modelAttribute("userAttributes", user.getAttributes())
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@AuthenticationPrincipal OAuth2User user, @PathVariable String id){
        return inventoryService.addItemToCart(cartName(user), id).thenReturn("redirect:/");
    }

    @DeleteMapping("/remove/{id}")
    Mono<String> removeFromCart(@AuthenticationPrincipal OAuth2User user, @PathVariable String id){
        return inventoryService.removeOnceFromCart(cartName(user), id).thenReturn("redirect:/");
    }

    @PostMapping
	@ResponseBody
	Mono<Item> createItem(@RequestBody Item newItem) {
		return this.inventoryService.saveItem(newItem);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	Mono<Void> deleteItem(@PathVariable String id) {
		return this.inventoryService.deleteItem(id);
	}

	private static String cartName(OAuth2User oAuth2User) {
		return oAuth2User.getName() + "'s Cart";
	}

}
