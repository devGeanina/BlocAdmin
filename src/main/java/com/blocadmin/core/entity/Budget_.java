package com.blocadmin.core.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.809+0300")
@StaticMetamodel(Budget.class)
public class Budget_ extends CommonEntity_ {
	public static volatile SingularAttribute<Budget, Short> type;
	public static volatile SingularAttribute<Budget, Double> totalSum;
	public static volatile SingularAttribute<Budget, Double> leftoverSum;
	public static volatile SingularAttribute<Budget, String> details;
}
