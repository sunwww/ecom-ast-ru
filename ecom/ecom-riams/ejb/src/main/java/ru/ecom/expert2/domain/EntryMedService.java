package ru.ecom.expert2.domain;

import lombok.Data;
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
@AIndexes({
        @AIndex(properties = {"entry"})
})
@Getter
@Setter
public class EntryMedService extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return entry;}
    private E2Entry entry ;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public VocMedService getMedService() {return medService;}
    private VocMedService medService ;

    public EntryMedService(E2Entry aEntry, VocMedService aMedService) {
        entry=aEntry;
        medService=aMedService;
    }
    public EntryMedService(E2Entry aEntry, EntryMedService aMedService) {
        entry=aEntry;
        medService=aMedService.getMedService();
        serviceDate =aMedService.getServiceDate();
        doctorSpeciality=aMedService.getDoctorSpeciality();
        mkb=aMedService.getMkb();
        cost = aMedService.getCost();
    }

    /** СНИЛС специалиста, выполневшего услугу */
    private String doctorSnils;

    /** Дата оказания мед. услуги */
    private Date serviceDate;

    /** Специальность врача */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getDoctorSpeciality() {return doctorSpeciality;}
    private VocE2FondV021 doctorSpeciality ;

    /** Диагноз, выявленный при оказании услуги */
    @Comment("Диагноз, выявленный при оказании услуги")
    @OneToOne
    public VocIdc10 getMkb() {return mkb;}
    private VocIdc10 mkb ;

    /** Цена */
    private BigDecimal cost ;

    public EntryMedService(){}

    /** Коммент */
    private String comment ;

    /** Цена */
    private BigDecimal uet ;

}
