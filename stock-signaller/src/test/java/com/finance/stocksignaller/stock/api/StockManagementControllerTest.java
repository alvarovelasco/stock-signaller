package com.finance.stocksignaller.stock.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.finance.stocksignaller.stock.model.StockRecord;
import com.finance.stocksignaller.stock.repo.CustomStockRecordRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(StockManagementController.class)
public class StockManagementControllerTest {

	@MockBean
	private CustomStockRecordRepository customStockRecordRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void findAll() throws Exception {
		List<StockRecord> records = Arrays.asList(new StockRecord("ONE", "one"));

		when(customStockRecordRepository.findAll()).thenReturn(records);

		mvc.perform(get("/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].symbol", is(records.get(0).getSymbol())));
	}

	@Test
	public void saveNew() throws Exception {
		StockRecord record = new StockRecord("ONE", "one");

		when(customStockRecordRepository.save(any())).thenAnswer(i -> {
			Object[] args = i.getArguments();
			return (StockRecord) args[0];
		});

		mvc.perform(post("/stocks").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content("{\"symbol\":\"ONE\",\"name\":\"one\"}")).andExpect(status().isOk());

		verify(customStockRecordRepository, VerificationModeFactory.times(1)).save(eq(record));
	}

	@Test
	public void givenNewStock_thenReplace() throws Exception {
		when(customStockRecordRepository.save(any())).thenAnswer(i -> {
			Object[] args = i.getArguments();
			return (StockRecord) args[0];

		});

		mvc.perform(put("/stocks/ONE").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content("{\"symbol\":\"ONE\",\"name\":\"two\"}")).andExpect(status().isOk());

		verify(customStockRecordRepository, VerificationModeFactory.times(1)).findBySymbol(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(0)).delete(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(1)).save(any());
	}

	@Test
	public void givenExistingStock_thenDeleteAndInsert() throws Exception {
		StockRecord stockRecord = new StockRecord("ONE", "one");

		when(customStockRecordRepository.findBySymbol(eq(stockRecord.getSymbol()))).thenReturn(stockRecord);
		when(customStockRecordRepository.save(any())).thenAnswer(i -> {
			Object[] args = i.getArguments();
			return (StockRecord) args[0];

		});

		mvc.perform(put("/stocks/ONE").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content("{\"symbol\":\"ONE\",\"name\":\"two\"}")).andExpect(status().isOk());

		verify(customStockRecordRepository, VerificationModeFactory.times(1)).findBySymbol(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(1)).delete(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(1)).save(any());
	}

	@Test
	public void givenExistingStock_thenDelete() throws Exception {
		StockRecord stockRecord = new StockRecord("ONE", "one");

		when(customStockRecordRepository.findBySymbol(eq(stockRecord.getSymbol()))).thenReturn(stockRecord);

		mvc.perform(delete("/stocks/ONE").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		verify(customStockRecordRepository, VerificationModeFactory.times(1)).findBySymbol(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(1)).delete(any());
		verify(customStockRecordRepository, VerificationModeFactory.times(0)).save(any());
	}

}
