package ru.ecom.expert2.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocKsg;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Позиции группировщика КСГ
 */
@Entity
@Getter
@Setter
@Accessors(prefix = "the")
@AIndexes({
        @AIndex(properties = {"kSGGrouper"})
        , @AIndex(properties = {"mainMKB"})
        , @AIndex(properties = {"anotherMKB"})
        , @AIndex(properties = {"serviceCode"})

})
public class GrouperKSGPosition extends BaseEntity {

    /**
     * Код позиции группировщика
     */
    private String theCode;

    /**
     * Группировщик КСГ
     */
    @OneToOne
    private GrouperKSG theKSGGrouper;

    /**
     * Код МКБ основной
     */
    private String theMainMKB;

    /**
     * Код МКБ сопутствующий
     */
    private String theAnotherMKB;

    /**
     * Код услуги
     */
    private String theServiceCode;

    /**
     * Возраст
     */
    private Integer theAge;

    /**
     * Длительность
     */
    private Integer theDuration;

    /**
     * Значение КСГ
     */
    @OneToOne
    private VocKsg theKSGValue;

    /**
     * Пол (код
     */
    private String theSex;

    /**
     * Дополнительный признак
     */
    private String theDopPriznak;

    /**
     * Код КСГ (для упрощения импорта)
     */
    private String theKsgCode;


}
