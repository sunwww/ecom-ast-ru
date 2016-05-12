package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типа травмы (бытовая, по пути на работу, производственная)
 */
@Entity
@Comment("Справочник типа травмы (бытовая, по пути на работу, производственная)")
@Table(schema="SQLUser")
public class VocTraumaType  extends VocBaseEntity {


}
