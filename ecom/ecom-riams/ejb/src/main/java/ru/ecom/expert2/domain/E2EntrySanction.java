package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.expert2.domain.voc.VocE2Sanction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/** Санкции к случаю*/
@Entity
@UnDeletable
public class E2EntrySanction extends BaseEntity {
    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Запись */
    private E2Entry theEntry ;

    /** Санкция */
    @Comment("Санкция")
    @OneToOne
    public VocE2Sanction getSanction() {return theSanction;}
    public void setSanction(VocE2Sanction aSanction) {theSanction = aSanction;}
    /** Санкция */
    private VocE2Sanction theSanction ;

    /** Доп. код */
    @Comment("Доп. код")
    public String getDopCode() {return theDopCode;}
    public void setDopCode(String aDopCode) {theDopCode = aDopCode;}
    /** Доп. код */
    private String theDopCode ;

    public E2EntrySanction(){theIsDeleted=false;}
    public E2EntrySanction (E2Entry aEntry, VocE2Sanction aSanction, String aDopCode, Boolean aIsMainDefect) {
        theEntry=aEntry;theSanction=aSanction;theDopCode=aDopCode;theIsDeleted=false;theIsMainDefect=aIsMainDefect;
    }

    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

    /** Главыный дефект случая */
    @Comment("Главыный дефект случая")
    public Boolean getIsMainDefect() {return theIsMainDefect;}
    public void setIsMainDefect(Boolean aIsMainDefect) {theIsMainDefect = aIsMainDefect;}
    /** Главыный дефект случая */
    private Boolean theIsMainDefect ;

}
