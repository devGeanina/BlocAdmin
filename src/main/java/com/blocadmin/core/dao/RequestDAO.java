package com.blocadmin.core.dao;

import java.util.List;
import com.blocadmin.core.entity.Request;

public interface RequestDAO extends CommonDAO<Request>{
	public abstract List<Request> getRequests();
}
