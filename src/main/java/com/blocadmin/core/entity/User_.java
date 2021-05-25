package com.blocadmin.core.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.979+0300")
@StaticMetamodel(User.class)
public class User_ extends CommonEntity_ {
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, Short> userType;
	public static volatile SingularAttribute<User, Integer> buildingNr;
	public static volatile SingularAttribute<User, String> details;
	public static volatile SingularAttribute<User, Integer> appartmentNr;
	public static volatile ListAttribute<User, Household> households;
	public static volatile ListAttribute<User, Request> requests;
}
