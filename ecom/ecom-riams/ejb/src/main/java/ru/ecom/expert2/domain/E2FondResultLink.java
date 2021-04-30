package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV009;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие резальтата госпитализации результату медоса
 */
@Entity
@Getter
@Setter
public class E2FondResultLink extends BaseEntity {

    /**
     * Результат обращения
     */
    @Comment("Результат обращения")
    @OneToOne
    public VocE2FondV009 getResult() {
        return result;
    }

    private VocE2FondV009 result;

    /**
     * Причина выписки
     */
    private String medosReasonDischarge;

    /**
     * Результат госпитализации
     */
    private String medosHospResult;

    /**
     * Исход госпитализации
     */
    private String medosHospOutcome;

    /**
     * Тип коек
     */
    private String bedSubType;


}
