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
@WebTrail(comment = "Прейскурант", nameProperties= "name", list="entityList-contract_priceList.do", view="entityView-contract_priceList.do")
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList")
public class PriceListForm extends IdEntityForm{

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

	/** Используется по умолчанию */
	@Comment("Используется по умолчанию")
	@Persist
	public Boolean getIsDefault() {return theIsDefault;}
	public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
	/** Используется по умолчанию */
	private Boolean theIsDefault;
}
