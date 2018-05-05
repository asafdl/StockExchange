package com.StockExchange.api.dataObjects;

import com.StockExchange.dataMaps.ClientConcurrentMap;
import com.StockExchange.dataMaps.StockConcurrentMap;

public class StockExchange {
	StockConcurrentMap stockMap = new StockConcurrentMap();
	ClientConcurrentMap clientMap = new ClientConcurrentMap();

	
	public StockExchange(StockConcurrentMap stockMap, ClientConcurrentMap clientMap) {
		this.stockMap = stockMap;
		this.clientMap = clientMap;
	}
	
	public Client getClientFromMap(Integer id) {
		Client cli = null;
		try {
			cli = clientMap.getValueAndThrowExceptionIfNotFound(id);
		} catch (Exception e) {
			//TODO: Handle exc
		}
		
		return cli;
	}
	
	public double getValueOfStock(String stockName) {
		return stockMap.getStockValueByName(stockName);
	}

}
