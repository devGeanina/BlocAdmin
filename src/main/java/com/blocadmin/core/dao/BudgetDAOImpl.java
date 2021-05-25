package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.Budget;

@Named
public class BudgetDAOImpl extends CommonDAOImpl<Budget> implements BudgetDAO{

	@Override
	public List<Budget> getBudgets() {
		TypedQuery<Budget> tq = em.createQuery("SELECT DISTINCT b FROM Budget b", Budget.class);
		List<Budget> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Budget>();
		}
		return results;
	}
}
