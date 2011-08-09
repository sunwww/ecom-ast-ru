package ru.ecom.ejb.domain.simple;

import javax.persistence.MappedSuperclass;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@MappedSuperclass
@AIndexes
({
@AIndex(unique = false, properties = {"code"}),
@AIndex(unique = false, properties = {"code", "name"}),
@AIndex(unique = false, properties = {"name"})
        })
public class VocIdCodeName extends BaseEntity {
    /** Название */
    @Comment("Наименование")
    @AFormatFieldSuggest({"NAME","DESCRIPTIO", "DISCRIPTIO"
    	,"N_PRVD","NAME_Z","NAZV", "NAZV_PRE"})
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    
    /** Внешний код */
	@Comment("Внешний код")
	@AFormatFieldSuggest({"PROF_LPU","KOD","VID_LPU","RES_G"
    	,"C_PRVD","AS","KL","Q_Z", "TYPS", "CASUS", "EXPERT"
            , "TCOD", "KOD_FOMS", "RNUMBER","COD","CODE" })
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Внешний код */
	private String theCode;
    /** Название */
    private String theName ;

}
