package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Accessors(prefix = "the")
public class E2FondResultLink extends BaseEntity {

     /** Результат обращения */
     @OneToOne
    private VocE2FondV009 theResult ;

    /** Причина выписки */
    private String theMedosReasonDischarge ;

    /** Результат госпитализации */
    private String theMedosHospResult ;

   /** Исход госпитализации */
   private String theMedosHospOutcome ;

   /** Тип коек*/
   private String theBedSubType ;



}
