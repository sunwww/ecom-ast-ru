package ru.ecom.mis.ejb.form.disability;

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
public class MedSocCommissionForm extends IdEntityForm {
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @Required @DateString @DoDateString
	public String getAssignmentDate() {return theAssignmentDate;}
	public void setAssignmentDate(String aAssignmentDate) {theAssignmentDate = aAssignmentDate;}

	/** Дата регистрации документов */
	@Comment("Дата регистрации документов")
	@Persist @DateString @DoDateString
	public String getRegistrationDate() {return theRegistrationDate;}
	public void setRegistrationDate(String aRegistrationDate) {theRegistrationDate = aRegistrationDate;}
	
	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	@Persist @DateString @DoDateString 
	public String getExaminationDate() {return theExaminationDate;}
	public void setExaminationDate(String aExaminationDate) {theExaminationDate = aExaminationDate;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Степень ограничения трудоспособности */
	@Comment("Степень ограничения трудоспособности")
	@Persist 
	public Long getDisabilityDergee() {return theDisabilityDergee;}
	public void setDisabilityDergee(Long aDisabilityDergee) {theDisabilityDergee = aDisabilityDergee;}

	/** Инвалидность */
	@Comment("Инвалидность")
	@Persist
	public Long getInvalidity() {return theInvalidity;}
	public void setInvalidity(Long aInvalidity) {theInvalidity = aInvalidity;}

	/** Инвалидность инфо */
	@Comment("Инвалидность инфо")
	@Persist
	public String getInvalidityInfo() {return theInvalidityInfo;}
	public void setInvalidityInfo(String aInvalidityInfo) {theInvalidityInfo = aInvalidityInfo;}

	/** Степень ограничения трудоспособности инфор */
	@Comment("Степень ограничения трудоспособности инфор")
	@Persist
	public String getDisabilityDergeeInfo() {return theDisabilityDergeeInfo;}
	public void setDisabilityDergeeInfo(String aDisabilityDergeeInfo) {theDisabilityDergeeInfo = aDisabilityDergeeInfo;}

	/** Информация о МСЭК */
	@Comment("Информация о МСЭК")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Изменена/установлена инвалидность */
	@Comment("Изменена/установлена инвалидность")
	@Persist
	public Boolean getInvalidityRegistration() {return theInvalidityRegistration;}
	public void setInvalidityRegistration(Boolean aInvalidityRegistration) {theInvalidityRegistration = aInvalidityRegistration;}

	/** Изменена/установлена инвалидность */
	private Boolean theInvalidityRegistration;
	/** Информация о МСЭК */
	private String theInfo;
	/** Степень ограничения трудоспособности инфор */
	private String theDisabilityDergeeInfo;
	/** Инвалидность инфо */
	private String theInvalidityInfo;
	/** Документ нетрудоспособности */
	private Long theDisabilityDocument;
	/** Дата направления */
	private String theAssignmentDate;
	/** Дата регистрации документов */
	private String theRegistrationDate;
	/** Дата освидетельствования */
	private String theExaminationDate;
	/** Комментарии */
	private String theComments;
	/** Степень ограничения трудоспособности */
	private Long theDisabilityDergee;
	/** Инвалидность */
	private Long theInvalidity;

}
