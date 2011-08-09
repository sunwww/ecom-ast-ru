package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип источника отчета
 * @author azviagin
 *
 */

@Comment("Тип источника отчета")
@Entity
@Table(schema="SQLUser")
public class VocReportSourceType extends VocBaseEntity{

}
