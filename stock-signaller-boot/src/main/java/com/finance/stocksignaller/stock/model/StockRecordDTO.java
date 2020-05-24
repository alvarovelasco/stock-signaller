package com.finance.stocksignaller.stock.model;

import java.io.Serializable;

public final class StockRecordDTO implements Serializable {

	private String symbol;

	private String name;
	
	/**
	 * To avoid Jackson error for no default constructor found
	 */
	public StockRecordDTO() {
	}

	public StockRecordDTO(String symbol, String name) {
		this.symbol = symbol.toUpperCase();
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StockRecordDTO [symbol=" + symbol + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockRecordDTO other = (StockRecordDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
