package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(name = "OMC_SPRSMO",schema="SQLUser")
@AIndexes
(
	@AIndex(unique = false, properties = "fondOkato")
)
public class OmcSprSmo extends OmcAbstractVoc {
	/** fondOkato */
	@Comment("fondOkato")
	public String getFondOkato() {return theFondOkato;}
	public void setFondOkato(String aFondOkato) {theFondOkato = aFondOkato;}

	/** areaName */
	@Comment("areaName")
	public String getAreaName() {return theAreaName;}
	public void setAreaName(String aAreaName) {theAreaName = aAreaName;}

	/** areaName */
	private String theAreaName;
	/** fondOkato */
	private String theFondOkato;

}
