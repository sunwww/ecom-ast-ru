package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.voc.VocKladr;
import ru.medos.ejb.persdata.domain.voc.VocState;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Адрес
	 */
	@Comment("Адрес")
@Entity
@Table(schema="SQLUser")
public class Address extends BaseEntity{
	/**
	 * Регион
	 */
	@Comment("Регион")
	
	public String getProvince() {
		return theProvince;
	}
	public void setProvince(String aProvince) {
		theProvince = aProvince;
	}
	/**
	 * Регион
	 */
	private String theProvince;
	/**
	 * Район
	 */
	@Comment("Район")
	
	public String getRegion() {
		return theRegion;
	}
	public void setRegion(String aRegion) {
		theRegion = aRegion;
	}
	/**
	 * Район
	 */
	private String theRegion;
	/**
	 * Населенный пункт
	 */
	@Comment("Населенный пункт")
	
	public String getSettlement() {
		return theSettlement;
	}
	public void setSettlement(String aSettlement) {
		theSettlement = aSettlement;
	}
	/**
	 * Населенный пункт
	 */
	private String theSettlement;
	/**
	 * Улица
	 */
	@Comment("Улица")
	
	public String getStreet() {
		return theStreet;
	}
	public void setStreet(String aStreet) {
		theStreet = aStreet;
	}
	/**
	 * Улица
	 */
	private String theStreet;
	/**
	 * Государство
	 */
	@Comment("Государство")
	@OneToOne
	public VocState getState() {
		return theState;
	}
	public void setState(VocState aState) {
		theState = aState;
	}
	/**
	 * Государство
	 */
	private VocState theState;
	/**
	 * Кладр
	 */
	@Comment("Кладр")
	@OneToOne
	public VocKladr getKladr() {
		return theKladr;
	}
	public void setKladr(VocKladr aKladr) {
		theKladr = aKladr;
	}
	/**
	 * Кладр
	 */
	private VocKladr theKladr;
	/**
	 * Городской адрес
	 */
	@Comment("Городской адрес")
	
	public Boolean getIsCity() {
		return theIsCity;
	}
	public void setIsCity(Boolean aIsCity) {
		theIsCity = aIsCity;
	}
	/**
	 * Городской адрес
	 */
	private Boolean theIsCity;
	/**
	 * Черновик
	 */
	@Comment("Черновик")
	
	public Boolean getIsDraft() {
		return theIsDraft;
	}
	public void setIsDraft(Boolean aIsDraft) {
		theIsDraft = aIsDraft;
	}
	/**
	 * Черновик
	 */
	private Boolean theIsDraft;
	/** Полный адрес */
	@Comment("Полный адрес")
	public String getFullname() {return theFullname;}
	public void setFullname(String aFullname) {theFullname = aFullname;}

	/** Полный адрес */
	private String theFullname;
}
