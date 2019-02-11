package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Справочник настроек экспертизы
 */
@Entity
@UnDeletable
public class Expert2Config extends VocBaseEntity {

    /** Значение параметра */
    @Comment("Значение параметра")
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение параметра */
    private String theValue ;

    @Comment("Удалено")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удалено */
    private Boolean theIsDeleted ;
}
