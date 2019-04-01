package com.cnb.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnb.ecommerce.client.DataClient;
import com.cnb.ecommerce.domain.CnbUser;
import com.cnb.ecommerce.domain.ItemSku;
import com.cnb.ecommerce.service.CartItemService;
import com.cnb.ecommerce.service.ItemSkuService;
import com.cnb.ecommerce.service.TaskData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class HomeController {	
	@Autowired
	private TaskData taskData;
	
	@Autowired
	private ItemSkuService itemSkuService;
	
	@Autowired
	private DataClient dataClient;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/hours")
	public String hours() {
		return "hours";
	}
	
	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}

	@RequestMapping("/catalog")
	public String catalog(Model model, Principal principal) {
		CnbUser cnbUser = taskData.getUser();
		model.addAttribute("user", cnbUser);
		List<ItemSku> skuList = itemSkuService.findAll();
		model.addAttribute("skuList", skuList);
		model.addAttribute("activeAll",true);	
		
		return "catalog";
	}	
	
	@RequestMapping("/cnb")
	public String cnb(Model model, Principal principal,String jsonData) {
		
		//Sample dummy data
		String gsonResponse = "{  \n   \"itemSkuList\":[  \n      {  \n         \"masterSKU\":\"54324\",\n         \"product\":\"Hp Pavilion\",\n         \"type\":\"Laptop\",\n         \"brand\":\"HP\",\n         \"model\":\"Pavilion 980\",\n        "
				+ " \"description\":\"Highly durable and long lasting laptop.\",\n         \"inventory\":\"300\",\n         \"price_CAD\":\"500\",\n         \"price_USD\":\"430\",\n         \"length\":\"12 inches\",\n         \"width\":\"8 inches\",\n        "
				+ " \"height\":\"1 inch\",\n         \"weight\":\"500 gms\",\n         \"imageUrl\":\"https://product-images.www8-hp.com/digmedialib/prodimg/lowres/c06074785.png\"\n      },\n      {  \n         \"masterSKU\":\"54000\",\n         "
				+ "\"product\":\"Alienware\",\n         \"type\":\"Laptop\",\n         \"brand\":\"Dell\",\n         \"model\":\"Alienware Area51\",\n         \"description\":\"Best gaming laptop.\",\n         \"inventory\":\"300\",\n        "
				+ " \"price_CAD\":\"1500\",\n         \"price_USD\":\"1300\",\n         \"length\":\"14 inches\",\n         \"width\":\"10 inches\",\n         \"height\":\"2 inch\",\n         \"weight\":\"1500 gms\",\n        "
				+ " \"imageUrl\":\"https://i.dell.com/sites/csimages/Video_Imagery/all/new-m15-image-ces-nvidia.jpg\"\n      }\n   ],\n   \"userDetails\":{  \n      \"userName\":\"CNB\",\n      \"userAddress\":\"Canada\"\n   }\n}";
				
		try {
			dataClient.readCnbJson(gsonResponse);
			dataClient.fillInitialRequest();
			dataClient.fillCnbUser();
			dataClient.fillTaskData();
		}
		
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "index";
	}
	
	@RequestMapping("/itemSkuDetail")
	public String itemSkuDetail(
			@PathParam("id") Long id, Model model, Principal principal
			) {
		CnbUser cnbUser = taskData.getUser();
		model.addAttribute("user", cnbUser.getUserDetails());
		
		itemSkuService.setIdCatalogMap();
		ItemSku itemSku = itemSkuService.findOne(String.valueOf(id));
		model.addAttribute("itemSku", itemSku);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		return "itemSkuDetail";
	}
}
