package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2CoefficientPatientDifficulty;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class E2CoefficientPatientDifficultyEntryLink extends BaseEntity{

    /** Запись */
    @Comment("Запись")
    @ManyToOne(cascade = CascadeType.ALL)
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Запись */
    private E2Entry theEntry ;

    /** Сложность лечения */
    @Comment("Сложность лечения")
    @OneToOne
    public VocE2CoefficientPatientDifficulty getDifficulty() {return theDifficulty;}
    public void setDifficulty(VocE2CoefficientPatientDifficulty aDifficulty) {theDifficulty = aDifficulty;}
    /** Сложность лечения */
    private VocE2CoefficientPatientDifficulty theDifficulty ;

    /** Расчитанное значение коэффициента */
    @Comment("Расчитанное значение коэффициента")
    @Column( precision = 15, scale = 5)
    public BigDecimal getValue() {return theValue;}
    public void setValue(BigDecimal aValue) {theValue = aValue;}
    /** Расчитанное значение коэффициента */
    private BigDecimal theValue ;
}
