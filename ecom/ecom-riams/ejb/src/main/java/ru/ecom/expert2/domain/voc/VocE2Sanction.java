package ru.ecom.expert2.domain.voc;


import ru.ecom.ejb.domain.simple.VocBaseEntity;

import javax.persistence.Entity;

/** Причины отказа в оплате медицинской помощи */
@Entity
public class VocE2Sanction extends VocBaseEntity {

    private String theOsn;
    private String theComment;
    public String getOsn() {return theOsn;}
    public void setOsn(String theOsn) { this.theOsn = theOsn;}

    public String getComment() {return theComment;}
    public void setComment(String theComment) {this.theComment = theComment;}



}
