package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N011 - классификатор значений маркеров*/
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocOncologyN011 extends VocBaseFederal {
    /** Marker */
    private String marker ;

    /** Значение (кратко) */
    private String value ;
}
