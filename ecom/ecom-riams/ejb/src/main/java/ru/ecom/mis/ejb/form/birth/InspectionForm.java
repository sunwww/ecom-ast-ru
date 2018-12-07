package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.Inspection;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;
/**
 * Осмотр
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Inspection.class)
@Comment("Осмотр")
@WebTrail(comment = "Осмотр", nameProperties= "id", view="entitySubclassView-preg_inspection.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection")
@Subclasses( { PregnanInspectionForm.class,ConfinedInspectionForm.class, AudiologicScreeningForm.class,
	ApgarEstimationForm.class, DownesEstimationForm.class, NewBornInfRiskEstimationForm.class, 
	HereditaryScreeningForm.class})
public class InspectionForm extends IdEntityForm{
	/** Кто проводил осмотр */
	@Comment("Кто проводил осмотр")
	@Persist 
	public String getInspector() {return theInspector;}
	public void setInspector(String aInspector) {theInspector = aInspector;}
	
	/** Дата осмотра */
	@Comment("Дата осмотра")
	@Persist @DateString @DoDateString
	@Required
	public String getInspectionDate() {return theInspectionDate;}
	public void setInspectionDate(String aInspectionDate) {theInspectionDate = aInspectionDate;}

	/** Время осмотра */
	@Comment("Время осмотра")
	@Persist @TimeString @DoTimeString
	@Required
	public String getInspectionTime() {return theInspectionTime;}
	public void setInspectionTime(String aInspectionTime) {theInspectionTime = aInspectionTime;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** История родов */
	@Comment("История родов")
	@Persist
	public Long getPregnancyHistory() {return thePregnancyHistory;}
	public void setPregnancyHistory(Long aPregnancyHistory) {thePregnancyHistory = aPregnancyHistory;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/** Описание */
	@Comment("Описание")
	@Persist
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}
	
	/** Специалист, проводивший осмотр */
	@Comment("Специалист, проводивший осмотр")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Информация об осмотре */
	@Comment("Информация об осмотре")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}

	/** Информация о специалисте */
	@Comment("Информация о специалисте")
	@Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aWorkFunctionInfo) {theWorkFunctionInfo = aWorkFunctionInfo;}

	/** Информация о типе осмотра */
	@Comment("Информация о типе осмотра")
	@Persist
	public String getTypeInformation() {return theTypeInformation;}
	public void setTypeInformation(String aTypeInformation) {theTypeInformation = aTypeInformation;}

	/** Информация о типе осмотра */
	private String theTypeInformation;
	/** Информация о специалисте */
	private String theWorkFunctionInfo;
	/** Информация об осмотре */
	private String theInformation;
	/** Специалист, проводивший осмотр */
	private Long theWorkFunction;
	/** Случай медицинского обслуживания */
	private Long theMedCase;
	/** История родов */
	private Long thePregnancyHistory;
	/** Беременность */
	private Long thePregnancy;
	/** Кто проводил осмотр */
	private String theInspector;
	/** Дата осмотра */
	private String theInspectionDate;
	/** Время осмотра */
	private String theInspectionTime;
	/** Описание */
	private String theNotes;

}
