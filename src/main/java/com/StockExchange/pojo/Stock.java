package com.StockExchange.pojo;

public class Stock {
    
    private String name;
    private double price;
    private int amount;
    
    public Stock(String name, double price,int amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    
    public String getName() {
    	return name;
    }
    
    public double getPrice() {
    	return price;
    }
    
    public int getAmount() {
    	return amount;
    }
    
    public void setAmount(int amount) {
    	this.amount = amount;
    }
    
    public void setPrice(double price) {
    	this.price = price;
    }
    
    public double getValue() {
    	return (double)amount * price;
    }
    
    @Override
    public boolean equals(Object obj) {
    	return (this.name.equals(((Stock) obj).getName()) && this.price == ((Stock)obj).getPrice());
    }
}
