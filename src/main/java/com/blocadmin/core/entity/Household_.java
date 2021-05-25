package com.blocadmin.core.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.951+0300")
@StaticMetamodel(Household.class)
public class Household_ extends CommonEntity_ {
	public static volatile SingularAttribute<Household, Integer> buildingNr;
	public static volatile SingularAttribute<Household, Integer> appartmentNr;
	public static volatile SingularAttribute<Household, String> details;
	public static volatile SingularAttribute<Household, Integer> roomsNr;
	public static volatile SingularAttribute<Household, Integer> nrCurrentOccupants;
	public static volatile SingularAttribute<Household, Integer> totalCapacity;
	public static volatile SingularAttribute<Household, User> owner;
	public static volatile ListAttribute<Household, Expense> expenses;
}
