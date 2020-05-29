package com.finance.stocksignaller.stock.repo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.finance.stocksignaller.stock.model.StockRecord;

@Repository("cachedStockRecordRepository")
@CacheConfig(cacheNames = "stocks")
public class CachedStockRecordRepositoryImpl implements CustomStockRecordRepository {

	private Logger log = LoggerFactory.getLogger(CachedStockRecordRepositoryImpl.class);

	@Autowired
	@Qualifier("rawCustomStockRepository")
	private CustomStockRecordRepository customStockRecordRepository;

	@Cacheable(value="stocks", sync = true)
	@Override
	public List<StockRecord> findAll() {
		log.debug("Find all");
		return customStockRecordRepository.findAll();
	}

	@Cacheable(value="stocks", key = "#symbol", sync = true)
	@Override
	public StockRecord findBySymbol(String symbol) {
		return customStockRecordRepository.findBySymbol(symbol);
	}

	@Override
	public boolean exists(StockRecord stockRecord) {
		return customStockRecordRepository.exists(stockRecord);
	}

	@CacheEvict(key = "#stockRecord.symbol", value = "stocks")
	@Override
	public StockRecord save(StockRecord stockRecord) {
		return customStockRecordRepository.save(stockRecord);
	}

	@CacheEvict(value="stocks", allEntries = true)
	@Override
	public Iterable<StockRecord> saveAll(Iterable<StockRecord> stockRecords) {
		return StreamSupport.stream(stockRecords.spliterator(), false).map(this::save)
				.collect(Collectors.toList());
	}

	@CacheEvict(key = "#stockrecord.symbol", value = "stocks")
	@Override
	public void delete(StockRecord stockRecord) {
		customStockRecordRepository.delete(stockRecord);
	}

}
