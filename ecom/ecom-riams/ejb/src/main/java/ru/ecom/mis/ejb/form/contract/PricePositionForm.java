package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.PricePosition;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;
@EntityForm
@EntityFormPersistance(clazz = PricePosition.class)
@Comment("Позиция прейскуранта")
@WebTrail(comment = "Позиция прейскуранта", nameProperties= "id", list="entityParentList-contract_pricePosition.do", view="entityParentView-contract_pricePosition.do")
@Parent(property="parent", parentForm=PriceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList/PricePosition")
@Setter
public class PricePositionForm extends IdEntityForm{
	/**
	 * Прайс-лист
	 */
	@Comment("Прайс-лист")
	@Persist
	public Long getPriceList() {return priceList;}
	/**
	 * Прайс-лист
	 */
	private Long priceList;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist
	public String getName() {return name;}
	/**
	 * Название
	 */
	private String name;
	/**
	 * Код
	 */
	@Comment("Код")
	@Persist
	public String getCode() {return code;}
	/**
	 * Код
	 */
	private String code;
	/**
	 * Цена
	 */
	@Comment("Цена")
	@Persist
	public String getCost() {return cost;}
	/**
	 * Цена
	 */
	private String cost;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {return dateFrom;}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;
	
	/** Группа прейскуранта */
	@Comment("Группа прейскуранта")
	@Persist
	public Long getParent() {return parent;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}

	/** Комментарий */
	private String comment;
	/** Группа прейскуранта */
	private Long parent;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	/** С НДС */
	@Comment("С НДС")
	@Persist
	public Boolean getIsVat() {return isVat;}

	/** С НДС */
	private Boolean isVat;
	
	/** Отображать на инфомате */
	@Comment("Отображать на инфомате")
	@Persist
	public Boolean getIsViewInfomat() {return isViewInfomat;}

	/** Отображать на инфомате */
	private Boolean isViewInfomat;
	/** Тип услуги */
	@Comment("Тип услуги")
	@Required @Persist
	public Long getPositionType() {return positionType;}

	/** Тип услуги */
	private Long positionType;

	/**
	 * НДС
	 */
	@Comment("НДС")
	@Persist
	public String getCostVat() {return costVat;}
	/**
	 * Цена
	 */
	private String costVat;
	
	/** Примечание для печати */
	@Comment("Примечание для печати")
	@Persist
	public String getPrintComment() {return printComment;}

	/** Примечание для печати */
	private String printComment;
	
	/** Ставка налога */
	@Comment("Ставка налога")
	@Persist
	public Long getTax() {return tax;}
	/** Ставка налога */
	private Long tax;
	
}
