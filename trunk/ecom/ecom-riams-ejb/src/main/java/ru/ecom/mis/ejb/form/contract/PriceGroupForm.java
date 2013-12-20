package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.PriceGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PriceGroup.class)
@Comment("Группа")
@WebTrail(comment = "Группа", nameProperties= "name", list="entityParentList-contract_priceGroup.do", view="entityParentView-contract_priceGroup.do")
@Parent(property="parent", parentForm=PriceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList/PriceGroup")
public class PriceGroupForm  extends PricePositionForm {
	/** Прейскурант */
	@Comment("Прейскурант")
	@Persist @Required
	public Long getPriceList() {return thePriceList;}
	public void setPriceList(Long aPriceList) {thePriceList = aPriceList;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}


	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Родитель */
	private Long theParent;
	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Прейскурант */
	private Long thePriceList;
	
	/** Сразу открывать */
	@Comment("Сразу открывать")
	@Persist
	public Boolean getIsOnceView() {return theIsOnceView;}
	public void setIsOnceView(Boolean aIsOnceView) {theIsOnceView = aIsOnceView;}

	/** Сразу открывать */
	private Boolean theIsOnceView;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	/** Тип услуги */
	@Comment("Тип услуги")
	@Persist
	public Long getPositionType() {return thePositionType;}
	public void setPositionType(Long aPositionType) {thePositionType = aPositionType;}

	/** Тип услуги */
	private Long thePositionType;

	
}
