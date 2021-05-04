package ru.ecom.address.ejb.domain.kladr;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class KladrDoma extends OmcAbstractVoc {
	
	/** Корпус */
	@Comment("Корпус")
	@AFormatFieldSuggest("KORP")
	public String getHouseBuilding() {
		return houseBuilding;
	}

	/** Сокращение */
	@Comment("Сокращение")
	@AFormatFieldSuggest("SOCR")
	public String getSocr() {
		return socr;
	}

	/** Почтовый индекс */
	@Comment("Почтовый индекс")
	@AFormatFieldSuggest("INDEX")
	public String getPostIndex() {
		return postIndex;
	}

	/** ГНИ */
	@Comment("ГНИ")
	@AFormatFieldSuggest("GNINMB")
	public String getGninmb() {
		return gnimb;
	}

	public void setGninmb(String aGnimb) {
		gnimb = aGnimb;
	}

	/** UNO */
	@Comment("UNO")
	@AFormatFieldSuggest("UNO")
	public String getUno() {
		return uno;
	}

	/** ОКАТД */
	@Comment("ОКАТД")
	@AFormatFieldSuggest("OCATD")
	public String getOcatd() {
		return ocatd;
	}

	/** ОКАТД */
	private String ocatd;
	/** UNO */
	private String uno;
	/** ГНИ */
	private String gnimb;
	/** Почтовый индекс */
	private String postIndex;
	/** Сокращение */
	private String socr;
	/** Корпус */
	private String houseBuilding;
}
