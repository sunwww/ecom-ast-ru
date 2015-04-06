package ru.ecom.mis.web.action.bypassexport;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class AttachmentByLpuForm extends BaseValidatorForm {
	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** № пакета */
	@Comment("№ пакета")
	@Required
	public String getNumberPackage() {return theNumberPackage;}
	public void setNumberPackage(String aNumberPackage) {theNumberPackage = aNumberPackage;}

	/** Период */
	@Comment("Период")
	@Required @DateString @DoDateString
	public String getPeriod() {return thePeriod;}
	public void setPeriod(String aPeriod) {thePeriod = aPeriod;}

	/** NumberReestr */
	@Comment("NumberReestr")
	@Required
	public String getNumberReestr() {return theNumberReestr;}
	public void setNumberReestr(String aNumberReestr) {theNumberReestr = aNumberReestr;}

	/** Файл */
	@Comment("Файл")
	public String getFilename() {return theFilename;}
	public void setFilename(String aFilename) {theFilename = aFilename;}

	/** НЕ проверять ЛПУ */
	@Comment("НЕ проверять ЛПУ")
	public Boolean getNoCheckLpu() {return theNoCheckLpu;}
	public void setNoCheckLpu(Boolean aNoCheckLpu) {theNoCheckLpu = aNoCheckLpu;}

	/** Созданные с даты */
	@DateString @DoDateString
	public String getChangedDateFrom() {return theChangedDateFrom;}
	public void setChangedDateFrom(String aChangedDateFrom) {theChangedDateFrom = aChangedDateFrom;}
	/** НЕ проверять ЛПУ */
	private Boolean theNoCheckLpu;
	private String theChangedDateFrom;
	/** Файл */
	private String theFilename;
	/** NumberReestr */
	private String theNumberReestr;
	/** Период */
	private String thePeriod;
	/** № пакета */
	private String theNumberPackage;
	/** ЛПУ */
	private Long theLpu;
	/** Период до */
	@Comment("Период до")
	@DateString @DoDateString @Required
	public String getPeriodTo() {return thePeriodTo;}
	public void setPeriodTo(String aPeriodTo) {thePeriodTo = aPeriodTo;}

	/** Период до */
	private String thePeriodTo;
	
	/** Участок */
	@Comment("Участок")
	public Long getArea() {return theArea;}
	public void setArea(Long aArea) {theArea = aArea;}

	/** Участок */
	private Long theArea;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	public Long getCompany() {return theCompany;}
	public void setCompany(Long aCompany) {theCompany = aCompany;}
	/** Страховая компания */
	private Long theCompany;
	
	/** FilenameError */
	@Comment("FilenameError")
	public String getFilenameError() {
		return theFilenameError;
	}

	public void setFilenameError(String aFilenameError) {
		theFilenameError = aFilenameError;
	}

	/** FilenameError */
	private String theFilenameError;
}
