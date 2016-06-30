package ru.ecom.mis.ejb.form.userdocument;

import ru.ecom.diary.ejb.form.protocol.TemplateProtocolForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.userdocument.TemplatePrintDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= TemplatePrintDocument.class)
@Comment("Шаблон печати протокола")
@WebTrail(comment = "Шаблон печати протокола", nameProperties= "id", view="entityView-mis_userDocument.do")
@EntityFormSecurityPrefix("/Policy/Mis/UserDocument")
@Parent(property="template", parentForm=TemplateProtocolForm.class)
public class TemplatePrintDocumentForm extends UserDocumentForm{
	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	@Persist @Required
	public Long getTemplate() {return theTemplate;}
	public void setTemplate(Long aTemplate) {theTemplate = aTemplate;}
	/** Шаблон протокола */
	private Long theTemplate;
	
	/** Тип группы документа */
	@Comment("Тип группы документа")
	@Persist
	public Long getGroupType() {return theGroupType;}
	public void setGroupType(Long aGroupType) {theGroupType = aGroupType;}
	/** Тип группы документа */
	private Long theGroupType;
}
