package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса
 */
@Entity
public class E2FondMedSpecLink extends BaseEntity {

    /** Медицинская специальность */
    @Comment("Медицинская специальность")
    @OneToOne
    public VocE2FondV021 getSpeciality() {return theSpeciality;}
    public void setSpeciality(VocE2FondV021 aSpeciality) {theSpeciality = aSpeciality;}
    /** Медицинская специальность */
    private VocE2FondV021 theSpeciality ;

 /** Рабочая функция специалиста (VocPost) */
    @Comment("Рабочая функция специалиста")
    public String getMedosWorkFunction() {return theMedosWorkFunction;}
    public void setMedosWorkFunction(String aMedosWorkFunction) {theMedosWorkFunction = aMedosWorkFunction;}
    /** Рабочая функция специалиста */
    private String theMedosWorkFunction ;

}
