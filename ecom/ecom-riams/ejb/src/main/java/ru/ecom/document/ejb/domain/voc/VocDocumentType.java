package ru.ecom.document.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип документа
 * (Больничный лист, справка о нетрудоспособности, справка на оружие, 
 * водительская комиссия и т.д)
 * @author azviagin
 *
 */
@Comment("Тип документа")
@Entity
@Table(schema="SQLUser")
public class VocDocumentType extends VocBaseEntity{

}
