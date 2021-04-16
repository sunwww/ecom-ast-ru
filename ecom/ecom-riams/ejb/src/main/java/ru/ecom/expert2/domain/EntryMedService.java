package ru.ecom.expert2.domain;

import lombok.Data;
import lombok.experimental.Accessors;
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
@Data
@Accessors(prefix = "the")
public class EntryMedService extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    /** Запись */
    private E2Entry theEntry ;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public VocMedService getMedService() {return theMedService;}
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
    private String theDoctorSnils ;

    /** Дата оказания мед. услуги */
    private Date theServiceDate ;

    /** Специальность врача */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getDoctorSpeciality() {return theDoctorSpeciality;}
    private VocE2FondV021 theDoctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @Comment("Диагноз, выявленный при оказании услуги")
    @OneToOne
    public VocIdc10 getMkb() {return theMkb;}
    private VocIdc10 theMkb ;

    /** Цена */
    private BigDecimal theCost ;

    public EntryMedService(){}

    /** Коммент */
    private String theComment ;

    /** Цена */
    private BigDecimal theUet ;

}
