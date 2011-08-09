package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Первичность документа нетрудоспособности
 * @author azviagin
 *
 */

@Comment("Первичность документа нетрудоспособности")
@Entity
@Table(schema="SQLUser")
public class VocDisabilityDocumentPrimarity extends VocBaseEntity{

}
