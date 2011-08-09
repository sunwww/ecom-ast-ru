




package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник первичности диагноза (первичный, повторный)
 */
@Entity
@Comment("Справочник первичности диагноза (первичный, повторный)")
@Table(schema="SQLUser")
public class VocPrimaryDiagnosis extends VocBaseEntity {
}
