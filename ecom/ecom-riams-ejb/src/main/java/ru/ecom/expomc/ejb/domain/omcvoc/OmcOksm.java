package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("OMC OKSM")
@Entity
@Table(name = "OMC_OKSM",schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "alfa2" }) })
public class OmcOksm extends OmcAbstractVoc {
	/** Полное имя */
	@Comment("Полное имя")
	public String getFullName() {return theFullName;}
	public void setFullName(String aFullName) {theFullName = aFullName;}

	/** alfa2 */
	@Comment("alfa2")
	public String getAlfa2() {return theAlfa2;}
	public void setAlfa2(String aAlfa2) {theAlfa2 = aAlfa2;}
	
	/** alfa3 */
	@Comment("alfa3")
	public String getAlfa3() {return theAlfa3;}
	public void setAlfa3(String aAlfa3) {theAlfa3 = aAlfa3;}

	/** СНГ */
	@Comment("СНГ")
	public Boolean getIsCompatriot() {return theIsCompatriot;}
	public void setIsCompatriot(Boolean aIsCompatriot) {theIsCompatriot = aIsCompatriot;}

	/** Текущая страна */
	@Comment("Текущая страна")
	public Boolean getIsCurrent() {return theIsCurrent;}
	public void setIsCurrent(Boolean aIsCurrent) {theIsCurrent = aIsCurrent;}

	/** Текущая страна */
	private Boolean theIsCurrent;
	/** СНГ */
	private Boolean theIsCompatriot;
	/** alfa3 */
	private String theAlfa3;

	/** alfa2 */
	private String theAlfa2;
	/** Полное имя */
	private String theFullName;

}
