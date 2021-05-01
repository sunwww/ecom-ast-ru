package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
/** Поправочные коэффициенты для поликлиники*/
public class VocE2PolyclinicCoefficient extends VocCoefficient {
    /**
     * Код для определения тарифа
     */
    @Comment("Код для определения тарифа")
    @OneToOne
    public VocE2BaseTariffType getTariffType() {return tariffType;}
    private VocE2BaseTariffType tariffType;

    /** Коэффициент для мобильной поликлиники */
    private Boolean isMobilePolyclinic;

    /** Для консультативного приема */
    private Boolean isConsultation;

    /** Для консультативно-диагностического обращений */
    private Boolean isDiagnosticSpo;

}
