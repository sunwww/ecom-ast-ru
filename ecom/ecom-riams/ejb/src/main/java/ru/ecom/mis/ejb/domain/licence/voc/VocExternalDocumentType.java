package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Тип внешнего документа")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocExternalDocumentType extends VocBaseEntity{
	/** Медицинские документы */
	private Boolean isMedical;
	
	/** В какую группу входит */
	private String groupName;
}
