package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.VocE2CoefficientPatientDifficulty;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@AIndexes({
        @AIndex(properties = {"entry"})
})
@Getter
@Setter
public class E2CoefficientPatientDifficultyEntryLink extends BaseEntity{

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return entry;}
    private E2Entry entry;

    /** Сложность лечения */
    @Comment("Сложность лечения")
    @OneToOne
    public VocE2CoefficientPatientDifficulty getDifficulty() {return difficulty;}
    private VocE2CoefficientPatientDifficulty difficulty;

    /** Расчитанное значение коэффициента */
    @Comment("Расчитанное значение коэффициента")
    @Column( precision = 15, scale = 5)
    public BigDecimal getValue() {return value;}
    private BigDecimal value;
}
