package ru.ecom.document.ejb.domain.certificate;


import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Краткий родовый сертификат
 * @author rkurbanov
 *
 */
@Entity
@Table(schema="SQLUser")
public class ShortConfinementCertificate extends Certificate {

    private MedCase medCase;

    /**  Госпитализация */
    @Comment("Госпитализация")
    @OneToOne
    public MedCase getMedCase() {
        return medCase;
    }
    public void setMedCase(MedCase medCase) {
        this.medCase = medCase;
    }
}
