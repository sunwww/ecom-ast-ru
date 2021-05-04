package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.form.contract.interceptor.MedContractPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class MedContractForm extends IdEntityForm{

	private Long privilege;
	@Comment("Льгота")
	@Persist
	public Long getPrivilege() {
		return privilege;
	}

	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return lpu;
	}
	/**
	 * ЛПУ
	 */
	private Long lpu;
	/**
	 * Заказчик
	 */
	@Comment("Заказчик")
	@Persist
	public Long getCustomer() {
		return customer;
	}
	/**
	 * Заказчик
	 */
	private Long customer;
	/**
	 * Родитель
	 */
	@Comment("Родитель")
	@Persist
	public Long getParent() {
		return parent;
	}
	/**
	 * Родитель
	 */
	private Long parent;
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
	/**
	 * Описание
	 */
	@Comment("Описание")
	@Persist
	public String getComment() {
		return comment;
	}
	/**
	 * Описание
	 */
	private String comment;
	/**
	 * Номер договора
	 */
	@Comment("Номер договора")
	@Persist @Required
	public String getContractNumber() {
		return contractNumber;
	}
	/**
	 * Номер договора
	 */
	private String contractNumber;
	/**
	 * Обработка правил
	 */
	@Comment("Обработка правил")
	@Persist
	public Long getRulesProcessing() {
		return rulesProcessing;
	}
	/**
	 * Обработка правил
	 */
	private Long rulesProcessing;
	/**
	 * Прейскурант
	 */
	@Comment("Прейскурант")
	@Persist @Required
	public Long getPriceList() {
		return priceList;
	}
	/**
	 * Прейскурант
	 */
	private Long priceList;
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {
		return info;
	}


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
	
	/** Лимит денег */
	@Comment("Лимит денег")
	@Persist
	public String getLimitMoney() {return limitMoney;}

	/** Лимит денег */
	private String limitMoney;	
	/** Обязательно гарантийный документ */
	@Comment("Обязательно гарантийный документ")
	@Persist
	public Boolean getIsRequiredGuaratee() {
		return isRequiredGuaratee;
	}

	/** Обязательно гарантийный документ */
	private Boolean isRequiredGuaratee;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist 
	public Long getServiceStream() {
		return serviceStream;
	}

	/** Поток обслуживания */
	private Long serviceStream;
	
	/** Метка договора */
	@Comment("Метка договора")
	@Persist
	public Long getContractLabel() {return contractLabel;}

	/** Метка договора */
	private Long contractLabel;
	
	/** Срок договора */
	@Comment("Срок договора")
	@Persist 
	public Long getContractTerm() {return contractTerm;}

	/** Срок договора */
	private Long contractTerm;
	/** Скидка по умолчанию */
	@Comment("Скидка по умолчанию")
	@Persist
	public String getDiscountDefault() {return discountDefault;}

	/** Скидка по умолчанию */
	private String discountDefault;
	
	/** Оплачен */
	@Comment("Оплачен")
	@Persist
	public Boolean getIsFinished() {
		return isFinished;
	}

	/** Оплачен */
	private Boolean isFinished;
}
