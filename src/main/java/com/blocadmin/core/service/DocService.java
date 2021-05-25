package com.blocadmin.core.service;

import java.util.List;
import com.blocadmin.core.dto.DocumentDTO;

public interface DocService {
	
	public abstract List<DocumentDTO> getDocs();

	public abstract void saveDoc(DocumentDTO docDTO);

	public abstract void deleteDoc(Long id);

	public abstract DocumentDTO getDoc(Long id);
}
