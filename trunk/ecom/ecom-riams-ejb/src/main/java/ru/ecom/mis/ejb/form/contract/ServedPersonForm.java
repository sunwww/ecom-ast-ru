package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.ecom.mis.ejb.form.contract.interceptor.MedContractPreCreateInterceptor;
import ru.ecom.mis.ejb.form.contract.interceptor.ServedPersonPreCreateInterceptor;
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
@EntityFormPersistance(clazz = ServedPerson.class)
@Comment("Обслуживаемая персона")
@WebTrail(comment = "Обс.персона", nameProperties= "info", list="entityParentList-contract_servedPerson.do", view="entityParentView-contract_servedPerson.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ServedPersonPreCreateInterceptor.class)
)
public class ServedPersonForm extends IdEntityForm{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@Persist @Required
	public Long getPerson() {
		return thePerson;
	}
	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Договорная персона
	 */
	private Long thePerson;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return theContract;
	}
	public void setContract(Long aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private Long theContract;
	/**
	 * Дата начала обслуживания
	 */
	@Comment("Дата начала обслуживания")
	@Persist @Required
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала обслуживания
	 */
	private String theDateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	@Comment("Дата окончания обслуживания")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания обслуживания
	 */
	private String theDateTo;
	
	@Comment("Авто создание счета")
	@Persist
	public Boolean getAutoAccount() {
		return theAutoAccount;
	}
	public void setAutoAccount(Boolean aAutoAccount) {
		theAutoAccount = aAutoAccount;
	}	
	/**
	 * Признак авто создания счета 
	 */	
	private Boolean theAutoAccount;
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

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

}
