package com.blocadmin.core.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.context.annotation.Scope;
import com.blocadmin.core.dto.DocumentDTO;
import com.blocadmin.core.service.DocService;

@Named
@Scope("view")
public class DocsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(DocsBean.class);
	
	@Inject
	private DocService docService;
	
	private List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
	private List<DocumentDTO> selectedDocs = new ArrayList<DocumentDTO>();
	private DocumentDTO selectedDocument = new DocumentDTO();
	private UploadedFile uploadedFile;
	private StreamedContent streamedFile;
	
	public List<DocumentDTO> getDocuments() {
		return documents;
	}
	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}
	public List<DocumentDTO> getSelectedDocs() {
		return selectedDocs;
	}
	public void setSelectedDocs(List<DocumentDTO> selectedDocs) {
		this.selectedDocs = selectedDocs;
	}
	public DocumentDTO getSelectedDocument() {
		return selectedDocument;
	}
	public void setSelectedDocument(DocumentDTO selectedDocument) {
		this.selectedDocument = selectedDocument;
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public StreamedContent getStreamedFile() {
		return streamedFile;
	}

	public void setStreamedFile(StreamedContent streamedFile) {
		this.streamedFile = streamedFile;
	}

	public void prepareToDownload(DocumentDTO fileEntity) {
		InputStream stream = new ByteArrayInputStream(fileEntity.getDoc());
		this.streamedFile = DefaultStreamedContent.builder().contentType(fileEntity.getContentType())
				.name(fileEntity.getName()).stream(() -> stream).build();
	}
	
	public void setup() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			this.documents = docService.getDocs();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the docs: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing this page."));
		}
	}

	public String deleteDoc(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			docService.deleteDoc(id);
			facesContext.addMessage(null, new FacesMessage("Document Removed!"));
			PrimeFaces.current().ajax().update("docForm:messages", "docForm:dt-docs");
			return "/gui/docs/viewDocs.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the document: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the document."));
			return null; // will reuse current view
		}
	}

	public String saveDoc() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			selectedDocument.setName(uploadedFile.getFileName());
			selectedDocument.setContentType(uploadedFile.getContentType());
			selectedDocument.setDoc(uploadedFile.getContent());
			docService.saveDoc(selectedDocument);
			facesContext.addMessage(null, new FacesMessage("Doc information added."));
			PrimeFaces.current().executeScript("PF('manageDocDialog').hide()");
			PrimeFaces.current().ajax().update("docForm:messages", "docForm:dt-docs");
			return "/gui/docs/viewDocs.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the document: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the document."));
			return null; // will reuse current view
		}
	}

	public void openNew() {
		this.selectedDocument = new DocumentDTO();
	}

	public String init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			setup();
		} catch (Exception ex) {
			LOGGER.error("Exception initializing the bean: " + ex.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing this page."));
		}
		return null;
	}
}
