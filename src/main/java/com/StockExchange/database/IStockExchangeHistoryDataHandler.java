package com.StockExchange.database;

import java.io.FileNotFoundException;

import com.StockExchange.pojo.ClientConcurrentMap;

public interface IStockExchangeHistoryDataHandler {

	public ClientConcurrentMap getClientHistoryFormDb() throws Exception;
	
	public void saveClientStateToDb(ClientConcurrentMap ccm);
}
