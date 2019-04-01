package com.cnb.ecommerce.service;

import java.util.List;

import com.cnb.ecommerce.domain.CartItem;
import com.cnb.ecommerce.domain.ShoppingCart;

public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, List<CartItem> cartItemList);
	
	void clearShoppingCart(ShoppingCart shoppingCart);
}
