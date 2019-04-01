package com.cnb.ecommerce.service;

import java.util.List;

import com.cnb.ecommerce.domain.ItemSku;

public interface ItemSkuService {

	List<ItemSku> findAll ();
	
	ItemSku findOne (String masterSKU);
	
	void setIdCatalogMap();
	
	List<ItemSku> findByCategory(String brand);
	
	List<ItemSku> blurrySearch(String type);
}


