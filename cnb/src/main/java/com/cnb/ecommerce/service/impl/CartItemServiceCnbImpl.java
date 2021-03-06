package com.cnb.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cnb.ecommerce.domain.CartItem;
import com.cnb.ecommerce.domain.CnbUser;
import com.cnb.ecommerce.domain.ItemSku;
import com.cnb.ecommerce.domain.ItemSkuToCartItem;
import com.cnb.ecommerce.domain.Order;
import com.cnb.ecommerce.domain.ShoppingCart;
import com.cnb.ecommerce.service.CartItemService;

@Service
public class CartItemServiceCnbImpl implements CartItemService {
	
	List<CartItem> listOfCartItems = new ArrayList<CartItem>();
	List<ItemSkuToCartItem> listOfItemSkuToCartItem = new ArrayList<ItemSkuToCartItem>();
	List<Order> listOfOrders = new ArrayList<Order>();
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	@Override
	public List<CartItem> findListOfCartFromShoppingCart() {
		if(this.shoppingCart.getCartItemList() == null) {
			return null;
		}
		return this.shoppingCart.getCartItemList();
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem, ShoppingCart shoppingCart) {
		if(listOfCartItems.contains(cartItem)) {
			listOfCartItems.remove(cartItem);
		}
		BigDecimal bigDecimal = new BigDecimal(cartItem.getItemSku().getPrice_CAD()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		//cartItemRepository.save(cartItem);
		listOfCartItems.add(cartItem);
		shoppingCart.setCartItemList(listOfCartItems);
		this.shoppingCart = shoppingCart;
		
		return cartItem;
	}

	@Override
	public CartItem addItemSkuToCartItem(ItemSku itemSku, CnbUser user, int qty) {
		List<CartItem> cartItemList = findListOfCartFromShoppingCart();
		
		if(cartItemList != null) {
			for (CartItem cartItem : cartItemList) {
				if(itemSku.getMasterSKU() == cartItem.getItemSku().getMasterSKU()) {
					//Hardcoding cart id as this is not a persistent session
					cartItem.setId(1234L);
					cartItem.setQty(cartItem.getQty()+qty);
					cartItem.setSubtotal(new BigDecimal(itemSku.getPrice_CAD()).multiply(new BigDecimal(qty)));
//					listOfCartItems.add(cartItem);
					//cartItemRepository.save(cartItem);
					shoppingCart.setCartItemList(listOfCartItems);
					
					//TODO remove the print block
					System.out.println("[DEBUG] : In CartItemServiceCnbImpl + cartItem list NOT empty");
					for(CartItem cartI : listOfCartItems) {
						System.out.println("[DEBUG] : master sku : " + cartI.getItemSku().getMasterSKU());
						System.out.println("[DEBUG] : quantity : " + cartI.getQty());
//						System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getCartItemList());
//						System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getId());
					}
					
					return cartItem;
				}
			}
		}
		
		CartItem cartItem = new CartItem();
		//Hardcoding cart id as this is not a persistent session
		cartItem.setId(1234L);
		cartItem.setShoppingCart(this.shoppingCart);
		cartItem.setItemSku(itemSku);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(itemSku.getPrice_CAD()).multiply(new BigDecimal(qty)));
		listOfCartItems.add(cartItem);
		//cartItem = cartItemRepository.save(cartItem);
		shoppingCart.setCartItemList(listOfCartItems);
		
		ItemSkuToCartItem itemSkuToCartItem = new ItemSkuToCartItem();
		itemSkuToCartItem.setItemSku(itemSku);
		itemSkuToCartItem.setCartItem(cartItem);
		listOfItemSkuToCartItem.add(itemSkuToCartItem);
		//itemSkuToCartItem.save(bookToCartItem);
		cartItem.setItemSkuToCartItemList(listOfItemSkuToCartItem);
		
		
		//TODO remove the print block
		System.out.println("[DEBUG] : In CartItemServiceCnbImpl");
		for(CartItem cartI : listOfCartItems) {
			System.out.println("[DEBUG] : master sku : " + cartI.getItemSku().getMasterSKU());
			System.out.println("[DEBUG] : quantity : " + cartI.getQty());
//			System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getCartItemList());
//			System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getId());
		}
		
		return cartItem;
	}

	@Override
	public CartItem findById(Long id) {
		for(CartItem cartItem : listOfCartItems) {
			if(cartItem.getId() == id) {
				return cartItem;
			}
		}
		return null;
	}

	@Override
	public void removeCartItem(CartItem cartItem) {
		if(listOfCartItems.contains(cartItem)) {
			listOfCartItems.remove(cartItem);
		}	
	}

	@Override
	public CartItem save(CartItem cartItem) {
		listOfCartItems.add(cartItem);
		return cartItem;
	}

//	@Override
//	public List<CartItem> findByOrder(Order order) {
//		for(Order orderFromList : listOfOrders) {
//			if(orderFromList == order) {
//				return orderFromList.getCartItemList();
//			}
//		}
//		return null;
//	}

}
