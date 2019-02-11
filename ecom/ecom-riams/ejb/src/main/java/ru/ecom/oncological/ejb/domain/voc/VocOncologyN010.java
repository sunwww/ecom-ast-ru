package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N010 - классификатор маркеров*/
@Entity
@Table(schema="SQLUser")
public class VocOncologyN010 extends VocBaseFederal {

    /** Маркер код */
    @Comment("Маркер код")
    public String getMarkerCode() {return theMarkerCode;}
    public void setMarkerCode(String aMarkerCode) {theMarkerCode = aMarkerCode;}
    /** Маркер код */
    private String theMarkerCode ;
}
