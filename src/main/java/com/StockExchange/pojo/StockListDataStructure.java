package com.StockExchange.pojo;

public class StockListDataStructure {
	private Integer attachedClientId;
	private Stock[] stocks;
	
	public Integer getAttachedClientId() {
		return attachedClientId;
	}
	public void setAttachedClientId(Integer attachedClientId) {
		this.attachedClientId = attachedClientId;
	}
	public Stock[] getStocks() {
		return stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}
}
