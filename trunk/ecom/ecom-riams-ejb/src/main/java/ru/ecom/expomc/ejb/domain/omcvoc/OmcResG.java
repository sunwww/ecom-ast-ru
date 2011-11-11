package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код результата обращения
 */
@Comment("Код результата обращения")
@Entity
@Table(name = "OMC_RES_G",schema="SQLUser")
public class OmcResG extends OmcAbstractVoc {
	/** Codefkl */
	@Comment("Codefkl")
	public String getCodefkl() {return theCodefkl;}
	public void setCodefkl(String aCodefkl) {theCodefkl = aCodefkl;}

	/** Codefds */
	@Comment("Codefds")
	public String getCodefds() {return theCodefds;}
	public void setCodefds(String aCodefds) {theCodefds = aCodefds;}

	/** Codefpl */
	@Comment("Codefpl")
	public String getCodefpl() {return theCodefpl;	}
	public void setCodefpl(String aCodefpl) {theCodefpl = aCodefpl;}

	/** Codefpl */
	private String theCodefpl;
	/** Codefds */
	private String theCodefds;
	/** Codefkl */
	private String theCodefkl;
}
