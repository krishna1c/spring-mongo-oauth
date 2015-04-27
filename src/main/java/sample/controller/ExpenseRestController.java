package sample.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.domain.Expense;
import sample.domain.ExpenseEntry;
import sample.domain.ExpenseSummary;
import sample.domain.util.DateUtil;
import sample.exception.MyResourceNotFoundException;
import sample.exception.RestPreconditions;
import sample.repository.ExpenseRepository;
import sample.service.CounterService;
import sample.service.ExpenseService;

/**
 * Expense Rest Controller
 * @author pmincz
 *
 */
@RestController
@RequestMapping("/users/{userId}/expenses")
public class ExpenseRestController {

	@Autowired
	private ExpenseRepository repo;
	@Autowired
	private CounterService counter;
	@Autowired
	private ExpenseService service;

	/**
	 * Get all the expense by userId
	 * @param userId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public List<Expense> getAll(@PathVariable Long userId) {
		return repo.findByUserId(userId);
	}
	
	/**
	 * Get expense by userId and expenseId
	 * @param userId
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public Expense getExpense(@PathVariable Long userId, @PathVariable Long id) {
		Expense expense = RestPreconditions.checkFound(repo.findByExpenseId(id));
		if (expense.getUserId() != userId) {
			throw new MyResourceNotFoundException("The expense does not belong to the user "+userId);
		}
		return expense;
	}

	/**
	 * Create expense based on userId and expense entry
	 * @param userId
	 * @param entry
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Expense create(@AuthenticationPrincipal @PathVariable Long userId, @RequestBody ExpenseEntry entry) {
		Expense expense;
		try {
			expense = new Expense(entry);
		} catch (ParseException e) {
			throw new MyResourceNotFoundException("The date format must be yyyy/MM/dd");
		}
		expense.setUserId(userId);
		expense.setExpenseId(counter.getNextExpenseIdSequence());
		return repo.save(expense);
	}

	/**
	 * Delete expense by userId and expenseId
	 * @param id
	 * @param userId
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public void delete(@AuthenticationPrincipal @PathVariable Long id, @PathVariable Long userId) {
		Expense expense = RestPreconditions.checkFound(repo.findByExpenseId(id));
		if (expense.getUserId() == userId) {
			repo.delete(expense.getId());
		} else {
			throw new MyResourceNotFoundException("The expense does not belong to the user "+userId);
		}
	}

	/**
	 * Update expense by userId and entry
	 * @param id
	 * @param userId
	 * @param entry
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public Expense update(@AuthenticationPrincipal @PathVariable Long id, @PathVariable Long userId, @RequestBody ExpenseEntry entry) {
		Expense update = RestPreconditions.checkFound(repo.findByExpenseId(id));
		if (userId == update.getUserId()) {
			update.setDescription(entry.getDescription());
			update.setAmount(entry.getAmount());
			try {
				update.setDate(DateUtil.getDate(entry.getDate()));
			} catch (ParseException e) {
				throw new MyResourceNotFoundException("The date format must be yyyy/MM/dd");
			}
			update.setUserId(userId);
			return repo.save(update);
		} else {
			throw new MyResourceNotFoundException("The expense does not belong to the user "+userId);
		}
	}
	
	/**
	 * Get expense summary by userId
	 * @param userId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/summary")
	public List<ExpenseSummary> getSummary(@PathVariable Long userId) {
		return service.getUserSummary(userId);
	}
}
