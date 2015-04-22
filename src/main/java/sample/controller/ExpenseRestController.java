package sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.domain.Expense;
import sample.exception.RestPreconditions;
import sample.repository.ExpenseRepository;
import sample.service.CounterService;

@RestController
@RequestMapping("/users/{userId}/expenses")
public class ExpenseRestController {

	@Autowired
	private ExpenseRepository repo;
	@Autowired
	private CounterService counter;

	@RequestMapping(method=RequestMethod.GET)
	public List<Expense> getAll(@PathVariable Long userId) {
		return repo.findByUserId(userId);
	}

	@RequestMapping(method=RequestMethod.POST)
	public Expense create(@PathVariable Long userId, @RequestBody Expense expense) {
		expense.setUserId(userId);
		expense.setExpenseId(counter.getNextExpenseIdSequence());
		return repo.save(expense);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public void delete(@AuthenticationPrincipal @PathVariable Long id, @PathVariable Long userId) {
		Expense expense = RestPreconditions.checkFound(repo.findByExpenseId(id));
		if (expense.getUserId() == userId) {
			repo.delete(expense.getId());
		}
	}

	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public Expense update(@PathVariable Long id, @PathVariable Long userId, @RequestBody Expense expense) {
		Expense update = RestPreconditions.checkFound(repo.findByExpenseId(id));
		if (userId == expense.getUserId()) {
			update.setDesciption(expense.getDesciption());
			update.setAmount(expense.getAmount());
			update.setDate(expense.getDate());
			update.setUserId(userId);
			return repo.save(update);
		}
		return update;
	}
}
