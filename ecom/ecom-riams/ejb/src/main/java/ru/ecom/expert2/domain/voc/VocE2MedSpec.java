package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Справочник медицинский специальностей (V015)
 */
@Entity
public class VocE2MedSpec extends VocBaseEntity{
    /** Неактуально */
    @Comment("Неактуально")
    public Boolean getNoActuality() {return theNoActuality;}
    public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
    /** Неактуально */
    private Boolean theNoActuality ;

}
