package com.StockExchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.StockExchange.database.StockExchangeHistoryDataHandlerImpl;
import com.StockExchange.pojo.Client;
import com.StockExchange.pojo.ClientConcurrentMap;
import com.StockExchange.pojo.Stock;
import com.jayway.restassured.RestAssured;

public class ClientAndStockTests {

	protected final String APPLICATION_JSON = "application/json";
	@BeforeClass
	public static void setup() {
		String port = System.getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(8080);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		String basePath = System.getProperty("server.base");
		if (basePath == null) {
			basePath = "";
		}
		RestAssured.basePath = basePath;

		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;

	}

	@Test
	public void createClientProtfolio() {
		Stock stock1 = new Stock("stock1", 1, 1);
		Stock stock2 = new Stock("stock2", 2, 2);
		Stock stock3 = new Stock("stock3", 3, 3);

		Client testClient = createClientProtfolio(0,stock1, stock2, stock3);

		List<Stock> stockProtfolioList = testClient.getCurrentStateOfProtfolio();

		assertStockState(stockProtfolioList, stock1, stock2, stock3);
	}
	
	@Test
	public void testAPINewClient() {
		
		HashMap<String,String> client = new HashMap<>();
		client.put("id", "1");
		
		RestAssured.given()
		.contentType(APPLICATION_JSON)
		.body(client)
		.when()
		.post("/addClient");
	}
	
	@Test
	public void createClientHistory() {
		ClientConcurrentMap ccm = new ClientConcurrentMap();
		StockExchangeHistoryDataHandlerImpl dataHandler = new StockExchangeHistoryDataHandlerImpl();
		Stock stock1 = new Stock("stock1", 1, 1);
		Stock stock2 = new Stock("stock2", 2, 2);
		Stock stock3 = new Stock("stock3", 3, 3);
		Stock stock4 = new Stock("stock4",4,4);
		
		Client cli = createClientProtfolio(0, stock1,stock2,stock3,stock4);
		Client cli1 = createClientProtfolio(1, stock1,stock2,stock3);
		
		ccm.updateValueIntoMap(cli.getId(), cli);
		ccm.updateValueIntoMap(cli1.getId(), cli1);
		
		dataHandler.saveClientStateToDb(ccm);
		
	}
	
	@Test
	public void setUpClientConcurrentMapFromHistory() throws FileNotFoundException {
		StockExchangeHistoryDataHandlerImpl dataHandler = new StockExchangeHistoryDataHandlerImpl();
		ClientConcurrentMap ccm = dataHandler.getClientHistoryFormDb();
		
		assertTrue(ccm.isKeyExists(0));
		assertTrue(ccm.isKeyExists(1));
	}

	private Client createClientProtfolio(int clientId, Stock... stockList) {
		Client testClient = new Client(clientId);
		for (Stock stock : stockList)
			testClient.addStockToProtfolio(stock);
		return testClient;
	}

	private void assertStockState(List<Stock> stockProtfolioList, Stock... stocks) {
		for (Stock stock : stockProtfolioList) {
			if (stock.getName().equals("stock1"))
				assertEquals(stocks[0], stock);
			else if (stock.getName().equals("stock2"))
				assertEquals(stocks[1], stock);
			else if (stock.getName().equals("stock3"))
				assertEquals(stocks[2], stock);
		}
	}
}
