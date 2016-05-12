package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник результатов госпитализации
 * 	выздоровление, улучшение, без перемен, ухудшение, здоров, умер
 */
@Entity
@Comment("Справочник результатов госпитализации")
@Table(schema="SQLUser")
public class VocHospitalizationResult extends VocBaseEntity {
	/** Омс код */
	@Comment("Омс код")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}

	/** Код федеральный по круглосуточному стационару */
	@Comment("Код федеральный по круглосуточному стационару")
	public String getCodefkl() {return theCodefkl;}
	public void setCodefkl(String aCodefkl) {theCodefkl = aCodefkl;}

	/** Код федеральный по дневному стационару */
	@Comment("Код федеральный по дневному стационару")
	public String getCodefds() {return theCodefds;}
	public void setCodefds(String aCodefds) {theCodefds = aCodefds;}

	/** Код федеральный по дневному стационару */
	private String theCodefds;
	/** Код федеральный по круглосуточному стационару */
	private String theCodefkl;
	/** Омс код */
	private String theOmcCode;
	
	/** В архиве */
	@Comment("В архиве")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** В архиве */
	private Boolean theIsArchival;
}
