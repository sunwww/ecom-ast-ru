package ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник тяжестей заболевания в оценке ковида")
@Table(schema = "SQLUser")
@Getter
@Setter
public class VocSost extends VocBaseEntity {
    /** Код ОМС */
    private String omcCode;
}