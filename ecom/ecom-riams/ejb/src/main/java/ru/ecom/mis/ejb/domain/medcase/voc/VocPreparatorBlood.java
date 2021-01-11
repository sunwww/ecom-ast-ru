package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocPreparatorBlood extends VocBaseEntity {
    /** Не действует */
    @Comment("Не действует")
    @AFormatFieldSuggest({"DISABLE"})
    public Boolean getDisable() {return theDisable;}
    public void setDisable(Boolean aDisable) {theDisable = aDisable;}
    /** Не действует */
    private Boolean theDisable;
}
