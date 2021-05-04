package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник типов полисов ОМС")
@UnDeletable
@Getter
@Setter
public class VocMedPolicyOmc extends VocBaseEntity {

    /**Код в промеде**/
    private String promedCode;

    /** Удаленная запись */
    private Boolean isDeleted ;
}
