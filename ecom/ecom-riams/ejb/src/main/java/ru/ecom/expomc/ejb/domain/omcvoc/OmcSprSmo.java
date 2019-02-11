package ru.ecom.expomc.ejb.domain.omcvoc;

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
/** Справочник страховых компаний F002 */
public class OmcSprSmo extends OmcAbstractVoc {
	/** fondOkato */
	@Comment("fondOkato")
	public String getFondOkato() {return theFondOkato;}
	public void setFondOkato(String aFondOkato) {theFondOkato = aFondOkato;}

	/** areaName */
	@Comment("areaName")
	public String getAreaName() {return theAreaName;}
	public void setAreaName(String aAreaName) {theAreaName = aAreaName;}

	/** СМО код */
	@Comment("СМО код")
	public String getSmoCode() {return theSmoCode;}
	public void setSmoCode(String aSmoCode) {theSmoCode = aSmoCode;}

	/** СМО код */
	private String theSmoCode;
	/** areaName */
	private String theAreaName;
	/** fondOkato */
	private String theFondOkato;

	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала действия */
	private Date theStartDate ;

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания действия */
	private Date theFinishDate ;

}
