package ru.ecom.mis.ejb.domain.medcase;
/**
 * Created by Milamesher on 04.09.2019.
 */

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class MedServiceComplexLink extends BaseEntity {
    /** Комплексная мед. услуга */
    @Comment("Комплексная мед. услуга")
    @OneToOne
    public MedService getComplexMedService() {return complexMedService;}
    private MedService complexMedService;

    /** Мед. услуга в программе комплексной*/
    @Comment("Мед. услуга в программе комплексной")
    @OneToOne
    public MedService getInnerMedService() {return innerMedService;}
    private MedService innerMedService;

    /** Количество */
    private Integer countInnerMedService;

    /** Специальность врача */
    @Comment("Специальность врача")
    @OneToOne
    public VocE2FondV021 getSpeciality() {return speciality;}
    private VocE2FondV021 speciality ;

    /** Выбрана по умолчанию */
    @Comment("Выбрана по умолчанию")
    @Column(nullable=false, columnDefinition="boolean default false")
    public Boolean getIsDefault() {return isDefault;}
    private Boolean isDefault ;

    /** Вес (для сортировки) */
    private Integer weight ;
}