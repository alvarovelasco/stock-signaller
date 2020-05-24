package com.finance.stocksignaller.stock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("stocks")
public class StockRecord {

	@Id
	private String id;

	private String symbol;

	private String name;

	public StockRecord(String symbol, String name) {
		this.symbol = symbol.toUpperCase();
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StockRecord [id=" + id + ", symbol=" + symbol + ", name=" + name + "]";
	}

}
