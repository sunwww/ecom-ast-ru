package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
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

    private E2Entry entry;
    private VocMedService medService;
    private VocE2FondV021 doctorSpeciality;
    private VocIdc10 mkb;
    /**
     * Цена
     */
    private BigDecimal cost;
    /**
     * Коммент
     */
    private String comment;

    /**
     * СНИЛС специалиста, выполневшего услугу
     */
    private String doctorSnils;

    /**
     * Дата оказания мед. услуги
     */
    private Date serviceDate;
    /**
     * Цена
     */
    private BigDecimal uet;

    public EntryMedService(E2Entry aEntry, VocMedService aMedService) {
        entry = aEntry;
        medService = aMedService;
    }

    public EntryMedService(E2Entry aEntry, EntryMedService aMedService) {
        entry = aEntry;
        medService = aMedService.getMedService();
        serviceDate = aMedService.getServiceDate();
        doctorSpeciality = aMedService.getDoctorSpeciality();
        mkb = aMedService.getMkb();
        cost = aMedService.getCost();
    }

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return entry;}

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @OneToOne
    public VocMedService getMedService() {
        return medService;
    }

    public EntryMedService() {
    }

    /**
     * Специальность врача
     */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getDoctorSpeciality() {
        return doctorSpeciality;
    }

    /**
     * Диагноз, выявленный при оказании услуги
     */
    @Comment("Диагноз, выявленный при оказании услуги")
    @OneToOne
    public VocIdc10 getMkb() {return mkb;}

}
