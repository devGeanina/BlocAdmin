package com.blocadmin.core.dao;

import java.util.List;
import com.blocadmin.core.entity.Budget;

public interface BudgetDAO extends CommonDAO<Budget> {
	public abstract List<Budget> getBudgets();
}
