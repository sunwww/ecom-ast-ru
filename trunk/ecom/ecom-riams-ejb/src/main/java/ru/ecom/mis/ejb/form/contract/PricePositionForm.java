package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.PricePosition;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
@EntityForm
@EntityFormPersistance(clazz = PricePosition.class)
@Comment("Позиция прейскуранта")
@WebTrail(comment = "Позиция прейскуранта", nameProperties= "id", list="entityParentList-contract_pricePosition.do", view="entityParentView-contract_pricePosition.do")
@Parent(property="parent", parentForm=PriceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList/PricePosition")
public class PricePositionForm extends IdEntityForm{
	/**
	 * Прайс-лист
	 */
	@Comment("Прайс-лист")
	@Persist
	public Long getPriceList() {return thePriceList;}
	public void setPriceList(Long aPriceList) {thePriceList = aPriceList;}
	/**
	 * Прайс-лист
	 */
	private Long thePriceList;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Код
	 */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/**
	 * Код
	 */
	private String theCode;
	/**
	 * Цена
	 */
	@Comment("Цена")
	@Persist
	public String getCost() {return theCost;}
	public void setCost(String aCost) {theCost = aCost;}
	/**
	 * Цена
	 */
	private String theCost;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	
	/** Группа прейскуранта */
	@Comment("Группа прейскуранта")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aPriceGroup) {theParent = aPriceGroup;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Группа прейскуранта */
	private Long theParent;
}
