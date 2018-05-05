package com.StockExchange.database;

import java.io.FileNotFoundException;

import com.StockExchange.api.dataObjects.StockExchange;
import com.StockExchange.dataMaps.ClientConcurrentMap;
import com.StockExchange.dataMaps.StockConcurrentMap;

public interface IStockExchangeHistoryDataHandler {

	public ClientConcurrentMap getClientHistoryFormDb() throws Exception;
	
	public void saveClientStateToDb(ClientConcurrentMap ccm);
	
	public StockExchange initStockExchange() throws Exception;
	
	public StockConcurrentMap getStockMapFromDb() throws Exception;
}
