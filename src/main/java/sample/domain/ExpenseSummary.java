package sample.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ExpenseSummary {

	private BigDecimal total;
	private BigDecimal average;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date begin;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date end;
	@JsonIgnore
	private Integer count;
	
	public ExpenseSummary() {
		this.total = new BigDecimal(0.0);
		this.average = new BigDecimal(0.0);
		this.count = 0;
		this.begin = null;
		this.end = null;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getAverage() {
		return average;
	}
	public void setAverage(BigDecimal average) {
		this.average = average;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
