package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Comment("Исследование антител")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocTransfusionResearchAntibodies extends VocBaseEntity {
    /** Не действует */
    @Comment("Не действует")
    @AFormatFieldSuggest({"DISABLE"})
    public Boolean getDisable() {return disable;}
    /** Не действует */
    private Boolean disable;
}
