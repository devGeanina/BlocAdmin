package com.blocadmin.core.bean;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.context.annotation.Scope;
import com.blocadmin.core.dto.ExpenseDTO;
import com.blocadmin.core.dto.HouseholdDTO;
import com.blocadmin.core.service.ExpenseService;
import com.blocadmin.core.service.HouseholdService;
import com.blocadmin.core.utils.Utils;


@Named
@Scope("view")
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(HomeBean.class);
	
	@Inject
	private HouseholdService householdService;
	
	@Inject
	private ExpenseService expenseService;
	
	private List<HouseholdDTO> households;
	private BarChartModel barModel;

	public List<HouseholdDTO> getHouseholds() {
		return households;
	}

	public void setHouseholds(List<HouseholdDTO> households) {
		this.households = households;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public void setup() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			this.households = householdService.getHouseholdsWithDebt();
			createBarModel(getExpensesFromLastMonth());
		} catch (Exception e) {
			LOGGER.error("Exception fetching the household information: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing the page."));
		}
	}
		
	private List<ExpenseDTO> getExpensesFromLastMonth(){
		List<ExpenseDTO> lastMonthExpenses = new ArrayList<>();
		List<ExpenseDTO> allExpenses = expenseService.getExpenses();
		
		if (!allExpenses.isEmpty()) {
			for (ExpenseDTO entityDTO : allExpenses) {
				if(!entityDTO.isPayedInFull()) {
					
					LocalDate expenseDueDate = Instant.ofEpochMilli(entityDTO.getDueDate().getTime()).atZone(ZoneId.systemDefault())
							.toLocalDate();
					
					Calendar calBefore = Calendar.getInstance();
					calBefore.add(Calendar.MONTH, -1);
					LocalDate minusAMonth = Instant.ofEpochMilli(calBefore.getTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

					Calendar calAfter = Calendar.getInstance();
					calAfter.add(Calendar.MONTH, +1);
					LocalDate plusAMonthA = Instant.ofEpochMilli(calAfter.getTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
					
					if (expenseDueDate.isAfter(minusAMonth) && expenseDueDate.isBefore(plusAMonthA)) {
						lastMonthExpenses.add(entityDTO);
					}
				}
			}

			if (!lastMonthExpenses.isEmpty()) {
				lastMonthExpenses.sort((o1, o2) -> o1.getDueDate().compareTo(o2.getDueDate()));
				List<ExpenseDTO> lastFiveElem = lastMonthExpenses.subList(Math.max(lastMonthExpenses.size() - 5, 0), lastMonthExpenses.size());
				lastMonthExpenses = lastFiveElem;
			}
		}
		
		return allExpenses;
	}
	
	public void createBarModel(List<ExpenseDTO> expenses) {
		 
	        barModel = new BarChartModel();
	        ChartData data = new ChartData();

	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("Due expenses this month");

	        List<Number> values = new ArrayList<>();
	        List<String> labels = new ArrayList<>();
	        
	        for(ExpenseDTO dto: expenses) {
	        	if(dto.getLeftoverSum() > 0) {
					values.add((Number)dto.getLeftoverSum());
					labels.add(Utils.convertDateToString(dto.getDueDate()));
	        	}
			}
	        
	        barDataSet.setData(values);

	        List<String> bgColor = new ArrayList<>();
	        bgColor.add("rgba(255, 205, 86, 0.2)");
	        bgColor.add("rgba(75, 192, 192, 0.2)");
	        bgColor.add("rgba(54, 162, 235, 0.2)");
	        bgColor.add("rgba(153, 102, 255, 0.2)");
	        bgColor.add("rgba(201, 203, 207, 0.2)");
	        barDataSet.setBackgroundColor(bgColor);

	        List<String> borderColor = new ArrayList<>();
	        borderColor.add("rgb(255, 205, 86)");
	        borderColor.add("rgb(75, 192, 192)");
	        borderColor.add("rgb(54, 162, 235)");
	        borderColor.add("rgb(153, 102, 255)");
	        borderColor.add("rgb(201, 203, 207)");
	        barDataSet.setBorderColor(borderColor);
	        barDataSet.setBorderWidth(1);

	        data.addChartDataSet(barDataSet);

	        data.setLabels(labels);
	        barModel.setData(data);

	        //Options
	        BarChartOptions options = new BarChartOptions();

	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Latest expenses due this month");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        // disable animation
	        Animation animation = new Animation();
	        animation.setDuration(1);
	        options.setAnimation(animation);

	        barModel.setOptions(options);
	    }
	
	public String init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			setup();
		} catch (Exception ex) {
			LOGGER.error("Exception initializing the bean: " + ex.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing the page."));
		}
		return null;
	}
}
