package com.finance.stocksignaller.stock.repo;

import com.finance.stocksignaller.stock.model.StockRecord;

public final class DuplicateStockException extends RuntimeException {

	private static final long serialVersionUID = 14525L;

	public DuplicateStockException(StockRecord stockRecord) {
		super(String.format("Stock with symbol {0} already exists", stockRecord.getSymbol()));
	}
}
