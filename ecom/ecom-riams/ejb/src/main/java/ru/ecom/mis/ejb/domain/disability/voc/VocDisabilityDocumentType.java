package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Тип документа нетрудоспособности
 * @author azviagin
 *
 */
@Comment("Тип документа нетрудоспособности")
@Entity
@Table(schema="SQLUser")
public class VocDisabilityDocumentType extends VocBaseEntity{

}
