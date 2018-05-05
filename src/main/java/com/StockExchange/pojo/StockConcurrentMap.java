package com.StockExchange.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class StockConcurrentMap extends ConcurrentHashMapWrapper<String, Stock>{
	

	public double getStockValueByName(String stockName) throws NoSuchElementException {
		double stockVal = 0;
		Stock stock = getValueAndThrowExceptionIfNotFound(stockName);
		stockVal = stock.getPrice();
		return stockVal;
	}
	
	public void updateStockValue(String name, double value) throws NoSuchElementException {
		Stock stock = getValueAndThrowExceptionIfNotFound(name);
		stock.setPrice(value);
	}

	
}
