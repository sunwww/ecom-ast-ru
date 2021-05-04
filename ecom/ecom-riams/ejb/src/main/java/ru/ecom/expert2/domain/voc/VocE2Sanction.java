package ru.ecom.expert2.domain.voc;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;

import javax.persistence.Entity;

/** Причины отказа в оплате медицинской помощи */
@Entity
@Getter
@Setter
public class VocE2Sanction extends VocBaseEntity {

    private String osn;
    private String comment;
}
