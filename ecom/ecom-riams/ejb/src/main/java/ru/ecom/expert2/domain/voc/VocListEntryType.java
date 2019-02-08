package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
public class VocListEntryType extends VocBaseEntity {

    /** Актуально */
    @Comment("Актуально")
    public Boolean getIsActual() {return theIsActual;}
    public void setIsActual(Boolean aIsActual) {theIsActual = aIsActual;}
    /** Актуально */
    private Boolean theIsActual = false ;
}
