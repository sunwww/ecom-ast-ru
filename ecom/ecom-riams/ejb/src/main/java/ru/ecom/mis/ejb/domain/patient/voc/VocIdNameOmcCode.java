package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Ид, имя, код ОМС
 */
@MappedSuperclass
@AIndexes({
	@AIndex(properties= {"omcCode"})
    ,   @AIndex(properties= {"deprecated"})
})
@Getter
@Setter
public class VocIdNameOmcCode extends VocIdName {

	/** Устарел */
	private Boolean deprecated;
    /** Код ОМС */
    private String omcCode ;
}
