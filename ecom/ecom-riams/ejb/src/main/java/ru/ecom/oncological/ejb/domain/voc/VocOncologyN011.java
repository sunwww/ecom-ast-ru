package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N011 - классификатор значений маркеров*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN011 extends VocBaseFederal {
    /** Marker */
    @Comment("Marker")
    public String getMarker() {return theMarker;}
    public void setMarker(String aMarker) {theMarker = aMarker;}
    /** Marker */
    private String theMarker ;

    /** Значение (кратко) */
    @Comment("Значение (кратко)")
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение (кратко) */
    private String theValue ;
}
