package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("OMC OKSM")
@Entity
@Table(name = "OMC_OKSM",schema="SQLUser")
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

	/** alfa3 */
	private String theAlfa3;

	/** alfa2 */
	private String theAlfa2;
	/** Полное имя */
	private String theFullName;

}
