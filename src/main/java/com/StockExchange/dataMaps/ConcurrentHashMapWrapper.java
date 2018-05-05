package com.StockExchange.dataMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWrapper<K,V> {
	ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
	
	public boolean isKeyExists(K key) {
		return map.containsKey(key);
	}
	
	public void updateValueIntoMap(K key,V value) {
		map.put(key, value);
		
	}
	
	public V getValueAndThrowExceptionIfNotFound(K key) throws NoSuchElementException {
		if(!isKeyExists(key))
			throw new NoSuchElementException("Looking for key: " + key.toString() + ", but cannot be found.");
		return map.get(key);
	}
	
	public List<V> getCurrentState(){
		List<V> stateList = new ArrayList<>();
		//Locking for reading, not sure if needed or we have no problem here.
		synchronized (this) {
			for(K stockName : map.keySet()) {
				stateList.add(map.get(stockName));
			}
		}
		return stateList;
	}
	
	public void deleteEntry(K key) {
		if(isKeyExists(key))
			map.remove(key);
	}
	

}
