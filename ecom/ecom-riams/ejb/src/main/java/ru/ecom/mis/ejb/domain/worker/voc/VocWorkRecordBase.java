package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Основание записи трудовой книжки
 * (статьи трудового кодекса)
 * @author azviagin
 *
 */
@Comment("Основание записи трудовой книжки")
@Entity
@Table(schema="SQLUser")
public class VocWorkRecordBase extends VocBaseEntity{

}
