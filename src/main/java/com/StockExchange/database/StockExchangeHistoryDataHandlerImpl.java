package com.StockExchange.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.StockExchange.api.dataObjects.Client;
import com.StockExchange.api.dataObjects.StockExchange;
import com.StockExchange.dataMaps.ClientConcurrentMap;
import com.StockExchange.dataMaps.StockConcurrentMap;
import com.StockExchange.pojo.Stock;
import com.StockExchange.pojo.StockListDataStructure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * For the purposes of this exercise our database is a file, this can be changed
 * by changing the implementation of IStockExchangeHistoryHandler
 * 
 * @author Asaf
 *
 */
public class StockExchangeHistoryDataHandlerImpl implements IStockExchangeHistoryDataHandler {

	private final String STOCK_STREAM_FILEPATH = "src/main/resources/StockStream.json";
	private final String CLIENT_HISTORY_FILEPATH = "src/main/resources/ClientHistory.json";
	
	@Override
	public ClientConcurrentMap getClientHistoryFormDb() throws FileNotFoundException {
		ClientConcurrentMap ccm = new ClientConcurrentMap();
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		BufferedReader br = new BufferedReader(new FileReader(CLIENT_HISTORY_FILEPATH));
		JsonArray jsonArr = parser.parse(br).getAsJsonArray();
		List<StockListDataStructure> clientHistoryList = new ArrayList<>();
		Type clientListDataType = new TypeToken<List<StockListDataStructure>>() {
		}.getType();
		clientHistoryList = gson.fromJson(jsonArr, clientListDataType);
		updateClientValuesIntoMapFromJson(clientHistoryList, ccm);
		return ccm;
	}

	@Override
	public void saveClientStateToDb(ClientConcurrentMap ccm) {
		List<Client> clientState = ccm.getCurrentState();
		List<StockListDataStructure> clientHistoryList = createStockListDataStructureFromClientList(clientState);
		try (Writer writer = new FileWriter(CLIENT_HISTORY_FILEPATH)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(clientHistoryList, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateClientValuesIntoMapFromJson(List<StockListDataStructure> clientHistoryList,
			ClientConcurrentMap ccm) {
		for (StockListDataStructure stockList : clientHistoryList) {
			Client cli = new Client(stockList.getAttachedClientId());
			for (Stock stock : stockList.getStocks()) {
				cli.addStockToProtfolio(stock);
			}
			ccm.updateValueIntoMap(cli.getId(), cli);
		}
	}

	private List<StockListDataStructure> createStockListDataStructureFromClientList(List<Client> clientState) {
		List<StockListDataStructure> toReturn = new ArrayList<>();
		for (Client cli : clientState) {
			StockListDataStructure slds = new StockListDataStructure();
			List<Stock> stocks = cli.getCurrentStateOfProtfolio();
			slds.setStocks(getStockArrFromList(stocks));
			slds.setAttachedClientId(cli.getId());
			toReturn.add(slds);
		}
		return toReturn;
	}

	private Stock[] getStockArrFromList(List<Stock> stockList) {
		Stock[] stockArr = new Stock[stockList.size()];
		for (int i = 0; i < stockList.size(); i++)
			stockArr[i] = stockList.get(i);
		return stockArr;
	}

	@Override
	public StockExchange initStockExchange() throws FileNotFoundException {
		return new StockExchange(getStockMapFromDb(), getClientHistoryFormDb());
	}

	@Override
	public StockConcurrentMap getStockMapFromDb() throws FileNotFoundException {
		StockConcurrentMap scm = new StockConcurrentMap();
		Gson gson = new Gson();
		Type stockListType = new TypeToken<List<Stock>>() {}.getType();
		JsonParser parser = new JsonParser();
		BufferedReader br = new BufferedReader(new FileReader(STOCK_STREAM_FILEPATH));
		JsonArray jsonArr = parser.parse(br).getAsJsonArray();
		List<Stock> stockList = gson.fromJson(jsonArr, stockListType);
		for(Stock st : stockList)
			scm.updateValueIntoMap(st.getName(), st);
		return scm;
	}

}
