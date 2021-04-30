package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
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
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ServedPersonPreCreateInterceptor.class)
)
@Setter
public class ServedPersonForm extends IdEntityForm{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@Persist @Required
	public Long getPerson() {return person;}
	/**
	 * Договорная персона
	 */
	private Long person;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@Persist
	public Long getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private Long contract;
	/**
	 * Дата начала обслуживания
	 */
	@Comment("Дата начала обслуживания")
	@Persist @Required
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала обслуживания
	 */
	private String dateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	@Comment("Дата окончания обслуживания")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания обслуживания
	 */
	private String dateTo;
	
	@Comment("Авто создание счета")
	@Persist
	public Boolean getAutoAccount() {
		return autoAccount;
	}
	/**
	 * Признак авто создания счета 
	 */	
	private Boolean autoAccount;
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return info;}

	/** Информация */
	private String info;
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return createTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {return editTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, последний изменивший запись */
	private String editUsername;
	/** Время, последнего изменения */
	private String editTime;
	/** Дата последнего изменения */
	private String editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	
	/** Счет */
	@Comment("Счет")
	@Persist
	public Long getAccount() {return account;}

	/** Счет */
	private Long account;

}
