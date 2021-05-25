package com.blocadmin.core.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-25T14:17:07.871+0300")
@StaticMetamodel(Document.class)
public class Document_ extends CommonEntity_ {
	public static volatile SingularAttribute<Document, String> name;
	public static volatile SingularAttribute<Document, byte[]> doc;
	public static volatile SingularAttribute<Document, String> contentType;
}
