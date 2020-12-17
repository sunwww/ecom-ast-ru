package ru.ecom.mis.ejb.domain.disability.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Причина аннулирования ЭЛН
 * @author milamesher
 *
 */
@Comment("Причина аннулирования ЭЛН")
@Entity
@Table(schema="SQLUser")
public class VocAnnulReason extends VocBaseEntity {
    /** Не действует */
    @Comment("Не действует")
    @AFormatFieldSuggest({"DISABLE"})
    public Boolean getDisable() {return theDisable;}
    public void setDisable(Boolean aDisable) {theDisable = aDisable;}
    /** Не действует */
    private Boolean theDisable;
}