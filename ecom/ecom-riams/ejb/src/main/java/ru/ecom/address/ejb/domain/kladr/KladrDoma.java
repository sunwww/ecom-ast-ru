package ru.ecom.address.ejb.domain.kladr;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcAbstractVoc;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes (
	@AIndex(properties="code")
)
@Table(schema="SQLUser")
@NamedQuery(name="KladrDoma.findByKladrCode", query="from KladrDoma where code=:code")
public class KladrDoma extends OmcAbstractVoc {
	
	/** Корпус */
	@Comment("Корпус")
	@AFormatFieldSuggest("KORP")
	public String getHouseBuilding() {
		return theHouseBuilding;
	}

	/** Сокращение */
	@Comment("Сокращение")
	@AFormatFieldSuggest("SOCR")
	public String getSocr() {
		return theSocr;
	}

	public void setSocr(String aSocr) {
		theSocr = aSocr;
	}

	public void setHouseBuilding(String aHouseBuilding) {
		theHouseBuilding = aHouseBuilding;
	}

	/** Почтовый индекс */
	@Comment("Почтовый индекс")
	@AFormatFieldSuggest("INDEX")
	public String getPostIndex() {
		return thePostIndex;
	}

	public void setPostIndex(String aPostIndex) {
		thePostIndex = aPostIndex;
	}

	/** ГНИ */
	@Comment("ГНИ")
	@AFormatFieldSuggest("GNINMB")
	public String getGninmb() {
		return theGnimb;
	}

	public void setGninmb(String aGnimb) {
		theGnimb = aGnimb;
	}

	/** UNO */
	@Comment("UNO")
	@AFormatFieldSuggest("UNO")
	public String getUno() {
		return theUno;
	}

	public void setUno(String aUno) {
		theUno = aUno;
	}

	/** ОКАТД */
	@Comment("ОКАТД")
	@AFormatFieldSuggest("OCATD")
	public String getOcatd() {
		return theOcatd;
	}

	public void setOcatd(String aOcatd) {
		theOcatd = aOcatd;
	}

	/** ОКАТД */
	private String theOcatd;
	/** UNO */
	private String theUno;
	/** ГНИ */
	private String theGnimb;
	/** Почтовый индекс */
	private String thePostIndex;
	/** Сокращение */
	private String theSocr;
	/** Корпус */
	private String theHouseBuilding;
}
