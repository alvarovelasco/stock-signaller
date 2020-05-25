package com.finance.stocksignaller.stock.api;

import static com.finance.stocksignaller.stock.model.StockRecordFactory.convert;
import static com.finance.stocksignaller.stock.model.StockRecordFactory.from;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class StockManagementController {

	private Logger log = LoggerFactory.getLogger(StockManagementController.class);

	@Autowired
	private CustomStockRecordRepository customStockRecordRepository;

	@GetMapping("/stocks")
	public List<StockRecordDTO> all() {
		log.debug("Fetch all stocks");
		return customStockRecordRepository.findAll().stream().map(StockRecordFactory::convert)
				.collect(Collectors.toList());
	}

	@PostMapping("/stocks")
	public StockRecordDTO newStock(@RequestBody StockRecordDTO stockRecord) {
		log.debug("newStock {}", stockRecord);
		return convert(customStockRecordRepository.save(from(stockRecord)));
	}

	@PutMapping("/stocks/{symbol}")
	public StockRecordDTO replaceStock(@RequestBody StockRecordDTO stockRecord, @PathVariable String symbol) {
		log.debug("replaceStock {} by {}", symbol, stockRecord);
		StockRecord existingStock = customStockRecordRepository.findBySymbol(symbol);
		if (existingStock != null) {
			log.debug("Delete stock {}", existingStock);
			customStockRecordRepository.delete(existingStock);
		}
		return convert(customStockRecordRepository.save(from(stockRecord)));
	}

	@DeleteMapping("/stocks/{symbol}")
	public void deleteStock(@PathVariable String symbol) {
		log.debug("Delete stock with {} symbol", symbol);
		StockRecord stockToDelete = customStockRecordRepository.findBySymbol(symbol);
		customStockRecordRepository.delete(stockToDelete);
	}

}
