package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник типов полисов ОМС")
public class VocMedPolicyOmc extends VocBaseEntity {

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
