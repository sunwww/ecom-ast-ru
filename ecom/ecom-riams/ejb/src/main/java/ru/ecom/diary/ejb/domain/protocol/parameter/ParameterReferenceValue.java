package ru.ecom.diary.ejb.domain.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class ParameterReferenceValue extends BaseEntity {
    /** Параметр */
    @Comment("Параметр")
    @ManyToOne
    public Parameter getParameter() {return parameter;}
    /** Параметр */
    private Parameter parameter ;

    /** Пол */
    @Comment("Пол")
    @OneToOne
    public VocSex getSex() {return sex;}
    /** Пол */
    private VocSex sex ;

    /** Возраст от */
    private Integer ageFrom ;

    /** Возраст до */
    private Integer ageTo ;

    /** Мин норма */
    private BigDecimal normaMin ;
    
    /** Мин максимум */
    private BigDecimal normaMax ;
    
    /** Максимально возможное значение */
    private Long superMax ;

    /** Минимально возможное значение */
    private Long superMin ;
}