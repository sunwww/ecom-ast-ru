package ru.ecom.queue.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**Виды очередей*/
@Entity
public class VocQueue extends VocBaseEntity {
    /** Не используется */
    @Comment("Не используется")
    public Boolean getIsArchival() {return theIsArchival;}
    public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
    /** Не используется */
    private Boolean theIsArchival=false ;

    /** Префикс для талона */
    @Comment("Префикс для талона")
    public String getPrefix() {return thePrefix;}
    public void setPrefix(String aPrefix) {thePrefix = aPrefix;}
    /** Префикс для талона */
    private String thePrefix ;
}
