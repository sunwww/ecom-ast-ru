package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.MappedSuperclass;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@MappedSuperclass
public class VocBall extends VocBaseEntity{
	
	/** Балл */
	@Comment("Балл")
	public Integer getBall() {return theBall;}
	public void setBall(Integer aBall) {theBall = aBall;}

	/** Балл */
	private Integer theBall;

}
