package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.Document;


@Named
public class DocDAOImpl extends CommonDAOImpl<Document> implements DocDAO{

	@Override
	public List<Document> getDocs() {
		TypedQuery<Document> tq = em.createQuery("SELECT DISTINCT d FROM Document d", Document.class);
		List<Document> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Document>();
		}
		return results;
	}
}
