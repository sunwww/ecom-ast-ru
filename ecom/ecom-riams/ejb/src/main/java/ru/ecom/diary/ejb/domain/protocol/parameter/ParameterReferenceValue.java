package ru.ecom.diary.ejb.domain.protocol.parameter;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class ParameterReferenceValue extends BaseEntity {
    /** Параметр */
    @Comment("Параметр")
    @ManyToOne
    public Parameter getParameter() {return theParameter;}
    public void setParameter(Parameter aParameter) {theParameter = aParameter;}
    /** Параметр */
    private Parameter theParameter ;

    /** Пол */
    @Comment("Пол")
    @OneToOne
    public VocSex getSex() {return theSex;}
    public void setSex(VocSex aSex) {theSex = aSex;}
    /** Пол */
    private VocSex theSex ;

    /** Возраст от */
    @Comment("Возраст от")
    public Integer getAgeFrom() {return theAgeFrom;}
    public void setAgeFrom(Integer aAgeFrom) {theAgeFrom = aAgeFrom;}
    /** Возраст от */
    private Integer theAgeFrom ;

    /** Возраст до */
    @Comment("Возраст до")
    public Integer getAgeTo() {return theAgeTo;}
    public void setAgeTo(Integer aAgeTo) {theAgeTo = aAgeTo;}
    /** Возраст до */
    private Integer theAgeTo ;

    /** Мин норма */
    @Comment("Мин норма")
    public BigDecimal getNormaMin() {return theNormaMin;}
    public void setNormaMin(BigDecimal aNormaMin) {theNormaMin = aNormaMin;}
    /** Мин норма */
    private BigDecimal theNormaMin ;
    
    /** Мин максимум */
    @Comment("Мин максимум")
    public BigDecimal getNormaMax() {return theNormaMax;}
    public void setNormaMax(BigDecimal aNormaMax) {theNormaMax = aNormaMax;}
    /** Мин максимум */
    private BigDecimal theNormaMax ;
    
    /** Максимально возможное значение */
    @Comment("Максимально возможное значение")
    public Long getSuperMax() {return theSuperMax;}
    public void setSuperMax(Long aSuperMax) {theSuperMax = aSuperMax;}
    /** Максимально возможное значение */
    private Long theSuperMax ;

    /** Минимально возможное значение */
    @Comment("Минимально возможное значение")
    public Long getSuperMin() {return theSuperMin;}
    public void setSuperMin(Long aSuperMin) {theSuperMin = aSuperMin;}
    /** Минимально возможное значение */
    private Long theSuperMin ;
}