package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.disability.DisabilityPermission;
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
 * Разрешение по документу нетрудоспособности
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance (clazz = DisabilityPermission.class)
@Comment("Разрешение по документу нетрудоспособности")
@WebTrail(comment = "Разрешение по документу нетрудоспособности", nameProperties= "info", view="entityParentView-dis_permission.do")
@Parent(property="disabilityDocument", parentForm=DisabilityDocumentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document/Permission")
public class DisabilityPermissionForm extends IdEntityForm {
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	
	/** Разрешено */
	@Comment("Разрешено")
	@Persist
	public Boolean getPermission() {return thePermission;}
	public void setPermission(Boolean aPermission) {thePermission = aPermission;}


	/** Дата начала разрешения */
	@Comment("Дата начала разрешения")
	@Persist @Required @DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания разрешения */
	@Comment("Дата окончания разрешения")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Информация о разрешении */
	@Comment("Информация о разрешении")
	@Persist
	public String getInfo() {return theInfo;	}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Документ нетрудоспособности */
	private Long theDisabilityDocument;
	/** Информация о разрешении */
	private String theInfo;
	/** Комментарии */
	private String theComment;
	/** Разрешено */
	private Boolean thePermission;
	/** Дата начала разрешения */
	private String theDateFrom;
	/** Дата окончания разрешения */
	private String theDateTo;
}
