package sample.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
	public List<Expense> findByUserId(Long userId);
	public Expense findByExpenseId(Long expenseId);
}
