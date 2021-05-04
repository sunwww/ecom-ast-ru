package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Классификатор способов оплаты медицинской помощи (IDSP)
 */
@Entity
@Getter
@Setter
public class VocE2FondV010 extends VocBaseFederal{
    /** Условия оказания помощи */
    private String usl ;
    
  
}
