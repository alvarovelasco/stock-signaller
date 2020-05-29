package com.finance.stocksignaller.stock.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockRecordRepositoryConfiguration {

	@Autowired
	@Qualifier("cacheStockRecordRepository")
	private CustomStockRecordRepository cacheRepo;
	
	@Autowired
	@Qualifier("rawStockRecordRepository")
	private CustomStockRecordRepository rawRepo;
	
	@Bean
	public CustomStockRecordRepository stockRecordRepository(
			@Value("${cache.stock.record.enabled:false}") Boolean cacheEnabled) {
		if (cacheEnabled) {
			return cacheRepo;
		}
		return rawRepo;
	}
	
	@Bean
	public CustomStockRecordRepository rawStockRecordRepository() {
		return new CustomStockRecordRepositoryImpl();
	}
	
	@Bean
	public CustomStockRecordRepository cacheStockRecordRepository() {
		return new CachedStockRecordRepositoryImpl();
	}
	
	
}
