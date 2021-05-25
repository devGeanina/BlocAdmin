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
import com.blocadmin.core.dto.HouseholdDTO;
import com.blocadmin.core.dto.UserDTO;
import com.blocadmin.core.entity.User;
import com.blocadmin.core.service.HouseholdService;
import com.blocadmin.core.service.UserService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@Named
@Scope("view")
public class HouseholdsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(HouseholdsBean.class);
	
	@Inject
	private HouseholdService householdService;
	
	@Inject
	private UserService userService;
	
	private List<HouseholdDTO> households = new ArrayList<HouseholdDTO>();
	private List<HouseholdDTO> selectedHouseholds = new ArrayList<HouseholdDTO>();
	private HouseholdDTO selectedHousehold;
	private List<UserDTO> users;
	private String summary;
	
	private PDFOptions pdfOpt;
	
	public List<HouseholdDTO> getHouseholds() {
		return households;
	}
	public void setHouseholds(List<HouseholdDTO> households) {
		this.households = households;
	}
	public List<HouseholdDTO> getSelectedHouseholds() {
		return selectedHouseholds;
	}
	public void setSelectedHouseholds(List<HouseholdDTO> selectedHouseholds) {
		this.selectedHouseholds = selectedHouseholds;
	}
	public HouseholdDTO getSelectedHousehold() {
		return selectedHousehold;
	}
	public void setSelectedHousehold(HouseholdDTO selectedHousehold) {
		this.selectedHousehold = selectedHousehold;
	}
	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
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
			this.households = householdService.getHouseholds();
			this.users = userService.getUsers();
			selectedHousehold = new HouseholdDTO();
			selectedHousehold.setSelectedOwner(new User());
			createSummary();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the households: " + e.getMessage());
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

	public String deleteHousehold(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			householdService.deleteHousehold(id);
			facesContext.addMessage(null, new FacesMessage("Household Removed!"));
			PrimeFaces.current().ajax().update("householdForm:messages", "householdForm:dt-households");
			return "/gui/households/viewHouseholds.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the household: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the household."));
			return null; // will reuse current view
		}
	}

	public String saveHousehold() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			householdService.saveHousehold(selectedHousehold);
			facesContext.addMessage(null, new FacesMessage("Household information added."));
			PrimeFaces.current().executeScript("PF('manageHouseholdDialog').hide()");
			PrimeFaces.current().ajax().update("householdForm:messages", "householdForm:dt-households");
			return "/gui/households/viewHouseholds.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the household: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the household."));
			return null; // will reuse current view
		}
	}

	public void openNew() {
		this.selectedHousehold = new HouseholdDTO();
		selectedHousehold.setSelectedOwner(new User());
	}
	
	public void createSummary() {
		StringBuilder builder = new StringBuilder();
		if(households != null && !households.isEmpty()) {
			List<HouseholdDTO> householdsWithDebts = households.stream().filter(o -> o.getTotalDebt() > 0.0).collect(Collectors.toList());
			double totalDebt = households.stream().mapToDouble(o -> o.getTotalDebt()).sum();
			builder.append("There are a total of ").append(households.size()).append(" households, with a total debt of ").append(totalDebt).append(" from ").append(householdsWithDebts.size()).append(".");
			if(householdsWithDebts != null && !householdsWithDebts.isEmpty()) {
				builder.append(" Households with debts: ");
				List<String> householdsAddresses= new ArrayList<String>();
				for(HouseholdDTO householdDTO: householdsWithDebts) {
					householdsAddresses.add(" B: ".concat(String.valueOf(householdDTO.getBuildingNr())).concat(", Ap: ").concat(String.valueOf(householdDTO.getAppartmentNr())).concat(" Owner: ").concat(householdDTO.getOwnerName()));
				}
				String householdAddressesFormatted = householdsAddresses.stream().map(Object::toString).collect(Collectors.joining("/"));
				builder.append(householdAddressesFormatted);
				builder.append(".");
			}
		}else {
			builder.append("No information on the households found yet.");
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
