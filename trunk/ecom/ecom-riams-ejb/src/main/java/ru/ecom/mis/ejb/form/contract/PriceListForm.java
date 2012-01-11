package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.PriceList;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
@EntityForm
@EntityFormPersistance(clazz = PriceList.class)
@Comment("Прейскурант")
@WebTrail(comment = "Прейскурант", nameProperties= "id", list="entityList-contract_priceList.do", view="entityView-contract_priceList.do")
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList")
public class PriceListForm extends IdEntityForm{
	/**
	 * Справочник типов цен
	 */
	@Comment("Справочник типов цен")
	@Persist
	public Long getVocPrice() {
		return theVocPrice;
	}
	public void setVocPrice(Long aVocPrice) {
		theVocPrice = aVocPrice;
	}
	/**
	 * Справочник типов цен
	 */
	private Long theVocPrice;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist @Required
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	
	/** Справочник типов цен (информация) */
	@Comment("Справочник типов цен (информация)")
	@Persist
	public String getVocPriceInfo() {
		return theVocPriceInfo;
	}

	public void setVocPriceInfo(String aVocPriceInfo) {
		theVocPriceInfo = aVocPriceInfo;
	}

	/** Справочник типов цен (информация) */
	private String theVocPriceInfo;
}
