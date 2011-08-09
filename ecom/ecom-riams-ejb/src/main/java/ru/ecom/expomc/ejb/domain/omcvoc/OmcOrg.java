package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Предприятия
 */
@Comment("Предприятия")
@Entity
@Table(name = "OMC_ORG",schema="SQLUser")
@AIndexes
        ({
        @AIndex(unique = false, properties = {"time", "code"}),
        @AIndex(unique = false, properties = {"time", "name"}),
        @AIndex(unique = false, properties = {"time", "code", "name"})
                })
public class OmcOrg extends OmcAbstractVoc {
    /**
     * Новый код
     */
    @Comment("Новый код")
    @AFormatFieldSuggest("RNUMBER15")
    public String getNewCode() {
        return theNewCode;
    }

    public void setNewCode(String aNewCode) {
        theNewCode = aNewCode;
    }

    /**
     * Новый код
     */
    private String theNewCode;
}
