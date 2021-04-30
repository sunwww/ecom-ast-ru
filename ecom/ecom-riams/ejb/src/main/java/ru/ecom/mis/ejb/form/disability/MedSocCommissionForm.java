package ru.ecom.mis.ejb.form.disability;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.disability.MedSocCommission;
import ru.ecom.mis.ejb.form.disability.interceptors.MedSocCommissionPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Медико-социальная экспертная комиссия
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance (clazz = MedSocCommission.class)
@Comment("Медико-социальная экспертная комиссия")
@WebTrail(comment = "Медико-социальная экспертная комиссия", nameProperties= "info", view="entityParentView-dis_medSocCommission.do")
@Parent(property="disabilityDocument", parentForm=DisabilityDocumentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document/MedSocCommission")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedSocCommissionPreCreate.class)
)
@Setter
public class MedSocCommissionForm extends IdEntityForm {
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return disabilityDocument;}

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @Required @DateString @DoDateString
	public String getAssignmentDate() {return assignmentDate;}

	/** Дата регистрации документов */
	@Comment("Дата регистрации документов")
	@Persist @DateString @DoDateString
	public String getRegistrationDate() {return registrationDate;}

	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	@Persist @DateString @DoDateString 
	public String getExaminationDate() {return examinationDate;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Степень ограничения трудоспособности */
	@Comment("Степень ограничения трудоспособности")
	@Persist 
	public Long getDisabilityDergee() {return disabilityDergee;}

	/** Инвалидность */
	@Comment("Инвалидность")
	@Persist
	public Long getInvalidity() {return invalidity;}

	/** Инвалидность инфо */
	@Comment("Инвалидность инфо")
	@Persist
	public String getInvalidityInfo() {return invalidityInfo;}

	/** Степень ограничения трудоспособности инфор */
	@Comment("Степень ограничения трудоспособности инфор")
	@Persist
	public String getDisabilityDergeeInfo() {return disabilityDergeeInfo;}

	/** Информация о МСЭК */
	@Comment("Информация о МСЭК")
	@Persist
	public String getInfo() {return info;}

	/** Изменена/установлена инвалидность */
	@Comment("Изменена/установлена инвалидность")
	@Persist
	public Boolean getInvalidityRegistration() {return invalidityRegistration;}

	/** Изменена/установлена инвалидность */
	private Boolean invalidityRegistration;
	/** Информация о МСЭК */
	private String info;
	/** Степень ограничения трудоспособности инфор */
	private String disabilityDergeeInfo;
	/** Инвалидность инфо */
	private String invalidityInfo;
	/** Документ нетрудоспособности */
	private Long disabilityDocument;
	/** Дата направления */
	private String assignmentDate;
	/** Дата регистрации документов */
	private String registrationDate;
	/** Дата освидетельствования */
	private String examinationDate;
	/** Комментарии */
	private String comments;
	/** Степень ограничения трудоспособности */
	private Long disabilityDergee;
	/** Инвалидность */
	private Long invalidity;

}
