package com.blocadmin.core.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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
import com.blocadmin.core.dto.BudgetDTO;
import com.blocadmin.core.service.BudgetService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@Named
@Scope("view")
public class BudgetsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(BudgetsBean.class);
	
	@Inject
	private BudgetService budgetService;

	private List<BudgetDTO> budgets;
	private List<BudgetDTO> selectedBudgets;
	private BudgetDTO selectedBudget;
	private String summary;
	
	private PDFOptions pdfOpt;
	
	public List<BudgetDTO> getBudgets() {
		return budgets;
	}
	public void setBudgets(List<BudgetDTO> budgets) {
		this.budgets = budgets;
	}
	public List<BudgetDTO> getSelectedBudgets() {
		return selectedBudgets;
	}
	public void setSelectedBudgets(List<BudgetDTO> selectedBudgets) {
		this.selectedBudgets = selectedBudgets;
	}
	public BudgetDTO getSelectedBudget() {
		return selectedBudget;
	}
	public void setSelectedBudget(BudgetDTO selectedBudget) {
		this.selectedBudget = selectedBudget;
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
			this.budgets = budgetService.getBudgets();
			createSummary();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the budgets: " + e.getMessage());
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

	public String deleteBudget(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			budgetService.deleteBudget(id);
			facesContext.addMessage(null, new FacesMessage("Budget Removed!"));
			PrimeFaces.current().ajax().update("budgetForm:messages", "budgetForm:dt-budgets");
			return "/gui/budgets/viewBudgets.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the budget: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the budget."));
			return null; // will reuse current view
		}
	}

	public String saveBudget() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			budgetService.saveBudget(selectedBudget);
			facesContext.addMessage(null, new FacesMessage("Budget information added."));
			PrimeFaces.current().executeScript("PF('manageBudgetDialog').hide()");
			PrimeFaces.current().ajax().update("budgetForm:messages", "budgetForm:dt-budgets");
			return "/gui/budgets/viewBudgets.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the budget: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the budget."));
			return null; // will reuse current view
		}
	}

	public void openNew() {
		this.selectedBudget = new BudgetDTO();
	}
	
	public void createSummary() {
		StringBuilder builder = new StringBuilder();
		if(budgets != null && !budgets.isEmpty()) {
			double totalSum = budgets.stream().mapToDouble(o -> o.getTotalSum()).sum();
			double leftoverSum = budgets.stream().mapToDouble(o -> o.getLeftoverSum()).sum();
			builder.append("Leftover sum to spend from current budget is of ").append(leftoverSum).append(" from a total of ").append(totalSum).append(".");
		}else {
			builder.append("No information on the budgets found yet.");
		}
		this.summary = builder.toString();
		PrimeFaces.current().ajax().update("menuForm:summaryForm");
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
