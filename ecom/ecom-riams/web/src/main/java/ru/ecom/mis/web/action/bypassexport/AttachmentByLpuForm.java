package ru.ecom.mis.web.action.bypassexport;

import lombok.Getter;
import lombok.Setter;
import org.apache.struts.upload.FormFile;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@Getter
@Setter
public class AttachmentByLpuForm extends BaseValidatorForm {

	/** Количество записей */
	private String count ;
	/** Фамилия */
	private String lastname ;

	/** Идентификатор */
	private Long id ;
	/** Пол */
	private Long sex ;
	
	/** Год */
	private Long year ;
	
	/** № пакета */
	@Comment("№ пакета")
	@Required
	public String getNumberPackage() {return numberPackage;}

	/** Тип пакета для плана ДД */
	private String thePacketType;

	/** Период */
	@Comment("Период")
	@Required @DateString @DoDateString
	public String getPeriod() {return period;}

	/** NumberReestr */
	@Comment("NumberReestr")
	@Required
	public String getNumberReestr() {return numberReestr;}

	/** Созданные с даты */
	@DateString @DoDateString
	public String getChangedDateFrom() {return changedDateFrom;}
	/** НЕ проверять ЛПУ */
	private Boolean noCheckLpu;
	private String changedDateFrom;
	/** Файл */
	private String filename;
	/** NumberReestr */
	private String numberReestr;
	/** Период */
	private String period;
	/** № пакета */
	private String numberPackage;
	/** ЛПУ */
	private Long lpu;
	/** Период до */
	@Comment("Период до")
	@DateString @DoDateString @Required
	public String getPeriodTo() {return periodTo;}

	/** Период до */
	private String periodTo;
	
	/** Участок */
	private Long area;
	
	/** Страховая компания */
	private Long company;
	
	/** FilenameError */
	private String filenameError;

	/** Файл с прик. населением */
	private FormFile attachmentFile;
	
	/** Формат импорта прик. населения */
	private Long importFormat;
	
	/** Номер направления */
	private String numberDirect;
	
	/** Информация о пациенте */
	private String patientInfo;

	/** Года рождения */
	private String years ;
}
