package ru.ecom.mis.ejb.form.prescription;

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
public class DrugPrescriptionByTicketForm extends DrugPrescriptionForm {

	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist 
	@DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}

	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString 
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}

	/** Метод введения */
	@Comment("Метод введения")
	@Persist
	public Long getMethod() {return theMethod;}
	public void setMethod(Long aMethod) {theMethod = aMethod;}
	
	/** Назначивший */
	@Comment("Назначивший")
	@Persist
	public Long getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(Long aPrescriptor) {thePrescriptSpecial = aPrescriptor;	}

	/** Протокол */
	@Comment("Протокол")
	@Persist 
	public Long getDiary() {return theDiary;}
	public void setDiary(Long aDiary) {theDiary = aDiary;}

	/** Протокол */
	private Long theDiary;
	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Метод введения */
	private Long theMethod;	/** Плановое время начала */
	private String thePlanStartTime;
	/** Плановая дата начала */
	private String thePlanStartDate;
	
	/** Номер */
	@Comment("Номер")
	@Persist
	public String getNumberPrescript() {
		return theNumberPrescript;
	}

	public void setNumberPrescript(String aNumberPrescript) {
		theNumberPrescript = aNumberPrescript;
	}

	/** Номер */
	private String theNumberPrescript;

	/** Кол-во строковое */
	@Comment("Кол-во строковое")
	@Persist
	public String getAmountString() {
		return theAmountString;
	}

	public void setAmountString(String aAmountString) {
		theAmountString = aAmountString;
	}

	/** Кол-во строковое */
	private String theAmountString;}
