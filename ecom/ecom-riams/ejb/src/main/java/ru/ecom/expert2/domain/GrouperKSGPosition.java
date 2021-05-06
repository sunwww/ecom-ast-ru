package ru.ecom.expert2.domain;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Позиции группировщика КСГ
 */
@Entity
@AIndexes({
        @AIndex(properties = {"kSGGrouper"})
        , @AIndex(properties = {"mainMKB"})
        , @AIndex(properties = {"anotherMKB"})
        , @AIndex(properties = {"serviceCode"})

})
@Getter
@Setter
public class GrouperKSGPosition extends BaseEntity {

    /** Код позиции группировщика */
    private String code ;

    /**
     * Группировщик КСГ
     */
    @Comment("Группировщик КСГ")
    @OneToOne
    public GrouperKSG getKsgGrouper() {return ksgGrouper;}
    private GrouperKSG ksgGrouper ;

    /**
     * Код МКБ основной
     */
    @Comment("Код МКБ основной")
    public String getMainMKB() {return mainMKB;}
    private String mainMKB ;

    /** Код МКБ сопутствующий */
    @Comment("Код МКБ сопутствующий")
    public String getAnotherMKB() {return anotherMKB;}
    private String anotherMKB ;

    /** Код услуги */
    @Comment("Код услуги")
    public String getServiceCode() {return serviceCode;}
    private String serviceCode ;

    /** Возраст */
    private Integer age ;

    /** Длительность */
    private Integer duration ;

    /** Значение КСГ */
    @Comment("Значение КСГ")
    @OneToOne
    public VocKsg getKsgValue() {return ksgValue;}
    private VocKsg ksgValue;

    /** Пол (код */
    private String sex ;
    /** Дополнительный признак */
    private String dopPriznak ;

    /** Код КСГ (для упрощения импорта) */
    private String ksgCode;


}
