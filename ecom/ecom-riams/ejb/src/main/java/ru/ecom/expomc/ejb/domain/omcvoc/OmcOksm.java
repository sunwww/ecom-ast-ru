package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("OMC OKSM")
@Entity
@Table(name = "OMC_OKSM",schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "alfa2" }) })
@Getter
@Setter
public class OmcOksm extends OmcAbstractVoc {

	/** Текущая страна */
	private Boolean isCurrent;
	/** СНГ */
	private Boolean isCompatriot;
	/** alfa3 */
	private String alfa3;

	/** alfa2 */
	private String alfa2;
	/** Полное имя */
	private String fullName;

}
