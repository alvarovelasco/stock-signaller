package com.finance.stocksignaller.stock.repo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.finance.stocksignaller.stock.model.StockRecord;

@Repository("rawCustomStockRepository")
public class CustomStockRecordRepositoryImpl implements CustomStockRecordRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	@Qualifier("queryRepo")
	private QueryRepository queryRepository;

	@Override
	public StockRecord save(StockRecord stockRecord) {
		if (exists(stockRecord))
			throw new DuplicateStockException(stockRecord);

		return mongoTemplate.save(stockRecord);
	}

	@Override
	public Iterable<StockRecord> saveAll(Iterable<StockRecord> stockRecords) {
		for (StockRecord record : stockRecords) {
			save(record);
		}
		return StreamSupport.stream(stockRecords.spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public boolean exists(StockRecord stockRecord) {
		return queryRepository.exists(stockRecord);
	}

	@Override
	public List<StockRecord> findAll() {
		return queryRepository.findAll();
	}

	@Override
	public StockRecord findBySymbol(String symbol) {
		return queryRepository.findBySymbol(symbol);
	}

	@Override
	public void delete(StockRecord stockRecord) {
		mongoTemplate.remove(stockRecord);
	}
}
