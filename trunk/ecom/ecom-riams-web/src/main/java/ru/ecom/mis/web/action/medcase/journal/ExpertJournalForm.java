package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class ExpertJournalForm extends BaseValidatorForm {
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
	
	
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	
	
	/** modeCase */
	@Comment("modeCase")
	public Long getModeCase() {return theModeCase;}
	public void setModeCase(Long aModeCase) {theModeCase = aModeCase;}

	/** patientStatus */
	@Comment("patientStatus")
	public Long getPatientStatus() {return thePatientStatus;}
	public void setPatientStatus(Long aPatientStatus) {thePatientStatus = aPatientStatus;}

	/** reasonDirect */
	@Comment("reasonDirect")
	public Long getReasonDirect() {return theReasonDirect;}
	public void setReasonDirect(Long aReasonDirect) {theReasonDirect = aReasonDirect;}

	/** deviationStandards */
	@Comment("deviationStandards")
	public Long getDeviationStandards() {
		return theDeviationStandards;
	}

	public void setDeviationStandards(Long aDeviationStandards) {
		theDeviationStandards = aDeviationStandards;
	}

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
	/** deviationStandards */
	private Long theDeviationStandards;
	/** reasonDirect */
	private Long theReasonDirect;
	/** patientStatus */
	private Long thePatientStatus;
	/** modeCase */
	private Long theModeCase;
	/** Отделение */
	private Long theDepartment;
	
	/** Дата окончания периода */
	private String theDateEnd;
	/** Дата начала периода */
	private String theDateBegin;
	
	/** Тип ВК */
	@Comment("Тип ВК")
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип ВК */
	private Long theType;
}
