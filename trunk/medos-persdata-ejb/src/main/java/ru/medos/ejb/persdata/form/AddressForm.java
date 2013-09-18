package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Address;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Address.class)
@Comment("Адрес")
@WebTrail(comment = "Адрес", nameProperties= "id", list="entityParentList-voc_address.do", view="entityParentView-voc_address.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis/Person")
public class AddressForm extends IdEntityForm{
	/**
	 * Регион
	 */
	@Comment("Регион")
	@Persist
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
	@Persist
	public String getDistrict() {
		return theDistrict;
	}
	public void setDistrict(String aDistrict) {
		theDistrict = aDistrict;
	}
	/**
	 * Район
	 */
	private String theDistrict;
	/**
	 * Населенный пункт
	 */
	@Comment("Населенный пункт")
	@Persist
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
	@Persist
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
	@Persist
	public Long getState() {
		return theState;
	}
	public void setState(Long aState) {
		theState = aState;
	}
	/**
	 * Государство
	 */
	private Long theState;
	/**
	 * Кладр
	 */
	@Comment("Кладр")
	@Persist
	public Long getKladr() {
		return theKladr;
	}
	public void setKladr(Long aKladr) {
		theKladr = aKladr;
	}
	/**
	 * Кладр
	 */
	private Long theKladr;
	/**
	 * Городской адрес
	 */
	@Comment("Городской адрес")
	@Persist
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
	@Persist
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
}
