package sample.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import sample.domain.util.DateUtil;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Expense entity
 * @author pmincz
 *
 */
@Document(collection = "expense")
public class Expense extends BaseEntity {
	
	@Indexed
	private Long expenseId;
	private String description;
	private BigDecimal amount;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date date;
	@JsonIgnore
	private Long userId;
	
	public Expense() {}
	
	public Expense(String id, Long expenseId, BigDecimal amount, Date date, Long userId) {
		this.setId(id);
		this.expenseId = expenseId;
		this.amount = amount;
		this.date = date;
		this.userId = userId;
	}
	
	public Expense(ExpenseEntry entry) throws ParseException {
		this.description = entry.getDescription();
		this.amount = entry.getAmount();
		this.date = DateUtil.getDate(entry.getDate());
	}
	
	public Long getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
