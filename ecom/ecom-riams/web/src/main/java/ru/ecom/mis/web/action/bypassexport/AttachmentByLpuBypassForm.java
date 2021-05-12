package ru.ecom.mis.web.action.bypassexport;

import lombok.Getter;
import lombok.Setter;
import org.apache.struts.upload.FormFile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@Getter
@Setter
public class AttachmentByLpuBypassForm extends BaseValidatorForm {
	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return lpu;}

	/** Период */
	@Comment("Период")
	@DateString @DoDateString
	public String getPeriod() {return period;}
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
	@DateString @DoDateString 
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
}
