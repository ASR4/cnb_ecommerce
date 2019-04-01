package com.cnb.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnb.ecommerce.domain.CartItem;
import com.cnb.ecommerce.domain.CnbUser;
import com.cnb.ecommerce.domain.ItemSku;
import com.cnb.ecommerce.domain.ShoppingCart;
import com.cnb.ecommerce.service.CartItemService;
import com.cnb.ecommerce.service.ItemSkuService;
import com.cnb.ecommerce.service.ShoppingCartService;
import com.cnb.ecommerce.service.TaskData;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private TaskData taskData;
	
	@Autowired
	private ItemSkuService itemSkuService;

	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		CnbUser user = taskData.getUser();
//		ShoppingCart shoppingCart = user.getShoppingCart();
		ShoppingCart shoppingCart = new ShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findListOfCartFromShoppingCart();
		
		if(cartItemList == null) {
			return "index";
		}
		//TODO remove the print block
				System.out.println("[DEBUG] : In ShoppingCartController");
				for(CartItem cartI : cartItemList) {
					System.out.println("[DEBUG] : master sku : " + cartI.getItemSku().getMasterSKU());
//					System.out.println("[DEBUG] : quantity : " + cartI.getQty());
//					System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getCartItemList());
//					System.out.println("[DEBUG] : shopping cart list : " + cartI.getShoppingCart().getId());
				}
		
		shoppingCartService.updateShoppingCart(shoppingCart, cartItemList);
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		
		return "cnbShoppingCart";
	}
	
	//CNB
	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("itemSku") ItemSku itemSku,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
			) {
		CnbUser user = taskData.getUser();
		itemSku = itemSkuService.findOne(itemSku.getMasterSKU());
		
		if (Integer.parseInt(qty) > itemSku.getInventory()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/itemSkuDetail?id=" + itemSku.getInventory();
		}
		
		cartItemService.addItemSkuToCartItem(itemSku, user, Integer.parseInt(qty));
		model.addAttribute("addItemSkuSuccess", true);
		
		return "forward:/itemSkuDetail?id=" + itemSku.getMasterSKU();
	}	
	
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem, cartItem.getShoppingCart());
		
		return "forward:/shoppingCart/cart";
	}
	
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id));
		
		return "forward:/shoppingCart/cart";
	}
}
