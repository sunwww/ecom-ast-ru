package ru.ecom.mis.ejb.form.contract;
import lombok.Setter;
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
@Setter
public class PriceListForm extends IdEntityForm{

	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist @Required
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;

	/** Используется по умолчанию */
	@Comment("Используется по умолчанию")
	@Persist
	public Boolean getIsDefault() {return isDefault;}
	/** Используется по умолчанию */
	private Boolean isDefault;
}
