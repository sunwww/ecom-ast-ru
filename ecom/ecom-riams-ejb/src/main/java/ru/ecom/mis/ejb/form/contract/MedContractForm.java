package ru.ecom.mis.ejb.form.contract;


import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractLabel;
import ru.ecom.mis.ejb.form.contract.interceptor.MedContractPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = MedContract.class)
@Comment("Мед.договор")
@WebTrail(comment = "Мед.договор", nameProperties= "info", list="entityParentList-contract_medContract.do", view="entityParentView-contract_medContract.do", shortView="entityParentView-contract_medContract.do?short=Short")
@Parent(property="parent", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedContractPreCreateInterceptor.class)
)
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
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {
		return theInfo;
	}

	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}

	/** Информация */
	private String theInfo;
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний изменивший запись */
	private String theEditUsername;
	/** Время, последнего изменения */
	private String theEditTime;
	/** Дата последнего изменения */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	
	/** Лимит денег */
	@Comment("Лимит денег")
	@Persist
	public String getLimitMoney() {return theLimitMoney;}
	public void setLimitMoney(String aLimitMoney) {theLimitMoney = aLimitMoney;}

	/** Лимит денег */
	private String theLimitMoney;	
	/** Обязательно гарантийный документ */
	@Comment("Обязательно гарантийный документ")
	@Persist
	public Boolean getIsRequiredGuaratee() {
		return theIsRequiredGuaratee;
	}

	public void setIsRequiredGuaratee(Boolean aIsRequiredGuaratee) {
		theIsRequiredGuaratee = aIsRequiredGuaratee;
	}

	/** Обязательно гарантийный документ */
	private Boolean theIsRequiredGuaratee;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist 
	public Long getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(Long aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private Long theServiceStream;
	
	/** Метка договора */
	@Comment("Метка договора")
	@Persist
	public Long getContractLabel() {return theContractLabel;}
	public void setContractLabel(Long aContractLabel) {theContractLabel = aContractLabel;}

	/** Метка договора */
	private Long theContractLabel;
}
