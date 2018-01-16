package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип удостоверения личности
 */
@Entity
@Comment("Тип удостоверения личности")
@Table(schema="SQLUser")
public class VocIdentityCard extends VocIdNameOmcCode {
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
