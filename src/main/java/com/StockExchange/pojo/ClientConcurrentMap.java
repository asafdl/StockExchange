package com.StockExchange.pojo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class ClientConcurrentMap extends ConcurrentHashMapWrapper<Integer, Client> {

	public void setUpClientMapFromHistoryFile(String filePath) throws FileNotFoundException {
		if (filePath == null || filePath.isEmpty())
			filePath = ".\\main\\resources\\ClientHistory.json";
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(filePath));
		List<StockListDataStructure> clientHistoryList = new ArrayList<>();
		Type clientListDataType = new TypeToken<List<StockListDataStructure>>() {
		}.getType();
		clientHistoryList = gson.fromJson(reader, clientListDataType);
		updateClientValuesIntoMapFromJson(clientHistoryList);
	}

	private void updateClientValuesIntoMapFromJson(List<StockListDataStructure> clientHistoryList) {
		for (StockListDataStructure stockList : clientHistoryList) {
			Client cli = new Client(stockList.getAttachedClientId());
			for (Stock stock : stockList.getStocks()) {
				cli.addStockToProtfolio(stock);
			}
			updateValueIntoMap(cli.getId(), cli);
		}
	}

	public void saveCurrentStateAsClientHistoryToFile() {
		List<Client> clientState = getCurrentState();
		List<StockListDataStructure> clientHistoryList = createStockListDataStructureFromClientList(clientState);
		try (Writer writer = new FileWriter("src/main/resources/ClientHistroy.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(clientHistoryList, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		for(int i=0; i<stockList.size(); i++)
			stockArr[i] = stockList.get(i);
		return stockArr;
	}
}
