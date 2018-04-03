package ru.ecom.mis.ejb.form.medcase.transfusion;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.TransfusionMonitoring;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= TransfusionMonitoring.class)
@Comment("Переливание трансфузионных сред")
@WebTrail(comment = "Переливание трансфузионных сред", nameProperties= "id", view="entitySubclassView-trans_transfusion.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="transfusion", parentForm= TransfusionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
@Subclasses({BloodTransfusionForm.class, OtherTransfusionForm.class})
public class TransfusionMonitoringForm extends IdEntityForm {
	/** Кол-во часов */
	@Comment("Кол-во часов")
	public Integer getHourAfterTransfusion() {return theHourAfterTransfusion;}
	public void setHourAfterTransfusion(Integer aHourAfterTransfusion) {theHourAfterTransfusion = aHourAfterTransfusion;}

	/** Частота пульса */
	@Comment("Частота пульса")
	public Integer getPulseRate() {return thePulseRate;}
	public void setPulseRate(Integer aPulseRate) {thePulseRate = aPulseRate;}

	/** Температура */
	@Comment("Температура")
	@DoIntegerString @IntegerString
	public String getTemperature() {return theTemperature;}
	public void setTemperature(String aTemperature) {theTemperature = aTemperature;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	public Integer getBloodPressureTop() {return theBloodPressureTop;}
	public void setBloodPressureTop(Integer aBloodPressureTop) {theBloodPressureTop = aBloodPressureTop;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	public Integer getBloodPressureLower() {return theBloodPressureLower;}
	public void setBloodPressureLower(Integer aBloodPressureLower) {theBloodPressureLower = aBloodPressureLower;}

	/** Переливание */
	@Comment("Переливание")
	@Persist @Required
	public Long getTransfusion() {return theTransfusion;}
	public void setTransfusion(Long aTransfusion) {theTransfusion = aTransfusion;}

	/** Переливание */
	private Long theTransfusion;
	/** Артериальное давление (нижнее) */
	private Integer theBloodPressureLower;
	/** Артериальное давление (верхнее) */
	private Integer theBloodPressureTop;
	/** Температура */
	private String theTemperature;
	/** Частота пульса */
	private Integer thePulseRate;
	/** Кол-во часов */
	private Integer theHourAfterTransfusion;


	/** Моча */
	@Comment("Моча")
	@Persist
	public Long getUrineColor() {return theUrineColor;}
	public void setUrineColor(Long aUrineColor) {theUrineColor = aUrineColor;}

	/** Диурез */
	@Comment("Диурез")
	@Persist
	public Integer getDiuresis() {return theDiuresis;}
	public void setDiuresis(Integer aDiuresis) {theDiuresis = aDiuresis;}

	/** Моча */
	private Long theUrineColor;
	/** Диурез */
	private Integer theDiuresis;
}
