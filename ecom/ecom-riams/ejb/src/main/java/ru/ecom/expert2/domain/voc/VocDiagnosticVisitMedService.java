package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class VocDiagnosticVisitMedService extends BaseEntity {

    /** КДП */
    @Comment("КДП")
    @ManyToOne
    public VocDiagnosticVisit getVisit() {return theVisit;}
    public void setVisit(VocDiagnosticVisit aVisit) {theVisit = aVisit;}
    /** КДП */
    private VocDiagnosticVisit theVisit ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    @OneToOne
    public VocMedService getMedService() {return theMedService;}
    public void setMedService(VocMedService aMedService) {theMedService = aMedService;}
    /** Медицинская услуга */
    private VocMedService theMedService ;
}
