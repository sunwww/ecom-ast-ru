package ru.ecom.mis.web.action.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class PrescriptionFindForm extends BaseValidatorForm {
	/** Номер */
	@Comment("Номер")
	public String getNumber() {
		return theNumber;
	}

	public void setNumber(String aNumber) {
		theNumber = aNumber;
	}

	/** Номер */
	private String theNumber;
	
	/** Дата */
	@Comment("Дата")
	@DoDateString @DateString
	public String getDateFrom() {
		return theDateFrom;
	}

	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}

	/** Дата */
	private String theDateFrom;
}
