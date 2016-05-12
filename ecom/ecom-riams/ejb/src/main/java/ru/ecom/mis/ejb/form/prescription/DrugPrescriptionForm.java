package ru.ecom.mis.ejb.form.prescription;

import javax.persistence.Transient;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Назначение на лекарства
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = DrugPrescription.class)
@Comment("Назначение лекарства")
@WebTrail(comment = "Назначение лекарства", nameProperties= "drug",list="entityParentList-pres_drugPrescription.do", view="entityParentView-pres_drugPrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/DrugPrescription")
public class DrugPrescriptionForm extends PrescriptionForm{
	
	/** Лекарство */
	@Comment("Лекарство")
	@Persist @Required
	public Long getDrug() {
		return theDrug;
	}

	public void setDrug(Long aDrug) {
		theDrug = aDrug;
	}

	/** Лекарство */
	private Long theDrug;
	
	/** Метод введения */
	@Comment("Метод введения")
	@Persist @Required
	public Long getMethod() {
		return theMethod;
	}

	public void setMethod(Long aMethod) {
		theMethod = aMethod;
	}

	/** Метод введения */
	private Long theMethod;
	
	/** Единица частоты использования */
	@Comment("Единица частоты использования")
	@Persist
	public Long getFrequencyUnit() {
		return theFrequencyUnit;
	}

	public void setFrequencyUnit(Long aFrequencyUnit) {
		theFrequencyUnit = aFrequencyUnit;
	}

	/** Единица частоты использования*/
	private Long theFrequencyUnit;
	
	/** Частота использования */
	@Comment("Частота использования")
	@Persist
	public String getFrequency() {
		return theFrequency;
	}

	public void setFrequency(String aFrequency) {
		theFrequency = aFrequency;
	}

	/** Частота использования */
	private String theFrequency;
	
	/** Единица длительности использования */
	@Comment("Единица длительности использования")
	@Persist
	public Long getDurationUnit() {
		return theDurationUnit;
	}

	public void setDurationUnit(Long aDurationUnit) {
		theDurationUnit = aDurationUnit;
	}

	/** Единица длительности использования */
	private Long theDurationUnit;
	
	/** Длительность использования */
	@Comment("Длительность использования")
	@Persist
	public String getDuration() {
		return theDuration;
	}

	public void setDuration(String aDuration) {
		theDuration = aDuration;
	}

	/** Длительность использования */
	private String theDuration;
	
	/** Тип порядка использования */
	@Comment("Тип порядка использования")
	@Persist
	public Long getOrderType() {
		return theOrderType;
	}

	public void setOrderType(Long aOrderType) {
		theOrderType = aOrderType;
	}

	/** Тип порядка использования */
	private Long theOrderType;
	
	/** Время порядка использования */
	@Comment("Время порядка использования")
	@Persist
	public String getOrderTime() {
		return theOrderTime;
	}

	public void setOrderTime(String aOrderTime) {
		theOrderTime = aOrderTime;
	}

	/** Время порядка использования */
	private String theOrderTime;

	/** Единица измерения количества */
	@Comment("Единица измерения количества")
	@Persist
	public Long getAmountUnit() {
		return theAmountUnit;
	}

	public void setAmountUnit(Long aAmountUnit) {
		theAmountUnit = aAmountUnit;
	}

	/** Единица измерения количества */
	private Long theAmountUnit;
	
	/** Количество */
	@Comment("Количество")
	@Persist
	public String getAmount() {
		return theAmount;
	}

	public void setAmount(String aAmount) {
		theAmount = aAmount;
	}

	/** Количество */
	private String theAmount;
	
	///** Краткий справочник лекарственных средств */
	//@Comment("Краткий справочник лекарственных средств")
	//@Persist 
	//public Long getDrugName() {return theDrugName;}
	//public void setDrugName(Long aDrugName) {theDrugName = aDrugName;}

	///** Краткий справочник лекарственных средств */
	//private Long theDrugName;

	/** Наименование лекарственного препарата */
	@Comment("Наименование лекарственного препарата")
	@Persist
	public String getDrugInfo() {return theDrugInfo;}
	public void setDrugInfo(String aDrugInfo) {theDrugInfo = aDrugInfo;}

	/** Наименование лекарственного препарата */
	private String theDrugInfo;
	
	/** Описание лекарственного назначения */
	@Comment("Описание лекарственного назначения")
	@Transient
	public String getDescriptionInfo() {
			return theDescriptionInfo;
	}
    public void setDescriptionInfo (String aDescriptionInfo) { theDescriptionInfo = aDescriptionInfo;}
    private String theDescriptionInfo;
	
}
