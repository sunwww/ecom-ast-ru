package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник приемных отделений")
@Table(schema="SQLUser")
public class VocPigeonHole extends VocBaseEntity{
	/** Разделяются номера стат.карт на экстренные плановые */
	@Comment("Разделяются номера стат.карт на экстренные плановые")
	public Boolean getIsStatStubEmerPlan() {return theIsStatStubEmerPlan;}
	public void setIsStatStubEmerPlan(Boolean aIsStatStubEmerPlan) {theIsStatStubEmerPlan = aIsStatStubEmerPlan;}

	/** Разделяются номера стат.карт на экстренные плановые */
	private Boolean theIsStatStubEmerPlan;

	
	/** Префикс стат.карт до */
	@Comment("Префикс стат.карт до")
	public String getPrefixBefore() {return thePrefixBefore;}
	public void setPrefixBefore(String aPrefixBefore) {thePrefixBefore = aPrefixBefore;}

	/** Префикс стат.карт после */
	@Comment("Префикс стат.карт после")
	public String getPrefixAfter() {return thePrefixAfter;}
	public void setPrefixAfter(String aPrefixAfter) {thePrefixAfter = aPrefixAfter;}

	/** Префикс стат.карт после */
	private String thePrefixAfter;
	/** Префикс стат.карт до */
	private String thePrefixBefore;

}
