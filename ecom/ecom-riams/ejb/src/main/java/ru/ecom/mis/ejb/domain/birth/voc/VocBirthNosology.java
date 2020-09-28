package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 23.12.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Comment("Нозология в акушерстве")
@Table(schema="SQLUser")
public class VocBirthNosology extends VocBaseEntity {
    /** Код МКБ */
    @Comment("Код МКБ")
    @OneToOne
    public VocIdc10 getIDCCode() {return theIDCCode;}
    public void setIDCCode(VocIdc10 aIDCCode) {theIDCCode = aIDCCode;}
    /** Код МКБ */
    private VocIdc10 theIDCCode;
}