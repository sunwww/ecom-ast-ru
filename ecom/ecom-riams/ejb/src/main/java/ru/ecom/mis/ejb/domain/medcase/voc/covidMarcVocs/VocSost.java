package ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник тяжестей заболевания в оценке ковида")
@Table(schema = "SQLUser")
public class VocSost extends VocBaseEntity {
    /** Код ОМС */
    @Comment("Код ОМС")
    public String getOmcCode() {return theOmcCode;}
    public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}
    /** Код ОМС */
    private String theOmcCode;
}