package com.blocadmin.core.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExpenseDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String expenseType;
    private double totalSum;
    private double leftoverSum;
    private boolean payedInFull;
    private String details;
    private Date dueDate;
    private List<Long> householdIds;
    private List<String> householdsAddresses;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public double getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}
	public double getLeftoverSum() {
		return leftoverSum;
	}
	public void setLeftoverSum(double leftoverSum) {
		this.leftoverSum = leftoverSum;
	}
	public boolean isPayedInFull() {
		return payedInFull;
	}
	public void setPayedInFull(boolean payedInFull) {
		this.payedInFull = payedInFull;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public List<Long> getHouseholdIds() {
		return householdIds;
	}
	public void setHouseholdIds(List<Long> householdIds) {
		this.householdIds = householdIds;
	}
	public List<String> getHouseholdsAddresses() {
		return householdsAddresses;
	}
	public void setHouseholdsAddresses(List<String> householdsAddresses) {
		this.householdsAddresses = householdsAddresses;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((expenseType == null) ? 0 : expenseType.hashCode());
		result = prime * result + ((householdIds == null) ? 0 : householdIds.hashCode());
		result = prime * result + ((householdsAddresses == null) ? 0 : householdsAddresses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(leftoverSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (payedInFull ? 1231 : 1237);
		temp = Double.doubleToLongBits(totalSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseDTO other = (ExpenseDTO) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (expenseType == null) {
			if (other.expenseType != null)
				return false;
		} else if (!expenseType.equals(other.expenseType))
			return false;
		if (householdIds == null) {
			if (other.householdIds != null)
				return false;
		} else if (!householdIds.equals(other.householdIds))
			return false;
		if (householdsAddresses == null) {
			if (other.householdsAddresses != null)
				return false;
		} else if (!householdsAddresses.equals(other.householdsAddresses))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(leftoverSum) != Double.doubleToLongBits(other.leftoverSum))
			return false;
		if (payedInFull != other.payedInFull)
			return false;
		if (Double.doubleToLongBits(totalSum) != Double.doubleToLongBits(other.totalSum))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ExpenseDTO [id=" + id + ", expenseType=" + expenseType + ", totalSum=" + totalSum + ", leftoverSum="
				+ leftoverSum + ", payedInFull=" + payedInFull + ", details=" + details + ", dueDate=" + dueDate
				+ ", householdIds=" + householdIds + ", householdsAddresses=" + householdsAddresses + "]";
	}
}
