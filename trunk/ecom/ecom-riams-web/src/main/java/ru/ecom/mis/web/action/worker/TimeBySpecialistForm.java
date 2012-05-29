package ru.ecom.mis.web.action.worker;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

public class TimeBySpecialistForm extends BaseValidatorForm {
	/** Специалист */
	public Long getSpecialist() {return theSpecialist;}
	public void setSpecialist(Long aSpecialist) {theSpecialist = aSpecialist;}

	/** Время с */
	@TimeString @DoTimeString 
	public String getTimeFrom() {return theTimeFrom;}
	public void setTimeFrom(String aTimeFrom) {theTimeFrom = aTimeFrom;}

	/** Время по */
	@TimeString @DoTimeString 
	public String getTimeTo() {return theTimeTo;}
	public void setTimeTo(String aTimeTo) {theTimeTo = aTimeTo;}
	

	/** Кол-во посещений */
	public Long getCountVisits() {return theCountVisits;}
	public void setCountVisits(Long aCountVisits) {theCountVisits = aCountVisits;}

	/** Времена */
	public String getTimes() {return theTimes;}
	public void setTimes(String aTimes) {theTimes = aTimes;}

	/** Дата */
	@DateString @DoDateString
	public String getDate() {return theDate;}
	public void setDate(String aDate) {theDate = aDate;}

	/** Дата */
	private String theDate;
	/** Времена */
	private String theTimes;
	/** Кол-во посещений */
	private Long theCountVisits;
	/** Время по */
	private String theTimeTo;
	/** Время с */
	private String theTimeFrom;
	/** Специалист */
	private Long theSpecialist;
	/** Перенести на дату */
	@DateString @DoDateString
	public String getMoveDate() {
		return theMoveDate;
	}

	public void setMoveDate(String aMoveDate) {
		theMoveDate = aMoveDate;
	}
	/** Перенести специалиста */
	public Long getMoveSpecialist() {
		return theMoveSpecialist;
	}

	public void setMoveSpecialist(Long aMoveSpecialist) {
		theMoveSpecialist = aMoveSpecialist;
	}

	/** Перенести специалиста */
	private Long theMoveSpecialist;
	/** Перенести на дату */
	private String theMoveDate;
}
