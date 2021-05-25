package com.blocadmin.core.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.902+0300")
@StaticMetamodel(Expense.class)
public class Expense_ extends CommonEntity_ {
	public static volatile SingularAttribute<Expense, Short> expenseType;
	public static volatile SingularAttribute<Expense, Double> totalSum;
	public static volatile SingularAttribute<Expense, Double> leftoverSum;
	public static volatile SingularAttribute<Expense, Boolean> payedInFull;
	public static volatile SingularAttribute<Expense, String> details;
	public static volatile SingularAttribute<Expense, Date> dueDate;
	public static volatile ListAttribute<Expense, Household> householdExpenses;
}
