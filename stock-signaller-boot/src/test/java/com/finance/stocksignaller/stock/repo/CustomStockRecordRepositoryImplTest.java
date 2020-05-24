package com.finance.stocksignaller.stock.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.finance.stocksignaller.stock.model.StockRecord;

@RunWith(MockitoJUnitRunner.class)
public class CustomStockRecordRepositoryImplTest {

	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private QueryRepository queryRepository;

	@InjectMocks
	private CustomStockRecordRepositoryImpl createUpdateStockRepository;

	@Before
	public void init() {
		when(mongoTemplate.save(any(StockRecord.class))).thenAnswer((InvocationOnMock invocation) -> {
			Object[] args = invocation.getArguments();
			return (StockRecord) args[0];
		});
	}

	@Test
	public void givenAnyRecord_whenSave_thenStoreToRepository() throws DuplicateStockException {
		StockRecord stockRecord = new StockRecord("ANY", "Any");
		StockRecord saveRecord = createUpdateStockRepository.save(stockRecord);

		verify(queryRepository, VerificationModeFactory.atMostOnce()).findBySymbol(stockRecord.getSymbol());
		assertNotNull(saveRecord);
	}

	@Test(expected = DuplicateStockException.class)
	public void givenAnExistingRecord_whenSave_thenThrowException() throws DuplicateStockException {
		StockRecord stockRecord = new StockRecord("EXI", "Existing");
		// Given an existing
		when(queryRepository.exists(any(StockRecord.class))).thenReturn(true);

		StockRecord saveRecord = createUpdateStockRepository.save(stockRecord);

		verify(queryRepository, VerificationModeFactory.atMostOnce()).findBySymbol(stockRecord.getSymbol());
		assertNull(saveRecord);
	}

	@Test
	public void givenAnyCollectionOfRecords_whenSave_thenStoreThemAllToRepository() throws DuplicateStockException {
		List<StockRecord> records = Arrays.asList(new StockRecord("ONE", "one"), new StockRecord("TWO", "two"));
		Iterable<StockRecord> savedRecords = createUpdateStockRepository.saveAll(records);

		verify(queryRepository, VerificationModeFactory.times(2)).exists(any());
		assertNotNull(savedRecords);
		List<StockRecord> savedStockRecordList = StreamSupport.stream(savedRecords.spliterator(), false)
				.collect(Collectors.toList());
		assertEquals(records, savedStockRecordList);
	}

	@Test(expected = DuplicateStockException.class)
	public void givenAnyCollectionOfRecord_andContainsOneExisting_whenSave_thenThrowException()
			throws DuplicateStockException {
		final List<StockRecord> records = Arrays.asList(new StockRecord("ONE", "one"), new StockRecord("TWO", "two"));
		// Given an existing
		when(queryRepository.exists(any(StockRecord.class))).thenReturn(true);

		Iterable<StockRecord> saveRecords = createUpdateStockRepository.saveAll(records);

		verify(queryRepository, VerificationModeFactory.times(1)).findBySymbol(any());
		assertNull(saveRecords);
	}

}
