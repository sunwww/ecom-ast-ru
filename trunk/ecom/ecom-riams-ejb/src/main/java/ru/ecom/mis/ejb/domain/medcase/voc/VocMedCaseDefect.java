package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов СМО (СМО, СПО, ССО, СЛО, визит, услуга)
 */
@Entity
@Comment("Справочник типов СМО (СМО, СПО, ССО, СЛО, визит, услуга)")
@Table(schema="SQLUser")
public class VocMedCaseDefect extends VocBaseEntity {

}
