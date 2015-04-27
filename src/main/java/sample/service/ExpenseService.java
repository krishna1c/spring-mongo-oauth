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

/**
 * Expense service
 * @author pmincz
 *
 */
@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository repo;

	/**
	 * Get user summary by userId
	 * @param userId
	 * @return
	 */
	public List<ExpenseSummary> getUserSummary(Long userId) {
		List<Expense> expenses = repo.findByUserId(userId, new Sort(Sort.Direction.ASC, "date"));
		return getSummary(expenses);
	}
	
	/**
	 * Get summary without filtering by user
	 * @return
	 */
	public List<ExpenseSummary> getSummary() {
		List<Expense> expenses = repo.findAll(new Sort(Sort.Direction.ASC, "date"));
		return getSummary(expenses);
	}
	
	/**
	 * Get the summary based in a collection of expenses
	 * If the expense corresponds to the same week count as the same summary
	 * @param expenses
	 * @return
	 */
	public List<ExpenseSummary> getSummary(List<Expense> expenses) {
		List<ExpenseSummary> summaries = new ArrayList<ExpenseSummary>();

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
	
	/**
	 * Is the same week?
	 * @param calendar
	 * @param targetCalendar
	 * @return
	 */
	public Boolean isSameWeek(Calendar calendar, Calendar targetCalendar) {
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);

		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);
		return (targetWeek == week && year == targetYear);
	}

	/**
	 * Update the summary with the expense
	 * @param summary
	 * @param expense
	 */
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

	/**
	 * Set average on the summary
	 * @param summary
	 */
	public void setAverage(ExpenseSummary summary) {
		BigDecimal result = summary.getTotal().divide(new BigDecimal(summary.getCount()));
		summary.setAverage(result);
	}
	
	/**
	 * Set average and add the summary to the final list
	 * @param summary
	 * @param summaries
	 * @return
	 */
	public ExpenseSummary initAndSaveSummary(ExpenseSummary summary, List<ExpenseSummary> summaries) {
		setAverage(summary);
		summaries.add(summary);
		return new ExpenseSummary();
	}

}
