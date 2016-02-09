package ru.ecom.mis.ejb.domain.userdocument;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.userdocument.voc.VocUserDocumentGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пользовательские документы
 */

@Comment("Пользовательские документы")
@Entity
@Table(schema="SQLUser")
public class UserDocument extends BaseEntity {
	
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;		
	
	/** Название файла */
	@Comment("Название файла")
	public String getFileName() {return theFileName;}
	public void setFileName(String aFileName) {theFileName = aFileName;}
	/** Название файла */
	private String theFileName;

	/** Тип группы документа */
	@Comment("Тип группы документа")
	@OneToOne
	public VocUserDocumentGroup getGroupType() {return theGroupType;}
	public void setGroupType(VocUserDocumentGroup aGroupType) {theGroupType = aGroupType;}
	/** Тип группы документа */
	private VocUserDocumentGroup theGroupType;

}
