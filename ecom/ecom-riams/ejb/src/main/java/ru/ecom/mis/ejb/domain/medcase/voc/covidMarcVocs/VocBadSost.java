package ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник признаков тяжёлого состояния")
@Table(schema = "SQLUser")
public class VocBadSost extends VocBaseEntity {
}