package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.upload.FormFile;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class AttachmentByLpuForm extends BaseValidatorForm {

	/** Количество записей */
	@Comment("Количество записей")
	public String getCount() {return theCount;}
	public void setCount(String aCount) {theCount = aCount;}
	/** Количество записей */
	private String theCount ;
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;}
	public void setLastname(String aLastname) {theLastname = aLastname;}
	/** Фамилия */
	private String theLastname ;

	/** Идентификатор */
	@Comment("Идентификатор")
	public Long getId() {return theId;}
	public void setId(Long aId) {theId = aId;}
	/** Идентификатор */
	private Long theId ;
	/** Пол */
	@Comment("Пол")
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/** Пол */
	private Long theSex ;
	
	/** Год */
	@Comment("Год")
	public Long getYear() {return theYear;}
	public void setYear(Long aYear) {theYear = aYear;}
	/** Год */
	private Long theYear ;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** № пакета */
	@Comment("№ пакета")
	@Required
	public String getNumberPackage() {return theNumberPackage;}
	public void setNumberPackage(String aNumberPackage) {theNumberPackage = aNumberPackage;}
	
	/** Тип пакета для плана ДД */
	@Comment("Тип пакета для плана ДД")
	public String getPacketType() {return thePacketType;}
	public void setPacketType(String aPacketType) {thePacketType = aPacketType;}
	private String thePacketType;

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
	
	/** Номер направления */
	@Comment("Номер направления")
	public String getNumberDirect() {
		return theNumberDirect;
	}

	public void setNumberDirect(String aNumberDirect) {
		theNumberDirect = aNumberDirect;
	}

	/** Номер направления */
	private String theNumberDirect;
	
	/** Информация о пациенте */
	@Comment("Информация о пациенте")
	public String getPatientInfo() {
		return thePatientInfo;
	}

	public void setPatientInfo(String aPatientInfo) {
		thePatientInfo = aPatientInfo;
	}

	/** Информация о пациенте */
	private String thePatientInfo;

	/** Года рождения */
	@Comment("Года рождения")
	public String getYears() {return theYears;}
	public void setYears(String aYears) {theYears = aYears;}
	/** Года рождения */
	private String theYears ;
}
