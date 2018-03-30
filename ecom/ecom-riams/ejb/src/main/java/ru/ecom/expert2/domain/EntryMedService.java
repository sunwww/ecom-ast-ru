package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV015;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
public class EntryMedService extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Запись */
    private E2Entry theEntry ;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public VocMedService getMedService() {return theMedService;}
    public void setMedService(VocMedService aMedService) {theMedService = aMedService;}
    /** Мед. услуга */
    private VocMedService theMedService ;

    public EntryMedService(E2Entry aEntry, VocMedService aMedService) {
        theEntry=aEntry;theMedService=aMedService;
    }
    public EntryMedService(E2Entry aEntry, EntryMedService aMedService) {
        theEntry=aEntry;theMedService=aMedService.getMedService();
    }

    /** Дата оказания мед. услуги */
    @Comment("Дата оказания мед. услуги")
    public Date getServiceDate() {return theServiceDate;}
    public void setServiceDate(Date aServiceDate) {theServiceDate = aServiceDate;}
    /** Дата оказания мед. услуги */
    private Date theServiceDate ;

    /** Специальность врача, оказавшего услугу */
    @Comment("Специальность врача, оказавшего услугу")
    @OneToOne
    public VocE2FondV015 getSpeciality() {return theSpeciality;}
    public void setSpeciality(VocE2FondV015 aSpeciality) {theSpeciality = aSpeciality;}
    /** Специальность врача, оказавшего услугу */
    private VocE2FondV015 theSpeciality ;
    public EntryMedService(){}


}
