package com.cnb.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnb.ecommerce.domain.ItemSku;
import com.cnb.ecommerce.service.ItemSkuService;
import com.cnb.ecommerce.service.TaskData; 

@Service
public class ItemSkuServiceImpl implements ItemSkuService {
	@Autowired
	private TaskData taskData;
	
	Map<String, ItemSku> idCatalogMap = new HashMap<>();
	
	@Override
	public void setIdCatalogMap() {
		List<ItemSku> catalogList = findAll();
		
		for(ItemSku item : catalogList) {
			if(item!=null) {
				idCatalogMap.put(item.getMasterSKU(), item);
			}
		}
	}
	
	@Override
	public ItemSku findOne(String masterSKU) {
		return this.idCatalogMap.get(masterSKU);		
	}
	

	@Override
	public List<ItemSku> findAll() {
		List<ItemSku> catalogList = taskData.getInitialRequest().getCnbData().getItemSkuList();		
		return catalogList;
	}

	@Override
	public List<ItemSku> findByCategory(String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemSku> blurrySearch(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
