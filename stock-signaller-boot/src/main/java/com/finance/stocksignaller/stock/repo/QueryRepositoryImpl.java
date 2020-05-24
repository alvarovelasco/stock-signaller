package com.finance.stocksignaller.stock.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.finance.stocksignaller.stock.model.StockRecord;

@Repository("queryRepo")
public class QueryRepositoryImpl implements QueryRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<StockRecord> findAll() {
		return mongoTemplate.findAll(StockRecord.class);
	}

	@Override
	public StockRecord findBySymbol(String symbol) {
		Query query = Query.query(Criteria.where("symbol").is(symbol));
		return mongoTemplate.findOne(query, StockRecord.class);
	}

	@Override
	public boolean exists(StockRecord stockRecord) {
		Query query = Query.query(Criteria.where("symbol").is(stockRecord.getSymbol()));
		return mongoTemplate.exists(query, StockRecord.class);
	}

}
