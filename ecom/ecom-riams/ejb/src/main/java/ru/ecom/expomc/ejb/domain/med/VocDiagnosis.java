package ru.ecom.expomc.ejb.domain.med;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
public class VocDiagnosis extends VocBaseEntity {
	/** idc10 */
	@Comment("idc10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** idc10 */
	private VocIdc10 theIdc10;
}
