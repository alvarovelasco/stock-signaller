package com.finance.stocksignaller.stock.model;

public class StockRecordFactory {

	
	public static StockRecord from(StockRecordDTO dto) {
		return new StockRecord(dto.getSymbol(), dto.getName());
	}
	
	public static StockRecordDTO convert(StockRecord record) {
		return new StockRecordDTO(record.getSymbol(), record.getName());
	}
}
