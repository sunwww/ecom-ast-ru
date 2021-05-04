package ru.ecom.mis.ejb.domain.userdocument;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.userdocument.voc.VocUserDocumentGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пользовательские документы
 */

@Comment("Пользовательские документы")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class UserDocument extends BaseEntity {
	
	/** Название */
	private String name;
	
	/** Название файла */
	private String fileName;

	/** Тип группы документа */
	@Comment("Тип группы документа")
	@OneToOne
	public VocUserDocumentGroup getGroupType() {return groupType;}
	private VocUserDocumentGroup groupType;

}
