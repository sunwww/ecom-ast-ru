package ru.ecom.expert2.domain;


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
        @AIndex(properties= {"kSGGrouper"})
        , @AIndex(properties= {"mainMKB"})
        , @AIndex(properties= {"anotherMKB"})
        , @AIndex(properties= {"serviceCode"})

})
public class GrouperKSGPosition extends BaseEntity {

    /** Код позиции группировщика */
    @Comment("Код позиции группировщика")
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код позиции группировщика */
    private String theCode ;

    /** Группировщик КСГ */
    @Comment("Группировщик КСГ")
    @OneToOne
    public GrouperKSG getKSGGrouper() {return theKSGGrouper;}
    public void setKSGGrouper(GrouperKSG aKSGGrouper) {theKSGGrouper = aKSGGrouper;}
    /** Группировщик КСГ */
    private GrouperKSG theKSGGrouper ;

    /** Код МКБ основной */
    @Comment("Код МКБ основной")
    public String getMainMKB() {return theMainMKB;}
    public void setMainMKB(String aMainMKB) {theMainMKB = aMainMKB;}
    /** Код МКБ */
    private String theMainMKB ;

    /** Код МКБ сопутствующий */
    @Comment("Код МКБ сопутствующий")
    public String getAnotherMKB() {return theAnotherMKB;}
    public void setAnotherMKB(String aAnotherMKB) {theAnotherMKB = aAnotherMKB;}
    /** Код МКБ сопутствующий */
    private String theAnotherMKB ;

    /** Код услуги */
    @Comment("Код услуги")
    public String getServiceCode() {return theServiceCode;}
    public void setServiceCode(String aServiceCode) {theServiceCode = aServiceCode;}
    /** Код услуги */
    private String theServiceCode ;

    /** Возраст */
    @Comment("Возраст")
    public Integer getAge() {return theAge;}
    public void setAge(Integer aAge) {theAge = aAge;}
    /** Возраст */
    private Integer theAge ;

    /** Длительность */
    @Comment("Длительность")
    public Integer getDuration() {return theDuration;}
    public void setDuration(Integer aDuration) {theDuration = aDuration;}
    /** Длительность */
    private Integer theDuration ;

    /** Значение КСГ */
    @Comment("Значение КСГ")
    @OneToOne
    public VocKsg getKSGValue() {return theKSGValue;}
    public void setKSGValue(VocKsg aKSGValue) {theKSGValue = aKSGValue;}
    /** Значение КСГ */
    private VocKsg theKSGValue ;

    /** Пол (код */
    @Comment("Пол (код")
    public String getSex() {return theSex;}
    public void setSex(String aSex) {theSex = aSex;}
    /** Пол (код */
    private String theSex ;
    /** Дополнительный признак */
    @Comment("Дополнительный признак")
    public String getDopPriznak() {return theDopPriznak;}
    public void setDopPriznak(String aDopPriznak) {theDopPriznak = aDopPriznak;}
    /** Дополнительный признак */
    private String theDopPriznak ;

}
