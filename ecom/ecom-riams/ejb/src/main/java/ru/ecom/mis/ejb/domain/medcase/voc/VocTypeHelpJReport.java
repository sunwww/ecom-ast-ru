package ru.ecom.mis.ejb.domain.medcase.voc;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Типы помощи для отчёта jasper по платникам
 * @author Milamesher
 *
 */
@Comment("Типы помощи для отчёта jasper по платникам")
@Entity
@Table(schema="SQLUser")
public class VocTypeHelpJReport extends VocBaseEntity{
}
