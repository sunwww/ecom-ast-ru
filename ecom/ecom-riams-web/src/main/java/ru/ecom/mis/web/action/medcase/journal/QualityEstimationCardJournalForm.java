package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class QualityEstimationCardJournalForm extends BaseValidatorForm {
	/** Дата начала периода */
	@Comment("Дата начала периода") 
	@Required @DateString @DoDateString
	public String getDateBegin() {return theDateBegin;}
	public void setDateBegin(String aDateBegin) {theDateBegin = aDateBegin;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString
	public String getDateEnd() {return theDateEnd;}
	public void setDateEnd(String aDateEnd) {theDateEnd = aDateEnd;}
	
	/** Тип экспертизы качества */
	@Comment("Тип экспертизы качества")
	public Long getEstimationKind() {return theEstimationKind;}
	public void setEstimationKind(Long aEstimationKind) {theEstimationKind = aEstimationKind;}
	/** Тип экспертизы качества */
	private Long theEstimationKind;
	
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	
	/** conclusionSent */
	@Comment("conclusionSent")
	public Long getConclusionSent() {return theConclusionSent;}
	public void setConclusionSent(Long aConclusionSent) {theConclusionSent = aConclusionSent;}

	/** conclusion */
	@Comment("conclusion")
	public Long getConclusion() {return theConclusion;}
	public void setConclusion(Long aConclusion) {theConclusion = aConclusion;}

	/** conclusion */
	private Long theConclusion;
	/** conclusionSent */
	private Long theConclusionSent;

	/** Отделение */
	private Long theDepartment;
	
	/** Дата окончания периода */
	private String theDateEnd;
	/** Дата начала периода */
	private String theDateBegin;
	
}
