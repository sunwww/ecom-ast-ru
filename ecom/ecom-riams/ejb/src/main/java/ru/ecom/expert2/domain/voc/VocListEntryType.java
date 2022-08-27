package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.enums.VocListEntryTypeCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class VocListEntryType extends BaseEntity {

    /** Актуально */
    private Boolean isActual = false ;

    private String sqlFileName;

    @Enumerated(EnumType.STRING)
    private VocListEntryTypeCode code;

    private String name;
}
