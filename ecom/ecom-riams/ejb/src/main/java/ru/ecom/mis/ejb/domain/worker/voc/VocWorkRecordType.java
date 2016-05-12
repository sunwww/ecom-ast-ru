package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип записи трудовой книжки
 * @author azviagin
 *
 */
@Comment("Тип записи трудовой книжки")
@Entity
@Table(schema="SQLUser")
public class VocWorkRecordType extends VocBaseEntity{

}
