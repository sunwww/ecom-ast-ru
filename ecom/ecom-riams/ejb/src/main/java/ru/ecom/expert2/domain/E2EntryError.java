package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2EntryError;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

@Entity
public class E2EntryError extends BaseEntity {

    /** Заполнение с ошибкой */
    @Comment("Заполнение с ошибкой")
    @OneToOne
    public E2ListEntry getListEntry() {return theListEntry;}
    public void setListEntry(E2ListEntry aListEntry) {theListEntry = aListEntry;}
    /** Заполнение с ошибкой */
    private E2ListEntry theListEntry ;

    /** Случай */
    @Comment("Случай")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Случай */
    private E2Entry theEntry ;

    /** Ошибка */
    @Comment("Ошибка")
    @OneToOne
    public VocE2EntryError getError() {return theError;}
    public void setError(VocE2EntryError aError) {theError = aError;}
    /** Ошибка */
    private VocE2EntryError theError ;
    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

    @PrePersist
    void onPrePesist(){
        if (theIsDeleted==null) {theIsDeleted=false;}
    }

    /** Код ошибки */
    @Comment("Код ошибки")
    public String getErrorCode() {return theErrorCode;}
    public void setErrorCode(String aErrorCode) {theErrorCode = aErrorCode;}
    /** Код ошибки */
    private String theErrorCode ;
    public E2EntryError(){}
    public E2EntryError(E2Entry aEntry, String aCode) {
        this.theEntry=aEntry;
        this.theErrorCode=aCode;
        this.theListEntry=aEntry.getListEntry();
    }

}
