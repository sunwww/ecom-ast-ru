package ru.ecom.ejb.domain.simple;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AIndexes
({
@AIndex(properties = {"code"}),
@AIndex(properties = {"name"})
        })
@Getter
@Setter
public class VocIdCodeName extends BaseEntity {
    /** Название */
    @Comment("Наименование")
    @AFormatFieldSuggest({"NAME","DESCRIPTIO", "DISCRIPTIO"
    	,"N_PRVD","NAME_Z","NAZV", "NAZV_PRE"})
    public String getName() { return name ; }

    /** Внешний код */
	@Comment("Внешний код")
	@AFormatFieldSuggest({"PROF_LPU","KOD","VID_LPU","RES_G"
    	,"C_PRVD","AS","KL","Q_Z", "TYPS", "CASUS", "EXPERT"
            , "TCOD", "KOD_FOMS", "RNUMBER","COD","CODE" })
	public String getCode() {
		return code;
	}

	/** Внешний код */
	private String code;
    /** Название */
    private String name ;

}
