package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
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
@Setter
public class DrugPrescriptionForm extends PrescriptionForm{

	private Long drug;
	private Long vocDrug;
	private Long method;
	private Long frequencyUnit;
	private String frequency;
	private Long orderType;
	private String orderTime;
	private Long amountUnit;
	private String drugInfo;
	private Float amount;
	private String descriptionInfo;
	private String planEndDate;
	private String planEndTime;


	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	@Persist @DateString @DoDateString
	public String getPlanEndDate() {return planEndDate;}

	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	@Persist @TimeString @DoTimeString
	public String getPlanEndTime() {return planEndTime;}

	@Comment("Лекарство")
	@Persist
	public Long getDrug() {
		return drug;
	}

	@Comment("Метод введения")
	@Persist
	public Long getMethod() {
		return method;
	}

	@Comment("Единица частоты использования")
	@Persist
	public Long getFrequencyUnit() {
		return frequencyUnit;
	}

	@Comment("Частота использования")
	@Persist
	public String getFrequency() {
		return frequency;
	}

	@Comment("Тип порядка использования")
	@Persist
	public Long getOrderType() {
		return orderType;
	}

	@Comment("Время порядка использования")
	@Persist
	public String getOrderTime() {
		return orderTime;
	}

	@Comment("Единица измерения количества")
	@Persist
	public Long getAmountUnit() {
		return amountUnit;
	}

	@Comment("Количество")
	@Persist
	public Float getAmount() {
		return amount;
	}

	@Comment("Наименование лекарственного препарата")
	@Persist
	public String getDrugInfo() {return drugInfo;}

	@Comment("Описание лекарственного назначения")
	@Transient
	public String getDescriptionInfo() {
		return descriptionInfo;
	}

	/** Лекарство */
	@Comment("Лекарство")
	@Persist @Required
	public Long getVocDrug() {return vocDrug;}

	/** форма выполнения */
	@Comment("форма выполнения")
	public PrescriptionFulfilmentForm getFulfilmentForm() {return fulfilmentForm;}
	private PrescriptionFulfilmentForm fulfilmentForm = new PrescriptionFulfilmentForm();


}
