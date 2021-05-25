package com.blocadmin.core.dao;

import java.util.List;
import com.blocadmin.core.entity.Expense;

public interface ExpenseDAO extends CommonDAO<Expense>{
	public abstract List<Expense> getExpenses();

	public abstract List<Expense> getExpensesForHousehold(Long householdId);
}
