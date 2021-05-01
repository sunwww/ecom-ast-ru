package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
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
@Setter
public class PrescriptionFulfilmentForm extends IdEntityForm{
	
	/** Дата выполнения */
	@Comment("Дата выполнения")
	@Persist @Required
	@DateString @DoDateString
	public String getFulfilDate() {return fulfilDate;}

	/** Дата выполнения */
	private String fulfilDate;
	
	/** Время выполнения */
	@Comment("Время выполнения")
	@Persist @Required
	@TimeString @DoTimeString
	public String getFulfilTime() {return fulfilTime;}

	/** Время выполнения */
	private String fulfilTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecutorWorkFunction() {return executorWorkFunction;}

	/** Исполнитель */
	private Long executorWorkFunction;
	
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Комментарии */
	private String comments;
	
	/** Назначение */
	@Comment("Назначение")
	@Persist
	public Long getPrescription() {return prescription;}

	/** Назначение */
	private Long prescription;
	
	/** Исполнитель (text) */
	@Comment("Исполнитель (text)")
	@Persist
		public String getExecutorInfo() {return executorInfo;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;	}

	/** Дата создания записи */
	@Comment("Дата создания записи")
	@Persist @DoDateString @DateString
	public String getDateCreate() {return dateCreate;}

	/** Дата создания записи */
	private String dateCreate;
	/** Пользователь */
	private String username;
        private String executorInfo;
}
