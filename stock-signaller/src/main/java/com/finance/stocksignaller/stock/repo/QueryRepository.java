package com.finance.stocksignaller.stock.repo;

import java.util.List;

import com.finance.stocksignaller.stock.model.StockRecord;

public interface QueryRepository {

	List<StockRecord> findAll();
	
	StockRecord findBySymbol(String symbol);
	

	boolean exists(StockRecord stockRecord);
}
