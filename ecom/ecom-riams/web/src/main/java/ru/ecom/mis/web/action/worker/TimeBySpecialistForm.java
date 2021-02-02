package ru.ecom.mis.web.action.worker;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

public class TimeBySpecialistForm extends BaseValidatorForm {

	private String theDate;
	private String theTimes;
	private Long theCountVisits;
	private String theTimeTo;
	private String theTimeFrom;
	private String dateFrom;
	private String dateTo;
	private Long theSpecialist;
	private Long theReserveType;
	private Long theMoveSpecialist;
	private String theMoveDate;


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

	/** Резерв */
	@Comment("Резерв")
	public Long getReserveType() {
		return theReserveType;
	}
	public void setReserveType(Long aReserveType) {
		theReserveType = aReserveType;
	}

	@Comment("Дата с")
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Comment("Дата по")
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}
