package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@AIndexes({
        @AIndex(properties = {"entry"})
})
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
        theEntry=aEntry;
        theMedService=aMedService;
    }
    public EntryMedService(E2Entry aEntry, EntryMedService aMedService) {
        theEntry=aEntry;
        theMedService=aMedService.getMedService();
        theServiceDate=aMedService.getServiceDate();
        theDoctorSpeciality=aMedService.getDoctorSpeciality();
        theMkb=aMedService.getMkb();
        theCost = aMedService.getCost();
    }

    /** СНИЛС специалиста, выполневшего услугу */
    @Comment("СНИЛС специалиста, выполневшего услугу")
    public String getDoctorSnils() {return theDoctorSnils;}
    public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}
    private String theDoctorSnils ;

    /** Дата оказания мед. услуги */
    @Comment("Дата оказания мед. услуги")
    public Date getServiceDate() {return theServiceDate;}
    public void setServiceDate(Date aServiceDate) {theServiceDate = aServiceDate;}
    private Date theServiceDate ;

    /** Специальность врача */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getDoctorSpeciality() {return theDoctorSpeciality;}
    public void setDoctorSpeciality(VocE2FondV021 aDoctorSpeciality) {theDoctorSpeciality = aDoctorSpeciality;}
    private VocE2FondV021 theDoctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @Comment("Диагноз, выявленный при оказании услуги")
    @OneToOne
    public VocIdc10 getMkb() {return theMkb;}
    public void setMkb(VocIdc10 aMkb) {theMkb = aMkb;}
    private VocIdc10 theMkb ;

    /** Цена */
    @Comment("Цена")
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    /** Цена */
    private BigDecimal theCost ;

    public EntryMedService(){}

    /** Коммент */
    @Comment("Коммент")
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    private String theComment ;

}
