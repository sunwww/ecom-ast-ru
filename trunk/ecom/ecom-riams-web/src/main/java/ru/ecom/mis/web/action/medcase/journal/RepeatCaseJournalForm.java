package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Min;
import ru.nuzmsh.forms.validator.validators.Required;

public class RepeatCaseJournalForm extends BaseValidatorForm{
	/** Дата начала периода */
	@Comment("Дата начала периода") 
	@Required @DoDateString @DateString
	public String getDateBegin() {return theDateBegin;}
	public void setDateBegin(String aDateBegin) {theDateBegin = aDateBegin;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@Required @DoDateString @DateString
	public String getDateEnd() {return theDateEnd;}
	public void setDateEnd(String aDateEnd) {theDateEnd = aDateEnd;}
	
	/** Кол-во случаев */
	@Comment("Кол-во случаев")
	@Required @Min(value = "2")
	public Long getCnt() {return theCnt;}
	public void setCnt(Long aCnt) {theCnt = aCnt;}

	/** Кол-во случаев */
	private Long theCnt;
	/** Дата окончания периода */
	private String theDateEnd;
	/** Дата начала периода */
	private String theDateBegin;
}
