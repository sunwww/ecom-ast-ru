package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import javax.persistence.Transient;

/**
 * Назначение на лекарства
 * @author oegorova,rkurbanov
 *
 */

@EntityForm
@EntityFormPersistance(clazz = DrugPrescription.class)
@Comment("Назначение лекарства")
@WebTrail(comment = "Назначение лекарства", nameProperties= "drug",list="entityParentList-pres_drugPrescription.do", view="entityParentView-pres_drugPrescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/DrugPrescription")
public class DrugPrescriptionForm extends PrescriptionForm{

	private Long theDrug;
	private Long theVocDrug;
	private Long theMethod;
	private Long theFrequencyUnit;
	private String theFrequency;
	private Long theOrderType;
	private String theOrderTime;
	private Long theAmountUnit;
	private String theDrugInfo;
	private Float theAmount;
	private String theDescriptionInfo;
	private String thePlanEndDate;
	private String thePlanEndTime;


	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	@Persist @DateString @DoDateString
	public String getPlanEndDate() {return thePlanEndDate;}
	public void setPlanEndDate(String aPlanEndDate) {thePlanEndDate = aPlanEndDate;}

	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	@Persist @TimeString @DoTimeString
	public String getPlanEndTime() {return thePlanEndTime;}
	public void setPlanEndTime(String aPlanEndTime) {thePlanEndTime = aPlanEndTime;}

	@Comment("Лекарство")
	@Persist
	public Long getDrug() {
		return theDrug;
	}
	public void setDrug(Long aDrug) {
		theDrug = aDrug;
	}

	@Comment("Метод введения")
	@Persist
	public Long getMethod() {
		return theMethod;
	}
	public void setMethod(Long aMethod) {
		theMethod = aMethod;
	}

	@Comment("Единица частоты использования")
	@Persist
	public Long getFrequencyUnit() {
		return theFrequencyUnit;
	}
	public void setFrequencyUnit(Long aFrequencyUnit) {
		theFrequencyUnit = aFrequencyUnit;
	}

	@Comment("Частота использования")
	@Persist
	public String getFrequency() {
		return theFrequency;
	}
	public void setFrequency(String aFrequency) {
		theFrequency = aFrequency;
	}

	@Comment("Тип порядка использования")
	@Persist
	public Long getOrderType() {
		return theOrderType;
	}
	public void setOrderType(Long aOrderType) {
		theOrderType = aOrderType;
	}

	@Comment("Время порядка использования")
	@Persist
	public String getOrderTime() {
		return theOrderTime;
	}
	public void setOrderTime(String aOrderTime) {
		theOrderTime = aOrderTime;
	}

	@Comment("Единица измерения количества")
	@Persist
	public Long getAmountUnit() {
		return theAmountUnit;
	}
	public void setAmountUnit(Long aAmountUnit) {
		theAmountUnit = aAmountUnit;
	}

	@Comment("Количество")
	@Persist
	public Float getAmount() {
		return theAmount;
	}
	public void setAmount(Float aAmount) {
		theAmount = aAmount;
	}

	///** Краткий справочник лекарственных средств */
	//@Comment("Краткий справочник лекарственных средств")
	//@Persist
	//public Long getDrugName() {return theDrugName;}
	//public void setDrugName(Long aDrugName) {theDrugName = aDrugName;}

	///** Краткий справочник лекарственных средств */
	//private Long theDrugName;

	@Comment("Наименование лекарственного препарата")
	@Persist
	public String getDrugInfo() {return theDrugInfo;}
	public void setDrugInfo(String aDrugInfo) {theDrugInfo = aDrugInfo;}

	@Comment("Описание лекарственного назначения")
	@Transient
	public String getDescriptionInfo() {
		return theDescriptionInfo;
	}
	public void setDescriptionInfo (String aDescriptionInfo) { theDescriptionInfo = aDescriptionInfo;}


	/** Лекарство */
	@Comment("Лекарство")
	@Persist @Required
	public Long getVocDrug() {return theVocDrug;}
	public void setVocDrug(Long aVocDrug) {theVocDrug = aVocDrug;}

	/** форма выполнения */
	@Comment("форма выполнения")
	public PrescriptionFulfilmentForm getFulfilmentForm() {return theFulfilmentForm;}
	public void setFulfilmentForm(PrescriptionFulfilmentForm aFulfilmentForm) {theFulfilmentForm = aFulfilmentForm;}
	private PrescriptionFulfilmentForm theFulfilmentForm = new PrescriptionFulfilmentForm();


}
