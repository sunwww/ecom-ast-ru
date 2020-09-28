package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.upload.FormFile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class AttachmentByLpuBypassForm extends BaseValidatorForm {
	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** № пакета */
	@Comment("№ пакета")
	
	public String getNumberPackage() {return theNumberPackage;}
	public void setNumberPackage(String aNumberPackage) {theNumberPackage = aNumberPackage;}

	/** Период */
	@Comment("Период")
	@DateString @DoDateString
	public String getPeriod() {return thePeriod;}
	public void setPeriod(String aPeriod) {thePeriod = aPeriod;}

	/** NumberReestr */
	@Comment("NumberReestr")
	
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
	@DateString @DoDateString 
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

	/** Файл с прик. населением */
	@Comment("Файл с прик. населением")
	public FormFile getAttachmentFile() {return theAttachmentFile;}
	public void setAttachmentFile(FormFile aAttachmentFile) {theAttachmentFile = aAttachmentFile;}
	/** Файл с прик. населением */
	private FormFile theAttachmentFile;
	
	/** Формат импорта прик. населения */
	@Comment("Формат импорта прик. населения")
	public Long getImportFormat() {return theImportFormat;}
	public void setImportFormat(Long aImportFormat) {theImportFormat = aImportFormat;}
	/** Формат импорта прик. населения */
	private Long theImportFormat;
}
