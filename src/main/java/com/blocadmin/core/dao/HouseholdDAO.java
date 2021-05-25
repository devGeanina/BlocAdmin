package com.blocadmin.core.dao;

import java.util.List;
import com.blocadmin.core.entity.Household;

public interface HouseholdDAO extends CommonDAO<Household>{
	public abstract List<Household> getHouseholds();

	public abstract List<Household> getHouseholdsWithDebt();
}
