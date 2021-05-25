package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import com.blocadmin.core.dao.DocDAO;
import com.blocadmin.core.dto.DocumentDTO;
import com.blocadmin.core.entity.Document;

@Named
public class DocServiceImpl implements DocService{
	
	@Inject
	private DocDAO docDAO;

	@Override
	public List<DocumentDTO> getDocs() {
		List<DocumentDTO> dtos = new ArrayList<>();

		List<Document> entities = docDAO.getDocs();
		for (Document entity : entities) {
			DocumentDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private DocumentDTO getDTO(Document entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		DocumentDTO entityDTO = new DocumentDTO();
		entityDTO.setName(entity.getName());
		entityDTO.setDoc(entity.getDoc());
		entityDTO.setId(entity.getId());
		entityDTO.setContentType(entity.getContentType());
		return entityDTO;
	}

	@Override
	public void saveDoc(DocumentDTO docDTO) {
		if (docDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Document doc = new Document();
		doc = getEntity(docDTO);
		docDAO.create(doc);
	}

	private Document getEntity(DocumentDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Document entity = new Document();
		entity.setName(entityDTO.getName());
		entity.setDoc(entityDTO.getDoc());
		entity.setContentType(entityDTO.getContentType());

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteDoc(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		docDAO.delete(id);
	}

	@Override
	public DocumentDTO getDoc(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Document entity = docDAO.find(id);
		DocumentDTO entityDTO = getDTO(entity);
		return entityDTO;
	}
}
