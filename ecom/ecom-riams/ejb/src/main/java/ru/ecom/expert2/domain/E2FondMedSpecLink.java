package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV015;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса
 */
@Entity
public class E2FondMedSpecLink extends BaseEntity {

     /** Медицинская специальность  */
     @Comment("Медицинская специальность")
     @OneToOne
     public VocE2FondV015 getMedSpec() {return theMedSpec;}
     public void setMedSpec(VocE2FondV015 aMedSpec) {theMedSpec = aMedSpec;}
     /** Медицинская специальность  */
    private VocE2FondV015 theMedSpec ;

 /** Рабочая функция специалиста (VocPost) */
    @Comment("Рабочая функция специалиста")
    public String getMedosWorkFunction() {return theMedosWorkFunction;}
    public void setMedosWorkFunction(String aMedosWorkFunction) {theMedosWorkFunction = aMedosWorkFunction;}
    /** Рабочая функция специалиста */
    private String theMedosWorkFunction ;

}
