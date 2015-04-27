package sample.domain;

import java.math.BigDecimal;

/**
 * Expense entry for the rest services
 * @author pmincz
 *
 */
public class ExpenseEntry {

	private String description;
	private BigDecimal amount;
	private String date;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
