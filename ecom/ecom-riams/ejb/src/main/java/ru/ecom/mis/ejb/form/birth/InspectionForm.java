package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class InspectionForm extends IdEntityForm{
	/** Кто проводил осмотр */
	@Comment("Кто проводил осмотр")
	@Persist 
	public String getInspector() {return inspector;}

	/** Дата осмотра */
	@Comment("Дата осмотра")
	@Persist @DateString @DoDateString
	@Required
	public String getInspectionDate() {return inspectionDate;}

	/** Время осмотра */
	@Comment("Время осмотра")
	@Persist @TimeString @DoTimeString
	@Required
	public String getInspectionTime() {return inspectionTime;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return pregnancy;}

	/** История родов */
	@Comment("История родов")
	@Persist
	public Long getPregnancyHistory() {return pregnancyHistory;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Описание */
	@Comment("Описание")
	@Persist
	public String getNotes() {return notes;}

	/** Специалист, проводивший осмотр */
	@Comment("Специалист, проводивший осмотр")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Информация об осмотре */
	@Comment("Информация об осмотре")
	@Persist
	public String getInformation() {return information;}

	/** Информация о специалисте */
	@Comment("Информация о специалисте")
	@Persist
	public String getWorkFunctionInfo() {return workFunctionInfo;}

	/** Информация о типе осмотра */
	@Comment("Информация о типе осмотра")
	@Persist
	public String getTypeInformation() {return typeInformation;}

	/** Информация о типе осмотра */
	private String typeInformation;
	/** Информация о специалисте */
	private String workFunctionInfo;
	/** Информация об осмотре */
	private String information;
	/** Специалист, проводивший осмотр */
	private Long workFunction;
	/** Случай медицинского обслуживания */
	private Long medCase;
	/** История родов */
	private Long pregnancyHistory;
	/** Беременность */
	private Long pregnancy;
	/** Кто проводил осмотр */
	private String inspector;
	/** Дата осмотра */
	private String inspectionDate;
	/** Время осмотра */
	private String inspectionTime;
	/** Описание */
	private String notes;

}
