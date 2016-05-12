package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Вид лечебно-профилактического учреждения
 */
@Comment("Вид лечебно-профилактического учреждения ")
@Entity
@Table(name = "OMC_VID_LPU",schema="SQLUser")
public class OmcVidLpu extends OmcAbstractVoc {

    /** Какой-то код */
    @Comment("Какой-то код")
    public String getQUt() { return theQUt ; }
    public void setQUt(String aQUt) { theQUt = aQUt ; }

    /** Какой-то код */
    private String theQUt ;
}
