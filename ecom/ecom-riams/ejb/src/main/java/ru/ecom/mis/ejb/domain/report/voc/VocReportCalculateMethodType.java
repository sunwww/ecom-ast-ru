package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип метода расчета отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип метода расчета отчетного показателя")
@Entity
@Table(schema="SQLUser")
public class VocReportCalculateMethodType extends VocBaseEntity{

}
