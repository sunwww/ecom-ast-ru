package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N012 - классификатор соответствия маркеров диагнозам*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN012 extends VocBaseFederal {

    private String ds;

    /** Marker */
    @Comment("Marker")
    public String getMarker() {return theMarker;}
    public void setMarker(String aMarker) {theMarker = aMarker;}
    /** Marker */
    private String theMarker ;

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }
}
