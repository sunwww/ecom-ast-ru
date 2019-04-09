package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Comment("Справочник. Профиль коек")
@Entity
@Table(schema="SQLUser")
public class VocBedType extends VocBaseEntity{
	/** ОМС код */
	@Comment("Омс код")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}

	
	/** Код федеральный по справочнику V020 */
	@Comment("Код федеральный по справочнику V020")
	public String getCodeF() {return theCodeF;}
	public void setCodeF(String aCodeF) {theCodeF = aCodeF;}

	/** Код федеральный стационар (для детей) */
	@Comment("Код федеральный стационар (для детей)")
	public String getCodeFC() {return theCodeFC;}
	public void setCodeFC(String aCodeFC) {theCodeFC = aCodeFC;}

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@OneToOne
	public VocE2MedHelpProfile getMedHelpProfile() {return theMedHelpProfile;}
	public void setMedHelpProfile(VocE2MedHelpProfile aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}
	/** Профиль медицинской помощи */
	private VocE2MedHelpProfile theMedHelpProfile ;

	/** Код федеральный стационар (для детей) */
	private String theCodeFC;
	/** Код федеральный по справочнику V020 */
	private String theCodeF;
	/** ОМС код */
	private String theOmcCode;
}