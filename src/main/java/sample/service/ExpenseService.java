package sample.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sample.domain.Expense;
import sample.domain.ExpenseSummary;
import sample.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository repo;

	public List<ExpenseSummary> getUserSummary(Long userId) {
		List<ExpenseSummary> summaries = new ArrayList<ExpenseSummary>();
		List<Expense> expenses = repo.findByUserId(userId, new Sort(Sort.Direction.ASC, "date"));

		Calendar calendar = Calendar.getInstance();
		Calendar targetCalendar = Calendar.getInstance();

		if (expenses.size() > 0) {
			calendar.setTime(expenses.get(0).getDate());
			ExpenseSummary summary = new ExpenseSummary();
			for (Expense expense : expenses) {
				targetCalendar.setTime(expense.getDate());

				if (!isSameWeek(calendar, targetCalendar)) {
					summary = initAndSaveSummary(summary, summaries);
					calendar.setTime(expense.getDate());
				}
				updateSummary(summary, expense);
			}
			initAndSaveSummary(summary, summaries);
		}

		return summaries;
	}
	
	public List<ExpenseSummary> getSummaries() {
		List<ExpenseSummary> summaries = new ArrayList<ExpenseSummary>();
		List<Expense> expenses = repo.findAll(new Sort(Sort.Direction.ASC, "date"));

		Calendar calendar = Calendar.getInstance();
		Calendar targetCalendar = Calendar.getInstance();

		if (expenses.size() > 0) {
			calendar.setTime(expenses.get(0).getDate());
			ExpenseSummary summary = new ExpenseSummary();
			for (Expense expense : expenses) {
				targetCalendar.setTime(expense.getDate());

				if (!isSameWeek(calendar, targetCalendar)) {
					summary = initAndSaveSummary(summary, summaries);
					calendar.setTime(expense.getDate());
				}
				updateSummary(summary, expense);
			}
			initAndSaveSummary(summary, summaries);
		}

		return summaries;
	}
	
	public Boolean isSameWeek(Calendar calendar, Calendar targetCalendar) {
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);

		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);
		return (targetWeek == week && year == targetYear);
	}

	public void updateSummary(ExpenseSummary summary, Expense expense) {
		//count++
		summary.setCount(summary.getCount()+1);
		//sum of amounts
		BigDecimal result = summary.getTotal().add(expense.getAmount());
		summary.setTotal(result);
		//dates
		if (summary.getBegin() == null) summary.setBegin(expense.getDate());
		summary.setEnd(expense.getDate());
	}

	public void setAverage(ExpenseSummary summary) {
		BigDecimal result = summary.getTotal().divide(new BigDecimal(summary.getCount()));
		summary.setAverage(result);
	}
	
	public ExpenseSummary initAndSaveSummary(ExpenseSummary summary, List<ExpenseSummary> summaries) {
		setAverage(summary);
		summaries.add(summary);
		return new ExpenseSummary();
	}

}
