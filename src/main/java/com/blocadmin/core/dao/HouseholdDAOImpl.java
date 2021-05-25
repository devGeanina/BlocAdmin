package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.Household;

@Named
public class HouseholdDAOImpl extends CommonDAOImpl<Household> implements HouseholdDAO{

	@Override
	public List<Household> getHouseholds() {
		TypedQuery<Household> tq = em.createQuery("SELECT DISTINCT h FROM Household h LEFT JOIN FETCH h.owner owner", Household.class);
		List<Household> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Household>();
		}
		return results;
	}
	
	@Override
	public List<Household> getHouseholdsWithDebt() {
		TypedQuery<Household> tq = em.createQuery("SELECT DISTINCT h FROM Household h LEFT JOIN FETCH h.owner owner LEFT JOIN h.expenses expense", Household.class);
		List<Household> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Household>();
		}
		return results;
	}
}
