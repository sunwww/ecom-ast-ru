package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.prescription.PrescriptionFulfilment;
import ru.ecom.mis.ejb.form.prescription.interceptor.FulfilmentPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Выполнениe назначения
 * @author oegorova,stkacheva
 *
 */

@EntityForm
@EntityFormPersistance(clazz = PrescriptionFulfilment.class)
@Comment("Выполнение назначения")
@WebTrail(comment = "Выполнение назначения", nameProperties= "id", view="entityView-pres_prescriptionFulfilment.do")
@Parent(property="prescription", parentForm=PrescriptionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/PrescriptionFulfilment")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(FulfilmentPreCreate.class)
)
public class PrescriptionFulfilmentForm extends IdEntityForm{
	
	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist @Required
	@DateString @DoDateString
	public String getFulfilDate() {return theFulfilDate;}
	public void setFulfilDate(String aFulfilDate) {theFulfilDate = aFulfilDate;}

	/** Дата выполнения */
	private String theFulfilDate;
	
	/** Время выполнения */
	@Comment("Время выполнения")
	@Persist @Required
	@TimeString @DoTimeString
	public String getFulfilTime() {return theFulfilTime;}
	public void setFulfilTime(String aFulfilTime) {theFulfilTime = aFulfilTime;}

	/** Время выполнения */
	private String theFulfilTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecutorWorkFunction() {return theExecutorWorkFunction;}
	public void setExecutorWorkFunction(Long aExecutor) {theExecutorWorkFunction = aExecutor;}

	/** Исполнитель */
	private Long theExecutorWorkFunction;
	
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Комментарии */
	private String theComments;
	
	/** Назначение */
	@Comment("Назначение")
	@Persist
	public Long getPrescription() {return thePrescription;}
	public void setPrescription(Long aPrescription) {thePrescription = aPrescription;}

	/** Назначение */
	private Long thePrescription;
	
	/** Исполнитель (text) */
	@Comment("Исполнитель (text)")
	@Persist
		public String getExecutorInfo() {return theExecutorInfo;}
		public void setExecutorInfo(String aExecutorInfo) {theExecutorInfo = aExecutorInfo;}
		
	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;	}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	
	/** Дата создания записи */
	@Comment("Дата создания записи")
	@Persist @DoDateString @DateString
	public String getDateCreate() {return theDateCreate;}
	public void setDateCreate(String aDateCreate) {theDateCreate = aDateCreate;}

	/** Дата создания записи */
	private String theDateCreate;
	/** Пользователь */
	private String theUsername;
        private String theExecutorInfo;
        
//        /** Стационарный? */
//		@Comment("Стационарный?")
//		@Persist
//		public boolean isInHospitalMedCase() {
//			return theInHospitalMedCase;
//		}
//
//		public void setInHospitalMedCase(boolean aInHospitalMedCase) {
//			theInHospitalMedCase = aInHospitalMedCase;
//		}
//
//		/** Стационарный? */
//		private boolean theInHospitalMedCase;
}
