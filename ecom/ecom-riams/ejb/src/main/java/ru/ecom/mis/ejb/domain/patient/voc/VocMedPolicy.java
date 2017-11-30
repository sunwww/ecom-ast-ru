package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocMedPolicy extends VocBaseEntity{

    /**Код в промеде**/
    private String promedCode;
    @Comment("Код в промеде")
    public String getPromedCode() {
        return promedCode;
    }
    public void setPromedCode(String promedCode) {
        this.promedCode = promedCode;
    }
}
