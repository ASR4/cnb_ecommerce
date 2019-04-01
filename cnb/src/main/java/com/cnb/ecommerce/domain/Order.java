package com.cnb.ecommerce.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
	
	private Long id;
	private Date orderDate;
	private String orderStatus;
	private BigDecimal orderTotal;
	private List<CartItem> cartItemList;

	private CnbUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public CnbUser getUser() {
		return user;
	}

	public void setUser(CnbUser user) {
		this.user = user;
	}	
	
}
