package com.blocadmin.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "budget_type", nullable = false)
    private short type;
	
	@Column(name = "total_sum", nullable = false)
    private double totalSum;
	
	@Column(name = "leftover_sum", nullable = false)
    private double leftoverSum;
	
	@Column(name = "details", nullable = true)
    private String details;

	public Budget() {}
	
	public Budget(short type, double totalSum, double leftoverSum, String details) {
		super();
		this.type = type;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.details = details;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Budget [type=" + type + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum + ", details="
				+ details + "]";
	}
}
