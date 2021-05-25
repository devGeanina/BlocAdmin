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
import com.blocadmin.core.dto.ExpenseDTO;
import com.blocadmin.core.dto.HouseholdDTO;
import com.blocadmin.core.service.ExpenseService;
import com.blocadmin.core.service.HouseholdService;
import com.blocadmin.core.utils.Constants;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@Named
@Scope("view")
public class ExpensesBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ExpensesBean.class);
	
	@Inject
	private ExpenseService expenseService;
	
	@Inject
	private HouseholdService householdService;
	
	private List<ExpenseDTO> expenses;
	private List<ExpenseDTO> selectedExpenses;
	private ExpenseDTO selectedExpense;
	private List<HouseholdDTO> households;
	private String defaultDateTimeFormat = Constants.DATE_TIME_FORMAT;
	private String summary;
	
	private PDFOptions pdfOpt;
	
	public List<ExpenseDTO> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<ExpenseDTO> expenses) {
		this.expenses = expenses;
	}
	public List<ExpenseDTO> getSelectedExpenses() {
		return selectedExpenses;
	}
	public void setSelectedExpenses(List<ExpenseDTO> selectedExpenses) {
		this.selectedExpenses = selectedExpenses;
	}
	public ExpenseDTO getSelectedExpense() {
		return selectedExpense;
	}
	public void setSelectedExpense(ExpenseDTO selectedExpense) {
		this.selectedExpense = selectedExpense;
	}
	
	public List<HouseholdDTO> getHouseholds() {
		return households;
	}
	public void setHouseholds(List<HouseholdDTO> households) {
		this.households = households;
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
			this.expenses = expenseService.getExpenses();
			this.households = householdService.getHouseholds();
			this.selectedExpense = new ExpenseDTO();
			selectedExpense.setHouseholdIds(new ArrayList<Long>());
			selectedExpense.setHouseholdsAddresses(new ArrayList<String>());
			createSummary();
		} catch (Exception e) {
			LOGGER.error("Exception fetching the expense: " + e.getMessage());
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

	public String deleteExpense(Long id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			expenseService.deleteExpense(id);
			facesContext.addMessage(null, new FacesMessage("Expense Removed!"));
			PrimeFaces.current().ajax().update("expenseForm:messages", "expenseForm:dt-expenses");
			return "/gui/expenses/viewExpenses.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception deleting the expense: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting the expense."));
			return null; // will reuse current view
		}
	}

	public String saveExpense() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			expenseService.saveExpense(selectedExpense);
			facesContext.addMessage(null, new FacesMessage("Expense information added."));
			PrimeFaces.current().executeScript("PF('manageExpenseDialog').hide()");
			PrimeFaces.current().ajax().update("expenseForm:messages", "expenseForm:dt-expenses");
			return "/gui/expenses/viewExpenses.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception saving the expense: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error saving the expense."));
			return null; // will reuse current view
		}
	}
	
	public void createSummary() {
		StringBuilder builder = new StringBuilder();
		if(expenses != null && !expenses.isEmpty()) {
			List<ExpenseDTO> monthlyExpenses = expenses.stream()
				    .filter(o -> o.getExpenseType().equalsIgnoreCase(Constants.EXPENSE_TYPE.MONTHLY.getName())).collect(Collectors.toList());
			List<ExpenseDTO> yearlyExpenses = expenses.stream()
				    .filter(o -> o.getExpenseType().equalsIgnoreCase(Constants.EXPENSE_TYPE.YEARLY.getName())).collect(Collectors.toList());
			double leftoverSum = expenses.stream().mapToDouble(o -> o.getLeftoverSum()).sum();
			builder.append("There are a total of ").append(expenses.size()).append(" registered, out of which ").append(monthlyExpenses.size()).append(" monthly and ").append(yearlyExpenses.size()).append(" yearly. Leftover sum to recover from tenants: ").append(leftoverSum).append(".");
		}else {
			builder.append("No information on the expenses found yet.");
		}
		this.summary = builder.toString();
	}

	public void openNew() {
		this.selectedExpense = new ExpenseDTO();
		selectedExpense.setHouseholdIds(new ArrayList<Long>());
		selectedExpense.setHouseholdsAddresses(new ArrayList<String>());
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
