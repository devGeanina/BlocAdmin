package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.Expense;


@Named
public class ExpenseDAOImpl extends CommonDAOImpl<Expense> implements ExpenseDAO{

	@Override
	public List<Expense> getExpenses() {
		TypedQuery<Expense> tq = em.createQuery("SELECT DISTINCT e FROM Expense e", Expense.class);
		List<Expense> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Expense>();
		}
		return results;
	}
	
	@Override
	public List<Expense> getExpensesForHousehold(Long householdId) {
		TypedQuery<Expense> tq = em.createQuery("SELECT DISTINCT e FROM Expense e LEFT JOIN e.householdExpenses h WHERE h.id=:householdId", Expense.class);
		tq.setParameter("householdId", householdId);
		List<Expense> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Expense>();
		}
		return results;
	}
}
