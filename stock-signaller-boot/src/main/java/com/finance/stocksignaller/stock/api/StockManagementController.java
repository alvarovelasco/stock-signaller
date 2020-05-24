package com.finance.stocksignaller.stock.api;

import static com.finance.stocksignaller.stock.model.StockRecordFactory.convert;
import static com.finance.stocksignaller.stock.model.StockRecordFactory.from;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finance.stocksignaller.stock.model.StockRecord;
import com.finance.stocksignaller.stock.model.StockRecordDTO;
import com.finance.stocksignaller.stock.model.StockRecordFactory;
import com.finance.stocksignaller.stock.repo.CustomStockRecordRepository;
import com.finance.stocksignaller.stock.repo.DuplicateStockException;

@RestController
public class StockManagementController {

	@Autowired
	private CustomStockRecordRepository customStockRecordRepository;

	@GetMapping("/stocks")
	List<StockRecordDTO> all() {
		return customStockRecordRepository.findAll().stream().map(StockRecordFactory::convert)
				.collect(Collectors.toList());
	}

	@PostMapping("/stocks")
	StockRecordDTO newStock(@RequestBody StockRecordDTO stockRecord) throws DuplicateStockException {
		return convert(customStockRecordRepository.save(from(stockRecord)));
	}

	@PutMapping("/stocks/{symbol}")
	StockRecordDTO replaceStock(@RequestBody StockRecordDTO stockRecord, @PathVariable String symbol)
			throws DuplicateStockException {
		StockRecord existingStock = customStockRecordRepository.findBySymbol(symbol);
		if (existingStock != null) {
			customStockRecordRepository.delete(existingStock);
		}
		return convert(customStockRecordRepository.save(from(stockRecord)));
	}

	@DeleteMapping("/stocks/{symbol}")
	void deleteStock(@PathVariable String symbol) {
		StockRecord stockToDelete = customStockRecordRepository.findBySymbol(symbol);
		customStockRecordRepository.delete(stockToDelete);
	}

}
