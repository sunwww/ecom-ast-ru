package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип версии
 * @author azviagin
 *
 */

@Comment("Тип версии")
@Entity
@Table(schema="SQLUser")
public class VocReportSetVersionType extends VocBaseEntity{

}
