package ru.ecom.expomc.ejb.domain.omcvoc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "OMC_SPRSMO",schema="SQLUser")
@AIndexes
(
	@AIndex(properties = "fondOkato")
)
@Getter
@Setter
/** Справочник страховых компаний F002 */
public class OmcSprSmo extends OmcAbstractVoc {

	/** СМО код */
	private String smoCode;
	/** areaName */
	private String areaName;
	/** fondOkato */
	private String fondOkato;

	/** Дата начала действия */
	private Date startDate;

	/** Дата окончания действия */
	private Date finishDate;

}
