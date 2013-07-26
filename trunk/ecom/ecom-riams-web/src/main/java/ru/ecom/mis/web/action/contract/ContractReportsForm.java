package ru.ecom.mis.web.action.contract;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class ContractReportsForm extends BaseValidatorForm{
	/** dateto */
	@DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	
	/** DateFrom */
	@DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Оператор */
	public Long getOperator() {return theOperator;}
	public void setOperator(Long aOperator) {theOperator = aOperator;}

	/** Прейскурант */
	public Long getPriceList() {return thePriceList;}
	public void setPriceList(Long aPriceList) {thePriceList = aPriceList;}

	/** Фильтр по наименованию */
	public String getFilterByName() {return theFilterByName;}
	public void setFilterByName(String aFilterByName) {theFilterByName = aFilterByName;}

	/** Фильтр по коду */
	public String getFilterByCode() {return theFilterByCode;}
	public void setFilterByCode(String aFilterByCode) {theFilterByCode = aFilterByCode;}

	/** Фильтр по коду */
	private String theFilterByCode;
	/** Фильтр по наименованию */
	private String theFilterByName;
	/** Прейскурант */
	private Long thePriceList;
	/** Оператор */
	private Long theOperator;
	/** dateto */
	private String theDateTo;
	/** DateFrom */
	private String theDateFrom;
}
