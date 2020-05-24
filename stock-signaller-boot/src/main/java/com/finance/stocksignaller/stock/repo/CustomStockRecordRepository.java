package com.finance.stocksignaller.stock.repo;

import com.finance.stocksignaller.stock.model.StockRecord;

public interface CustomStockRecordRepository extends QueryRepository {

	StockRecord save(StockRecord stockRecord) ;
	
	Iterable<StockRecord> saveAll(Iterable<StockRecord> stockRecords) ;

	void delete(StockRecord stockRecord);
	
	
}
