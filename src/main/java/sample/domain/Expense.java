package sample.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "collection")
public class Expense extends BaseEntity {
	
	@Indexed
	private Long expenseId;
	private String desciption;
	private BigDecimal amount;
	private Date date;
	@JsonIgnore
	private Long userId;
	
	public Long getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
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
}
