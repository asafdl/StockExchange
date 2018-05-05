package com.StockExchange.api.dataObjects;

import java.util.List;

import com.StockExchange.dataMaps.StockConcurrentMap;
import com.StockExchange.pojo.Stock;


public class Client {

	private int id;
	private StockConcurrentMap stockProtfolio = new StockConcurrentMap();
	
	public Client(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public double getProtfolioValue() {
		List<Stock> stockState = getCurrentStateOfProtfolio();
		double sum = 0;
		for(Stock stock : stockState) 
			sum+=stock.getValue();
		
		return sum;
	}
	
	public void addStockToProtfolio(Stock stock) {
		stockProtfolio.updateValueIntoMap(stock.getName(),stock);
	}
	
	public void removeStockFromProtfolio(String stockName) {
		stockProtfolio.deleteEntry(stockName);
	}
	
	public List<Stock> getCurrentStateOfProtfolio() {
		return stockProtfolio.getCurrentState();
	}
	
	
}
