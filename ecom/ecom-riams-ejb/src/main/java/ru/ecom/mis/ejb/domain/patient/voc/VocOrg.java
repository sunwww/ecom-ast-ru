package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Предприятия
 */
@Entity
@Comment("Предприятие")
@AIndexes({
        @AIndex(unique = false, properties="oldFondNumber"),
        @AIndex(unique = false, properties="fondNumber"),
        @AIndex(unique = false, properties="name", name="vocOrg_name"),
        @AIndex(unique = false, properties = {"oldFondNumber","name","fondNumber"},name="vocOrg_oldFondNumber_name_fondNumber")
        }) 
@Table(schema="SQLUser")
public class VocOrg extends VocIdName {


    /** Старый код ФОНДА */
    @AFormatFieldSuggest("RNUMBER")
    @Comment("Старый код ФОНДА")
    public String getOldFondNumber() { return theOldFondNumber ; }
    public void setOldFondNumber(String aOldFondNumber) { theOldFondNumber = aOldFondNumber ; }

    /** Код ФОНДА */
    @AFormatFieldSuggest("RNUMBER15")
    @Comment("Новый код ФОНДА")
    public String getFondNumber() { return theFondNumber ; }
    public void setFondNumber(String aFondNumber) { theFondNumber = aFondNumber ; }

    /** Код ФОНДА */
    private String theFondNumber ;
    /** Старый код ФОНДА */
    private String theOldFondNumber ;
}
