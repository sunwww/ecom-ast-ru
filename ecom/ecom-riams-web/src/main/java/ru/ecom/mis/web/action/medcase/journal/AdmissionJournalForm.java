package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;
/**
 * Журнал обращений
 * @author stkacheva
 */
public class AdmissionJournalForm extends BaseValidatorForm{
	/** Дата начала периода */
	@Comment("Дата начала периода") 
	@Required
	public String getDateBegin() {return theDateBegin;}
	public void setDateBegin(String aDateBegin) {theDateBegin = aDateBegin;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	public String getDateEnd() {return theDateEnd;}
	public void setDateEnd(String aDateEnd) {theDateEnd = aDateEnd;}

	/** Искать по дате выписке? */
	@Comment("Искать по дате выписке?")
	public Boolean getDischargeIs() {return theDischargeIs;}
	public void setDischargeIs(Boolean aDischargeIs) {theDischargeIs = aDischargeIs;}

	
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergancyIs() {return theEmergancyIs;}
	public void setEmergancyIs(Boolean aEmergancyIs) {theEmergancyIs = aEmergancyIs;}

	/** Время */
	@Comment("Время")
	@DoTimeString @TimeString
	public String getTimeBegin() {return theTimeBegin;}
	public void setTimeBegin(String aTimeBegin) {theTimeBegin = aTimeBegin;}

	/** Время */
	private String theTimeBegin;
	/** Экстренность */
	private Boolean theEmergancyIs;
	/** Отделение */
	private Long theDepartment;
	/** Искать по дате выписке? */
	private Boolean theDischargeIs;
	/** Дата окончания периода */
	private String theDateEnd;
	/** Дата начала периода */
	private String theDateBegin;
}
