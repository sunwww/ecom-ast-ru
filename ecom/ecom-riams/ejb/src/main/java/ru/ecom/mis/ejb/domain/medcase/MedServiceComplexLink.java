package ru.ecom.mis.ejb.domain.medcase;
/**
 * Created by Milamesher on 04.09.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
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
    private MedService theComplexMedService;

    /** Мед. услуга в программе комплексной*/
    @Comment("Мед. услуга в программе комплексной")
    @OneToOne
    public MedService getInnerMedService() {return theInnerMedService;}
    public void setInnerMedService(MedService aInnerMedService) {theInnerMedService = aInnerMedService;}
    private MedService theInnerMedService;

    /** Количество */
    @Comment("Количество")
    public Integer getCountInnerMedService() {
        return theCountInnerMedService;
    }
    public void setCountInnerMedService(Integer aCountInnerMedService) {theCountInnerMedService = aCountInnerMedService;}
    private Integer theCountInnerMedService;

    /** Специальность врача */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getSpeciality() {return theSpeciality;}
    public void setSpeciality(VocE2FondV021 aSpeciality) {theSpeciality = aSpeciality;}
    private VocE2FondV021 theSpeciality ;

    /** Выбрана по умолчанию */
    @Comment("Выбрана по умолчанию")
    @Column(nullable=false, columnDefinition="boolean default false")
    public Boolean getIsDefault() {return theIsDefault;}
    public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
    private Boolean theIsDefault ;

    /** Вес (для сортировки) */
    @Comment("Вес")
    public Integer getWeight() {return theWeight;}
    public void setWeight(Integer aWeight) {theWeight = aWeight;}
    private Integer theWeight ;
}