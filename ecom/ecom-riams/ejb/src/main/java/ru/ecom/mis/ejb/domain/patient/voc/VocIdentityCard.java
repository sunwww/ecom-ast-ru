package ru.ecom.mis.ejb.domain.patient.voc;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Тип удостоверения личности
 */
@Entity
@Comment("Тип удостоверения личности")
@Table(schema="SQLUser")
@NamedQuery(name="VocIdentityCard.findByOmcCode", query="from VocIdentityCard where omcCode=:code")

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
