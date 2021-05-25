package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.Request;

@Named
public class RequestDAOImpl extends CommonDAOImpl<Request> implements RequestDAO{

	@Override
	public List<Request> getRequests() {
		TypedQuery<Request> tq = em.createQuery("SELECT DISTINCT r FROM Request r LEFT JOIN FETCH r.owner owner", Request.class);
		List<Request> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<Request>();
		}
		return results;
	}
}
