package sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sample.controller.ExpenseRestController;
import sample.domain.Expense;
import sample.domain.ExpenseEntry;
import sample.domain.User;
import sample.repository.ExpenseRepository;
import sample.repository.UserRepository;
import sample.util.AbstractRestTest;
import sample.util.IntegrationTestUtil;

public class ExpensesTest extends AbstractRestTest {
	
	@InjectMocks
	ExpenseRestController controller;
	
	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void getExpenses() throws Exception {
		User user = userRepo.findAll().get(0);
		
		// @formatter:off
		mvc.perform(get("/users/"+user.getUserId()+"/expenses")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// @formatter:on
	}
	
	@Test
	public void getExpense() throws Exception {
		Expense expense = expenseRepo.findAll().get(0);
		
		// @formatter:off
		mvc.perform(get("/users/"+expense.getUserId()+"/expenses/"+expense.getExpenseId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// @formatter:on
	}
	
	@Test
	public void postExpense() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		
		User user = userRepo.findAll().get(0);
		
		ExpenseEntry expenseEntry = new ExpenseEntry();
		expenseEntry.setAmount(new BigDecimal(123.56));
		expenseEntry.setDate("2015/02/02");
		expenseEntry.setDescription("new expense");
		
		mvc.perform(
						post("/users/"+user.getUserId()+"/expenses").header("Authorization", "Bearer " + accessToken)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(IntegrationTestUtil.convertObjectToJsonBytes(expenseEntry)))
								.andExpect(status().isOk());
	}
	
	@Test
	public void deleteExpense() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		postExpense();
		Expense expense = expenseRepo.findAll().get(0);
		
		mvc.perform(MockMvcRequestBuilders.delete("/users/"+expense.getUserId()+"/expenses/"+expense.getExpenseId()).header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateExpense() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		postExpense();
		Expense expense = expenseRepo.findAll().get(0);
		
		ExpenseEntry expenseEntry = new ExpenseEntry();
		expenseEntry.setAmount(new BigDecimal(654.65));
		expenseEntry.setDescription("expense updated");
		expenseEntry.setDate("2015/01/03");
		
		mvc.perform(
				MockMvcRequestBuilders.put("/users/"+expense.getUserId()+"/expenses/"+expense.getExpenseId()).header("Authorization", "Bearer " + accessToken)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(IntegrationTestUtil.convertObjectToJsonBytes(expenseEntry)))
								.andExpect(status().isOk());
	}
	
	@Test
	public void getUserSummary() throws Exception {
		User user = userRepo.findAll().get(0);
		
		// @formatter:off
		mvc.perform(get("/users/"+user.getUserId()+"/expenses/summary")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// @formatter:on
	}
	

}
