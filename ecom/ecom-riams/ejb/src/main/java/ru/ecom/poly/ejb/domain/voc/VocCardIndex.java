package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Статус документа нетрудоспособности")
@Entity
@Table(schema="SQLUser")
public class VocCardIndex extends VocBaseEntity{
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

    /** ЛПУ */
	private MisLpu theLpu;
}
