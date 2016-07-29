package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Тип внешнего документа")
@Table(schema="SQLUser")
public class VocExternalDocumentType extends VocBaseEntity{
	/** Медицинские документы */
	@Comment("Медицинские документы")
	public Boolean getIsMedical() {return theIsMedical;}
	public void setIsMedical(Boolean aIsMedical) {theIsMedical = aIsMedical;}

	/** Медицинские документы */
	private Boolean theIsMedical;
	
	/** В какую группу входит */
	@Comment("В какую группу входит")
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}
	/** В какую группу входит */
	private String theGroupName;
}
