package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Getter
@Setter
@Accessors(prefix = "the")
@AIndexes({
        @AIndex(properties = {"entry"})})
public class E2CoefficientPatientDifficultyEntryLink extends BaseEntity{

    /** Запись */
    @ManyToOne
    private E2Entry theEntry ;

    /** Сложность лечения */
    @OneToOne
    private VocE2CoefficientPatientDifficulty theDifficulty ;

    /** Расчитанное значение коэффициента */
    @Column( precision = 15, scale = 5)
    private BigDecimal theValue ;
}
