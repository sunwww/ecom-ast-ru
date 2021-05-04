package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class VocListEntryType extends VocBaseEntity {

    /** Актуально */
    private Boolean isActual = false ;
}
