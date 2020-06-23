package ru.ecom.document.ejb.domain.certificate;


import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Краткий родовый сертификат
 * @author rkurbanov
 *
 */
@Entity
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
