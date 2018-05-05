package com.StockExchange.api.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.StockExchange.api.dataObjects.Client;
import com.StockExchange.api.dataObjects.StockExchange;
import com.google.gson.Gson;

@RestController
public class ClientController {

	StockExchange stockExchange;
	

	@RequestMapping("/addClient")
	public Client addClient(@RequestBody String clientJson) {
	  	Gson gson = new Gson();
	  	return gson.fromJson(clientJson, Client.class);
	}
	    
	@RequestMapping("/getClientProtfolioValue")
	public double getClientProtfolioValue(@RequestParam(value="clientId",required=true) int id) {   	
		return 0;
	}
}
