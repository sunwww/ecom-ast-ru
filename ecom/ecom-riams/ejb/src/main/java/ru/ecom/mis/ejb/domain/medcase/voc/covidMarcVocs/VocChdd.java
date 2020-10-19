package ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник частоты дыхательных движений в оценке ковида")
@Table(schema = "SQLUser")
public class VocChdd extends VocBaseEntity {
}