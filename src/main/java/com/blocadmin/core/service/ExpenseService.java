package com.blocadmin.core.service;

import java.util.List;
import com.blocadmin.core.dto.ExpenseDTO;

public interface ExpenseService {
	
	public abstract List<ExpenseDTO> getExpenses();

	public abstract void saveExpense(ExpenseDTO expenseDTO);

	public abstract void deleteExpense(Long id);

	public abstract ExpenseDTO getExpense(Long id);
}
