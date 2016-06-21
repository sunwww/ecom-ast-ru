package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Список ЛПУ в группе")
@Entity
@Table(schema="SQLUser")
public class LpuGroup extends BaseEntity{
	/** Центр */
	@Comment("Центр")
	public Long getGroupLpu() {return theGroupLpu;}
	public void setGroupLpu(Long aGroupLpu) {	theGroupLpu = aGroupLpu;}
	/** Центр */
	private Long theGroupLpu;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	/** ЛПУ */
	private Long theLpu;
}
