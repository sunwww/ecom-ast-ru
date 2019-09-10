package ru.ecom.mis.ejb.domain.medcase;/**
 * Created by Milamesher on 04.09.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Связь комплексной услуги и услуги, входящей в эту комплексную
 * @author Milamesher
 *
 */
@Comment("Связь комплексной услуги и услуги, входящей в эту комплексную")
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="complexMedService"),
        @AIndex(properties="innerMedService")
})
public class MedServiceComplexLink extends BaseEntity {
    /** Комплексная мед. услуга */
    @Comment("Комплексная мед. услуга")
    @OneToOne
    public MedService getComplexMedService() {return theComplexMedService;}
    public void setComplexMedService(MedService aComplexMedService) {theComplexMedService = aComplexMedService;}

    /** Комплексная мед. услуга */
    private MedService theComplexMedService;

    /** Мед. услуга в программе комплексной*/
    @Comment("Мед. услуга в программе комплексной")
    @OneToOne
    public MedService getInnerMedService() {return theInnerMedService;}
    public void setInnerMedService(MedService aInnerMedService) {theInnerMedService = aInnerMedService;}

    /** Мед. услуга в программе комплексной */
    private MedService theInnerMedService;

    /** Количество */
    @Comment("Количество")
    public Integer getCountInnerMedService() {
        return theCountInnerMedService;
    }
    public void setCountInnerMedService(Integer aCountInnerMedService) {
        theCountInnerMedService = aCountInnerMedService;
    }

    /** Количество */
    private Integer theCountInnerMedService;
}