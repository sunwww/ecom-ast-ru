package ru.ecom.mis.ejb.form.userdocument;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.userdocument.UserDocument;


import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= UserDocument.class)
@Comment("Пользовательский документ")
@WebTrail(comment = "Пользовательский документ", nameProperties= "id", view="entityView-mis_userDocument.do")
@EntityFormSecurityPrefix("/Policy/Mis/UserDocument")

public class UserDocumentForm extends IdEntityForm{

	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;		
	
	/** Название файла */
	@Comment("Название файла")
	@Persist @Required
	public String getFileName() {return theFileName;}
	public void setFileName(String aFileName) {theFileName = aFileName;}
	/** Название файла */
	private String theFileName;
	
	
	/** Тип группы документа */
	@Comment("Тип группы документа")
	@Persist @Required
	public Long getGroupType() {return theGroupType;}
	public void setGroupType(Long aGroupType) {theGroupType = aGroupType;}
	/** Тип группы документа */
	private Long theGroupType;
}
