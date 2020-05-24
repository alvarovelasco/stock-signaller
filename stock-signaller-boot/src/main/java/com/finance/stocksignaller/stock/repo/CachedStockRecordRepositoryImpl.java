package com.finance.stocksignaller.stock.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.finance.stocksignaller.stock.model.StockRecord;

@Repository("cachedStockRecordRepository")
public class CachedStockRecordRepositoryImpl implements CustomStockRecordRepository {

	@Autowired
	@Qualifier("rawCustomStockRepository")
	private CustomStockRecordRepository customStockRecordRepository;

	@Override
	public List<StockRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockRecord findBySymbol(String symbol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(StockRecord stockRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StockRecord save(StockRecord stockRecord) throws DuplicateStockException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<StockRecord> saveAll(Iterable<StockRecord> stockRecords) throws DuplicateStockException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(StockRecord stockRecord) {
		// TODO Auto-generated method stub
		
	}
	
	
}
