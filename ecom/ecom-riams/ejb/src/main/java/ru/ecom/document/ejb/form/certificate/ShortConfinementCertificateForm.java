package ru.ecom.document.ejb.form.certificate;

import ru.ecom.document.ejb.domain.certificate.ShortConfinementCertificate;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/** Created by rkurbanov on 26.07.2018. */

@EntityForm
@EntityFormPersistance(clazz=ShortConfinementCertificate.class)
@Comment("Краткий родовый сертификат")
@WebTrail(comment="Краткий родовый сертификат",  nameProperties="id", view="entityParentView-preg_shortConfCertificate.do" )
@Parent(property="medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy")
public class ShortConfinementCertificateForm extends CertificateForm {

    private Long medCase;

    /** Госпитализация */
    @Comment("Госпитализация")
    @Persist
    public Long getMedCase() {
        return medCase;
    }
    public void setMedCase(Long medCase) {
        this.medCase = medCase;
    }
}
