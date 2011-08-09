package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.MappedSuperclass;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Ид, имя, код ОМС
 */
@MappedSuperclass
@AIndexes({
	@AIndex(unique = false, properties= {"omcCode"})
    ,   @AIndex(unique = false, properties= {"deprecated"})
})

public class VocIdNameOmcCode extends VocIdName {
    /** Код ОМС */
    @Comment("Код в ОМС")
    public String getOmcCode() { return theOmcCode ; }
    public void setOmcCode(String aOmcCode) { theOmcCode = aOmcCode ; }

	/** Устарел */
	@Comment("Устарел")
	public Boolean getDeprecated() {return theDeprecated;}
	public void setDeprecated(Boolean aDeprecated) {theDeprecated = aDeprecated;}

	/** Устарел */
	private Boolean theDeprecated;
    /** Код ОМС */
    private String theOmcCode ;
}
