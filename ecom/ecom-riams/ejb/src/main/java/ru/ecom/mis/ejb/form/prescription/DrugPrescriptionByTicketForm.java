package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescriptionByTicket;
import ru.ecom.poly.ejb.form.protocol.ProtocolForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Назначение на лекарства
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = DrugPrescriptionByTicket.class)
@Comment("Назначение лекарства")
@WebTrail(comment = "Назначение лекарства", nameProperties= "id",list="entityParentList-pres_drugPrescription.do", view="entityParentView-pres_drugPrescription.do")
@Parent(property="diary", parentForm=ProtocolForm.class)
@EntityFormSecurityPrefix("/Policy/Poly/DrugPrescription")
@Setter
public class DrugPrescriptionByTicketForm extends DrugPrescriptionForm {

	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist 
	@DateString @DoDateString
	public String getPlanStartDate() {return planStartDate;}

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString 
	public String getPlanStartTime() {return planStartTime;}

	/** Метод введения */
	@Comment("Метод введения")
	@Persist
	public Long getMethod() {return method;}

	/** Назначивший */
	@Comment("Назначивший")
	@Persist
	public Long getPrescriptSpecial() {return prescriptSpecial;}

	/** Протокол */
	@Comment("Протокол")
	@Persist 
	public Long getDiary() {return diary;}

	/** Протокол */
	private Long diary;
	/** Назначивший */
	private Long prescriptSpecial;
	/** Метод введения */
	private Long method;	/** Плановое время начала */
	private String planStartTime;
	/** Плановая дата начала */
	private String planStartDate;
	
	/** Номер */
	@Comment("Номер")
	@Persist
	public String getNumberPrescript() {
		return numberPrescript;
	}

	/** Номер */
	private String numberPrescript;

	/** Кол-во строковое */
	@Comment("Кол-во строковое")
	@Persist
	public String getAmountString() {
		return amountString;
	}

	/** Кол-во строковое */
	private String amountString;}
