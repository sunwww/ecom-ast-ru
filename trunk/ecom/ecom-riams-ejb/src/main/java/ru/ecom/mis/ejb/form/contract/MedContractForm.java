package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = MedContract.class)
@Comment("Медицинский договор")
@WebTrail(comment = "Медицинский договор", nameProperties= "id", list="entityParentList-contract_medContract.do", view="entityParentView-contract_medContract.do", shortView="entityShortView-contract_medContract.do")
@Parent(property="parent", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract")
public class MedContractForm extends IdEntityForm{
	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}
	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}
	/**
	 * ЛПУ
	 */
	private Long theLpu;
	/**
	 * Заказчик
	 */
	@Comment("Заказчик")
	@Persist
	public Long getCustomer() {
		return theCustomer;
	}
	public void setCustomer(Long aCustomer) {
		theCustomer = aCustomer;
	}
	/**
	 * Заказчик
	 */
	private Long theCustomer;
	/**
	 * Родитель
	 */
	@Comment("Родитель")
	@Persist
	public Long getParent() {
		return theParent;
	}
	public void setParent(Long aParent) {
		theParent = aParent;
	}
	/**
	 * Родитель
	 */
	private Long theParent;
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
	/**
	 * Описание
	 */
	@Comment("Описание")
	@Persist
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Описание
	 */
	private String theComment;
	/**
	 * Номер договора
	 */
	@Comment("Номер договора")
	@Persist @Required
	public String getContractNumber() {
		return theContractNumber;
	}
	public void setContractNumber(String aContractNumber) {
		theContractNumber = aContractNumber;
	}
	/**
	 * Номер договора
	 */
	private String theContractNumber;
	/**
	 * Обработка правил
	 */
	@Comment("Обработка правил")
	@Persist
	public Long getRulesProcessing() {
		return theRulesProcessing;
	}
	public void setRulesProcessing(Long aRulesProcessing) {
		theRulesProcessing = aRulesProcessing;
	}
	/**
	 * Обработка правил
	 */
	private Long theRulesProcessing;
	/**
	 * Прейскурант
	 */
	@Comment("Прейскурант")
	@Persist @Required
	public Long getPriceList() {
		return thePriceList;
	}
	public void setPriceList(Long aPriceList) {
		thePriceList = aPriceList;
	}
	/**
	 * Прейскурант
	 */
	private Long thePriceList;
}
