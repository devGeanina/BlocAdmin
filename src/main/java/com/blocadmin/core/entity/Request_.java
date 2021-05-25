package com.blocadmin.core.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.962+0300")
@StaticMetamodel(Request.class)
public class Request_ extends CommonEntity_ {
	public static volatile SingularAttribute<Request, Short> requestType;
	public static volatile SingularAttribute<Request, String> name;
	public static volatile SingularAttribute<Request, User> owner;
	public static volatile SingularAttribute<Request, String> details;
	public static volatile SingularAttribute<Request, Boolean> isResolved;
	public static volatile SingularAttribute<Request, Date> dueDate;
}
