package ru.ecom.mis.ejb.form.userdocument;

import lombok.Setter;
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
@Setter
public class TemplatePrintDocumentForm extends UserDocumentForm{
	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	@Persist @Required
	public Long getTemplate() {return template;}
	/** Шаблон протокола */
	private Long template;
	
	/** Тип группы документа */
	@Comment("Тип группы документа")
	@Persist
	public Long getGroupType() {return groupType;}
	/** Тип группы документа */
	private Long groupType;
}
