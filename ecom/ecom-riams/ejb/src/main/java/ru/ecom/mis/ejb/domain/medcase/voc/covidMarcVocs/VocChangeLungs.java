package ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник изменений в лёгких в оценке ковида")
@Table(schema = "SQLUser")
public class VocChangeLungs extends VocBaseEntity {
}