package ru.ecom.mis.ejb.domain.medcase.voc;

/**
 * Приоритет диагноза
 * Основной, конкурирующий, сопутствующий, осложнение
 */
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Приоритет диагноза")
@Table(schema="SQLUser")
public class VocPriorityDiagnosis extends VocBaseEntity{
}
