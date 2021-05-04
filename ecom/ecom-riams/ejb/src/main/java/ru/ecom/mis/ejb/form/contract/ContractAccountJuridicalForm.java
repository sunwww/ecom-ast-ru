package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;


@EntityForm
@EntityFormPersistance(clazz = ContractAccount.class)
@Comment("Договорной счет")
@WebTrail(comment = "Договорной счет", nameProperties= "accountNumber", list="entityParentList-contract_juridicalAccount.do", view="entityParentView-contract_juridicalAccount.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount")
@Setter
public class ContractAccountJuridicalForm extends IdEntityForm{

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
	
	/** Мед.договор */
	@Comment("Мед.договор")
	@Persist
	public Long getContract() {return contract;}

	/** Мед.договор */
	private Long contract;
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
	


	/** Номер счета */
	@Comment("Номер счета")
	@Persist
	public String getAccountNumber() {
		return accountNumber;
	}

	/** Номер счета */
	private String accountNumber;
	
	/** Оплачен */
	@Comment("Оплачен")
	@Persist
	public Boolean getIsFinished() {
		return isFinished;
	}


	/** Оплачен */
	private Boolean isFinished;
	
	/** Период с */
	@Comment("Период с")
	@Persist @DateString @DoDateString @Required
	public String getPeriodFrom() {
		return periodFrom;
	}


	/** Период по */
	@Comment("Период по")
	@Persist @DateString @DoDateString @Required
	public String getPeriodTo() {
		return periodTo;
	}


	/** Период по */
	private String periodTo;
	/** Период с */
	private String periodFrom;
}

