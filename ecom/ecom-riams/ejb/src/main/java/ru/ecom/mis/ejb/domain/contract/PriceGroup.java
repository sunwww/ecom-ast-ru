package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PriceGroup extends PricePosition {
	/** Сразу открывать */
	@Comment("Сразу открывать")
	public Boolean getIsOnceView() {return theIsOnceView;}
	public void setIsOnceView(Boolean aIsOnceView) {theIsOnceView = aIsOnceView;}
	private Boolean theIsOnceView;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	private MisLpu theLpu;
}