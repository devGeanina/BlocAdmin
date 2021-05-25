package com.blocadmin.core.dao;

import java.util.List;
import com.blocadmin.core.entity.Document;

public interface DocDAO extends CommonDAO<Document>{
	public abstract List<Document> getDocs();
}
