package ru.ecom.mis.web.action.contract;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class ContractReportsForm extends BaseValidatorForm{
	/** dateto */
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}

	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}

	/** dateto */
	private String theDateTo;
	
	/** DateFrom */
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}

	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}

	/** DateFrom */
	private String theDateFrom;
}
