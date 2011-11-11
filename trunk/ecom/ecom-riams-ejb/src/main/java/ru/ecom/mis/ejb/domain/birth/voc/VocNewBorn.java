package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник новорожденный: одноплодный, 1 из многоплодных, 2 из многоплодных, 3..4...5")
@Entity
public class VocNewBorn extends VocBaseEntity {
	/** Многоплодный */
	@Comment("Многоплодный")
	public Boolean getIsPolycarpous() {return theIsPolycarpous;}
	public void setIsPolycarpous(Boolean aIsPolycarpous) {theIsPolycarpous = aIsPolycarpous;}

	/** Какой по счету ребенок */
	@Comment("Какой по счету ребенок")
	public String getBirthOrder() {return theBirthOrder;}
	public void setBirthOrder(String aBirthOrder) {theBirthOrder = aBirthOrder;}

	/** Какой по счету ребенок */
	private String theBirthOrder;
	/** Многоплодный */
	private Boolean theIsPolycarpous;
}
