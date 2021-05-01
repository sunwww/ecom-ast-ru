package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV012;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Соответствие исхода случая результату медоса
 */
@Entity
@Getter
@Setter
public class E2FondIshodLink extends BaseEntity {

     /** Исход обращения */
     @OneToOne
     public VocE2FondV012 getIshod() {return ishod;}
    private VocE2FondV012 ishod;

    /** Результат госпитализации */
    private String medosHospResult;

   /** Тип коек*/
   private String bedSubType;

}
