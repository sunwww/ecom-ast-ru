package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class PriceGroup extends PricePosition {
	/** Сразу открывать */
	@Comment("Сразу открывать")
	public Boolean getIsOnceView() {return theIsOnceView;}
	public void setIsOnceView(Boolean aIsOnceView) {theIsOnceView = aIsOnceView;}

	/** Сразу открывать */
	private Boolean theIsOnceView;


}
