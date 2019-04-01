package com.cnb.ecommerce.service;

import java.util.List;

import com.cnb.ecommerce.domain.CartItem;
import com.cnb.ecommerce.domain.CnbUser;
import com.cnb.ecommerce.domain.ItemSku;
import com.cnb.ecommerce.domain.ShoppingCart;

public interface CartItemService {
	List<CartItem> findListOfCartFromShoppingCart();
	
	CartItem updateCartItem(CartItem cartItem, ShoppingCart shoppingCart);
	
	CartItem addItemSkuToCartItem(ItemSku itemSku, CnbUser user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	ShoppingCart getShoppingCart();

	void setShoppingCart(ShoppingCart shoppingCart);

}
