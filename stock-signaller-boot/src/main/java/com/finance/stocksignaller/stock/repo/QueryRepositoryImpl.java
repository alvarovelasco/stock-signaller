package com.finance.stocksignaller.stock.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.finance.stocksignaller.stock.model.StockRecord;

@Repository("queryRepo")
public class QueryRepositoryImpl implements QueryRepository {

	private Logger log = LoggerFactory.getLogger(QueryRepositoryImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<StockRecord> findAll() {
		log.debug("Fetch all");
		return mongoTemplate.findAll(StockRecord.class);
	}

	@Override
	public StockRecord findBySymbol(String symbol) {
		log.debug("Fetch by {}", symbol);
		Query query = Query.query(Criteria.where("symbol").is(symbol));
		return mongoTemplate.findOne(query, StockRecord.class);
	}

	@Override
	public boolean exists(StockRecord stockRecord) {
		Query query = Query.query(Criteria.where("symbol").is(stockRecord.getSymbol()));
		return mongoTemplate.exists(query, StockRecord.class);
	}

}
