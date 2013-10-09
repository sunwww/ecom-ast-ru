package ru.ecom.mis.web.action.bypassexport;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class AttachmentByLpuForm extends BaseValidatorForm {
	/** ЛПУ */
	@Comment("ЛПУ")
	@Required
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
	@Required
	public String getFilename() {return theFilename;}
	public void setFilename(String aFilename) {theFilename = aFilename;}

	/** НЕ проверять ЛПУ */
	@Comment("НЕ проверять ЛПУ")
	public Boolean getNoCheckLpu() {return theNoCheckLpu;}
	public void setNoCheckLpu(Boolean aNoCheckLpu) {theNoCheckLpu = aNoCheckLpu;}

	/** НЕ проверять ЛПУ */
	private Boolean theNoCheckLpu;/** Файл */
	private String theFilename;
	/** NumberReestr */
	private String theNumberReestr;
	/** Период */
	private String thePeriod;
	/** № пакета */
	private String theNumberPackage;
	/** ЛПУ */
	private Long theLpu;

}
