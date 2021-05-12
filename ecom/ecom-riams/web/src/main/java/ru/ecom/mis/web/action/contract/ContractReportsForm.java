package ru.ecom.mis.web.action.contract;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@Getter
@Setter
public class ContractReportsForm extends BaseValidatorForm{

	/** Позиция прейскуранта */
	private Long pricePosition ;
	/** Рабочая функция исполнителя */
	private Long workFunction ;
	/** Гарантийное письмо (номер */
	private String guaranteeNumber;
	/** dateto */
	@DateString @DoDateString
	public String getDateTo() {return dateTo;}
	/** DateFrom */
	@DateString @DoDateString
	public String getDateFrom() {return dateFrom;}
	/** Отделение */
	private Long department;
	/** Гражданство */
	private Long nationality;
	/** Услуга по прейскуранту */
	private Long priceMedService;
	/** Фильтр по коду */
	private String filterByCode;
	/** Фильтр по наименованию */
	private String filterByName;
	/** Прейскурант */
	private Long priceList;
	/** Оператор */
	private Long operator;
	/** dateto */
	private String dateTo;
	/** DateFrom */
	private String dateFrom;
	/** Тип позиции */
	private Long positionType;
	/** Тип отделения */
	private Long departmentType;
	/** Номер контракта */
	private String contractNumber;
	/** Тип юрид. персоны */
	private Long juridicalPersonType;
	/** contractLabel */
	private Long contractLabel;
	/** Номер счета */
	private String accountNumber;
}
