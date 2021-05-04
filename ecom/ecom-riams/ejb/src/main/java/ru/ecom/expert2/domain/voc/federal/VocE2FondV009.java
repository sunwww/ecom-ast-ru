package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Результат обращения  (RSLT)
 */
@Entity
@Getter
@Setter
public class VocE2FondV009  extends VocBaseFederal{
    /** Условия оказания помощи */
    private String usl ;

    /** Коды исхода доп. диспансеризации */
    private String extDispCodes ;
    
  
}
