package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Milamesher
 * Список лабораторных анализов, которые нужно дополнительно печатать после договора
 */

@Comment("Список лабораторных анализов, которые нужно дополнительно печатать после договора")
@Entity
@Table(schema="SQLUser")
public class VocLabAnalysisExtraPrint extends BaseEntity {
    /**Код услуги */
    @Comment("Код услуги")
    public String getMedService() {return theMedService;}
    public void setMedService(String aMedService) {theMedService = aMedService;}

    /**Код услуги */
    private String theMedService;
}