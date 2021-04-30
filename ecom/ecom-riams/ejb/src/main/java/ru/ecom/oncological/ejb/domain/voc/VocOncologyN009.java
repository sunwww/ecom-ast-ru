package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N009 - классификатор соответсвия гистологии диагнозам*/
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocOncologyN009 extends VocBaseFederal {

    private String ds;

    /** Histology */
    private String histology ;

}
