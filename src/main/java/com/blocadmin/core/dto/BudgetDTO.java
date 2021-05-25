package com.blocadmin.core.dto;

import java.io.Serializable;
import java.util.Objects;


public class BudgetDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String type;
    private double totalSum;
    private double leftoverSum;
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.type);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.totalSum) ^ (Double.doubleToLongBits(this.totalSum) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.leftoverSum) ^ (Double.doubleToLongBits(this.leftoverSum) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.details);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BudgetDTO other = (BudgetDTO) obj;
        if (Double.doubleToLongBits(this.totalSum) != Double.doubleToLongBits(other.totalSum)) {
            return false;
        }
        if (Double.doubleToLongBits(this.leftoverSum) != Double.doubleToLongBits(other.leftoverSum)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Budget{" + "id=" + id + ", type=" + type + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum + ", details=" + details + '}';
    }
}
