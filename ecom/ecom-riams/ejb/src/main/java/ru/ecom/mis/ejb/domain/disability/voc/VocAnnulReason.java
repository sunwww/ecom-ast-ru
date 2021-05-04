package ru.ecom.mis.ejb.domain.disability.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocAnnulReason extends VocBaseEntity {
    /** Не действует */
    @Comment("Не действует")
    @AFormatFieldSuggest({"DISABLE"})
    public Boolean getDisable() {return disable;}
    private Boolean disable;
}