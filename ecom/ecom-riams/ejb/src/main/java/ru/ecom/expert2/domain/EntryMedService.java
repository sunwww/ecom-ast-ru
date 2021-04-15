package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Accessors(prefix = "the")
@AIndexes({
        @AIndex(properties = {"entry"})
})
public class EntryMedService extends BaseEntity {

    /** Запись */
    @ManyToOne
    private E2Entry theEntry ;

    /** Мед. услуга */
    @OneToOne
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
    @OneToOne
    private VocE2FondV021 theDoctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @OneToOne
    private VocIdc10 theMkb ;

    /** Цена */
    private BigDecimal theCost ;

    public EntryMedService(){}

    /** Коммент */
    private String theComment ;

}
