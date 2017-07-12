package ru.ecom.mis.ejb.domain.directory;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.directory.voc.VocTypeNumber;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Номер телефона */
@Comment("Номер телефона")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class TelephoneNumber extends BaseEntity{
    
    /** номер телефона */
    @Comment("номер телефона")
    public String getTelNumber() {return theTelNumber;}
    public void setTelNumber(String aTelNumber) {theTelNumber = aTelNumber;}
    private String theTelNumber;
    
    /** Ссылка на запись */
    @Comment("Ссылка на запись")
    @ManyToOne
    public Entry getEntry() {return theEntry;}
    public void setEntry(Entry aEntry) {theEntry = aEntry;}
    private Entry theEntry;
    
    /** Тип номера */
    @Comment("Тип номера")
    @OneToOne
    public VocTypeNumber getTypeNumber() {return theTypeNumber;}
    public void setTypeNumber(VocTypeNumber aTypeNumber) {theTypeNumber = aTypeNumber;}
    private VocTypeNumber theTypeNumber;
}