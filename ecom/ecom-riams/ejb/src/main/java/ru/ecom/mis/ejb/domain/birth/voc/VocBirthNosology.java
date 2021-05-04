package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 23.12.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Comment("Нозология в акушерстве")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocBirthNosology extends VocBaseEntity {
    /** Код МКБ */
    @Comment("Код МКБ")
    @OneToOne
    public VocIdc10 getIDCCode() {return iDCCode;}
    private VocIdc10 iDCCode;
}