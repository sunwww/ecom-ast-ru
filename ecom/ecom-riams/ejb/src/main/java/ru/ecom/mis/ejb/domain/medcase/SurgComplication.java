package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocComplication;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Comment("Осложнения при операции")
@Table(schema="SQLUser")
public class SurgComplication extends BaseEntity {
    @Comment("Дата осложнения")
    public Date getDateComp() {
        return dateComp;
    }
    public void setDateComp(Date dateComp) {
        this.dateComp = dateComp;
    }
    /** Дата осложнения */
    private Date dateComp;

    @Comment("Осложнение")
    @OneToOne
    public VocComplication getComplication() {
        return theComplication;
    }
    public void setComplication(VocComplication aComplication) {
        this.theComplication = aComplication;
    }

    /**Осложнение*/
    private VocComplication theComplication;

    @Comment("Причина осложнения текст")
    public String getCompReasonString() {
        return theCompReasonString;
    }
    public void setCompReasonString(String aCompReasonString) {
        this.theCompReasonString = aCompReasonString;
    }

    /**Причина осложнения текст*/
    private String theCompReasonString;

    @Comment("Осложнение текст")
    public String getComplicationString() {
        return theComplicationString;
    }
    public void setComplicationString(String aComplicationString) {
        this.theComplicationString = aComplicationString;
    }

    /**Осложнение текст*/
    private String theComplicationString;

    /** Хирургическая операция */
    @Comment("Хирургическая операция")
    @ManyToOne(fetch = FetchType.LAZY)
    public SurgicalOperation getSurgicalOperation() {return theSurgicalOperation;}
    public void setSurgicalOperation(SurgicalOperation aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}
    private SurgicalOperation theSurgicalOperation;
}