package com.blocadmin.core.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import org.springframework.context.annotation.Scope;
import com.blocadmin.core.dto.RequestDTO;
import com.blocadmin.core.dto.UserDTO;
import com.blocadmin.core.entity.User;
import com.blocadmin.core.service.RequestService;
import com.blocadmin.core.service.UserService;
import com.blocadmin.core.utils.Constants;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@Named
@Scope("view")
public class RequestsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(RequestsBean.class);
	
	@Inject
	private RequestService requestService;
	
	@Inject
	private UserService userService;
	
	private List<RequestDTO> requests = new ArrayList<RequestDTO>();
	private List<RequestDTO> selectedRequests = new ArrayList<RequestDTO>();
	private String defaultDateTimeFormat = Constants.DATE_TIME_FORMAT;
	private RequestDTO selectedRequest;
	private List<UserDTO> users;
	private String summary;
	
	private PDFOptions pdfOpt;
	
	public List<RequestDTO> getRequests() {
		return requests;
	}
	public void setRequests(List<RequestDTO> requests) {
		this.requests = requests;
	}
	public List<RequestDTO> getSelectedRequests() {
		return selectedRequests;
	}
	public void setSelectedRequests(List<RequestDTO> selectedRequests) {
		this.selectedRequests = selectedRequests;
	}
	public RequestDTO getSelectedRequest() {
		return selectedRequest;
	}
	public void setSelectedRequest(RequestDTO selectedRequest) {
		this.selectedRequest = selectedRequest;
	}
	
	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
	public String getDefaultDateTimeFormat() {
		return defaultDateTimeFormat;
	}
	public void setDefaultDateTimeFormat(String defaultDateTimeFormat) {
		this.defaultDateTimeFormat = defaultDateTimeFormat;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public void setup() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			this.requests = requestService.getRequests();
			this.users = userService.getUsers();
			this.selectedRequest = new RequestDTO();
			selectedRequest.setSelectedOwner(new User());
			createSummary();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the requests: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing this page."));
		}
		
		pdfOpt = new PDFOptions();
		pdfOpt.setFacetBgColor("#F88017");
		pdfOpt.setFacetFontColor("#0000ff");
		pdfOpt.setFacetFontStyle("BOLD");
		pdfOpt.setCellFontSize("12");
		pdfOpt.setFontName("Courier");
		pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
	}
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
	}

	public String deleteRequest(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			requestService.deleteRequest(id);
			facesContext.addMessage(null, new FacesMessage("Request Removed!"));
			PrimeFaces.current().ajax().update("requestForm:messages", "requestForm:dt-requests");
			return "/gui/requests/viewRequests.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the request: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the request."));
			return null; // will reuse current view
		}
	}

	public String saveRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			requestService.saveRequest(selectedRequest);
			facesContext.addMessage(null, new FacesMessage("Request information added."));
			PrimeFaces.current().executeScript("PF('manageRequestDialog').hide()");
			PrimeFaces.current().ajax().update("requestForm:messages", "requestForm:dt-requests");
			return "/gui/requests/viewRequests.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the request: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the request."));
			return null; // will reuse current view
		}
	}

	public void openNew() {
		this.selectedRequest = new RequestDTO();
		selectedRequest.setSelectedOwner(new User());
	}
	
	public void createSummary() {
		StringBuilder builder = new StringBuilder();
		if(requests != null && !requests.isEmpty()) {
			List<RequestDTO> unresolvedRequests = requests.stream().filter(o -> !o.isResolved()).collect(Collectors.toList());
			builder.append("There are a total of ").append(requests.size()).append(" requests, out of which ").append(unresolvedRequests.size()).append(" are unresolved.");
		}else {
			builder.append("No information on the requests found yet.");
		}
		this.summary = builder.toString();
	}

	/**
	 * Call PostConstruct to have the summary ready once the bean is constructed as well.
	 * @return
	 */
	@PostConstruct
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
