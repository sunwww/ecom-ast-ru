package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV009;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV012;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса
 */
@Entity
@Getter
@Setter
@Accessors(prefix = "the")
public class E2FondIshodLink extends BaseEntity {

     /** Исход обращения */
     @OneToOne
    private VocE2FondV012 theIshod ;

    /** Результат госпитализации */
    private String theMedosHospResult ;

   /** Тип коек*/
   private String theBedSubType ;

}
