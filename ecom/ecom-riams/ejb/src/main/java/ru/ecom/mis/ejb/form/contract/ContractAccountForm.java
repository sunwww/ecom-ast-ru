package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.form.contract.interceptor.ContractAccountPreCreateInterceptor;
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
@EntityFormPersistance(clazz = ContractAccount.class)
@Comment("Договорной счет")
@WebTrail(comment = "Договорной счет", nameProperties= "info", list="entityParentList-contract_account.do", view="entityParentView-contract_account.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ContractAccountPreCreateInterceptor.class)
)
@Setter
public class ContractAccountForm extends IdEntityForm{

	private Long privilege;
	@Comment("Льгота")
	@Persist
	public Long getPrivilege() {
		return privilege;
	}

	/**
	 * Сумма баланса
	 */
	@Comment("Сумма баланса")
	@Persist
	public String getBalanceSum() {
		return balanceSum;
	}
	/**
	 * Сумма баланса
	 */
	private String balanceSum;
	/**
	 * Резервированная сумма
	 */
	@Comment("Резервированная сумма")
	@Persist
	public String getReservationSum() {
		return reservationSum;
	}
	/**
	 * Резервированная сумма
	 */
	private String reservationSum;
	/**
	 * Дата открытия
	 */
	@Comment("Дата открытия")
	@Persist
	@Required
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата открытия
	 */
	private String dateFrom;
	/**
	 * Дата закрытия
	 */
	@Comment("Дата закрытия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата закрытия
	 */
	private String dateTo;
	/**
	 * Блокирован
	 */
	@Comment("Блокирован")
	@Persist
	public Boolean getBlock() {
		return block;
	}
	/**
	 * Блокирован
	 */
	private Boolean block;
	
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

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return info;}

	/** Информация */
	private String info;
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
	
	/** Мед.договор */
	@Comment("Мед.договор")
	@Persist
	public Long getContract() {return contract;}

	/** Мед.договор */
	private Long contract;
	
	/** Скидка по умолчанию */
	@Comment("Скидка по умолчанию")
	@Persist
	public String getDiscountDefault() {return discountDefault;}

	/** Скидка по умолчанию */
	private String discountDefault;
	
	/** Список услуг */
	@Comment("Список услуг")
	public String getPriceMedServicies() {return priceMedServicies;}
	/** Услуг */
	@Comment("Услуг")
	public Long getPriceMedService() {return priceMedService;}

	/** Обслуживаемая персона */
	@Comment("Обслуживаемая персона")
	@Required
	public Long getServedPerson() {return servedPerson;}

	/** Обслуживаемая персона */
	private Long servedPerson;
	/** Услуг */
	private Long priceMedService;	
	/** Список услуг */
	private String priceMedServicies;
	/**
	 * Прейскурант
	 */
	@Comment("Прейскурант")
	public Long getPriceList() {return priceList;}
	/**
	 * Прейскурант
	 */
	private Long priceList;


}
