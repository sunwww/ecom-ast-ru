package ru.ecom.mis.web.action.disability;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class DisabilitySearchForm extends BaseValidatorForm {
	/** DateBegin */
	@DateString @DoDateString @Required
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** EndDate */
	@DateString @DoDateString
	public String getEndDate() {return theEndDate;}
	public void setEndDate(String aEndDate) {theEndDate = aEndDate;}

	/** Причина закрытия */
	public Long getCloseReason() {return theCloseReason;}
	public void setCloseReason(Long aCloseReason) {theCloseReason = aCloseReason;}

	/** Причина нетрудоспособности */
	public Long getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(Long aDisabilityReason) {theDisabilityReason = aDisabilityReason;}

	/** Sn */
	public Long getSn() {return theSn;}
	public void setSn(Long aSn) {theSn = aSn;}
	
	/** Первичность */
	public Long getPrimarity() {return thePrimarity;}
	public void setPrimarity(Long aPrimarity) {thePrimarity = aPrimarity;}

	/** Первичность */
	private Long thePrimarity;
	/** Sn */
	private Long theSn;
	/** Причина нетрудоспособности */
	private Long theDisabilityReason;
	/** Причина закрытия */
	private Long theCloseReason;
	/** EndDate */
	private String theEndDate;
	/** DateBegin */
	private String theBeginDate;
	
	/** Номер пакета */
	public String getPacketNumber() {
		return thePacketNumber;
	}

	public void setPacketNumber(String aPacketNumber) {
		thePacketNumber = aPacketNumber;
	}

	/** Номер пакета */
	private String thePacketNumber;

	/** Головное ЛПУ */
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** Головное ЛПУ */
	private Long theLpu;
	
	/** Исполнитель */
	public Long getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(Long aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Исполнитель */
	private Long theWorkFunction;
}
