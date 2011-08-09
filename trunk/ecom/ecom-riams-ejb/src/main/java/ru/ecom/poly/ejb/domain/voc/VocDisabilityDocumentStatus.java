package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Статус документа нетрудоспособности
 * @author oegorova
 *
 */

@Comment("Статус документа нетрудоспособности")
@Entity
@Table(schema="SQLUser")
public class VocDisabilityDocumentStatus extends VocBaseEntity{

}
