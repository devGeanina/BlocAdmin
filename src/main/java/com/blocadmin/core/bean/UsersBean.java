package com.blocadmin.core.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
import com.blocadmin.core.dto.UserDTO;
import com.blocadmin.core.service.UserService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@Named
@Scope("view")
public class UsersBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(UsersBean.class);

	@Inject
	private UserService userService;

	private List<UserDTO> users;
	private List<UserDTO> selectedUsers;
	private UserDTO selectedUser;
	
	private PDFOptions pdfOpt;

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<UserDTO> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<UserDTO> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public UserDTO getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDTO selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void setup() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			this.users = userService.getUsers();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the users: " + e.getMessage());
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

	public String deleteUser(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			userService.deleteUser(id);
			facesContext.addMessage(null, new FacesMessage("User Removed!"));
			PrimeFaces.current().ajax().update("userForm:messages", "userForm:dt-users");
			return "/gui/users/viewUsers.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the user: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the user."));
			return null; // will reuse current view
		}
	}

	public String saveUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			userService.saveUser(selectedUser);
			facesContext.addMessage(null, new FacesMessage("User information added."));
			PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
			PrimeFaces.current().ajax().update("userForm:messages", "userForm:dt-users");
			return "/band/users/viewUsers.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the user: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the user."));
			return null; // will reuse current view
		}
	}

	public void openNew() {
		this.selectedUser = new UserDTO();
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
