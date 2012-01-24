package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.MinLength;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 21.11.2006
 * Time: 7:38:46
 * To change this template use File | Settings | File Templates.
 */
public class PatientSearchForm extends BaseValidatorForm {
    /** Фамилия */
    @DoUpperCase @DoTrimString  @Required @MinLength(value = 3)
    public String getLastname() { return theLastname ; }
    public void setLastname(String aLastname) { theLastname = aLastname ; }

    /** ЛПУ */
    public Long getLpu() { return theLpu ; }
    public void setLpu(Long aLpu) { theLpu = aLpu ; }

    /** Участок */
    public Long getLpuArea() { return theLpuArea ; }
    public void setLpuArea(Long aLpuArea) { theLpuArea = aLpuArea ; }

    /** Участок */
    private Long theLpuArea ;
    /** ЛПУ */
    private Long theLpu ;
    /** Фамилия */
    private String theLastname ;
    
    /** Дата выписки */
    @DoDateString @DateString
	public String getDischargeDate() {
		return theDischargeDate;
	}

	public void setDischargeDate(String aDischargeDate) {
		theDischargeDate = aDischargeDate;
	}

	/** Дата выписки */
	private String theDischargeDate;
	/** Искать в выписанных */
	public Boolean getIsSearchDischarge() {
		return theIsSearchDischarge;
	}

	public void setIsSearchDischarge(Boolean aIsSearchDischarge) {
		theIsSearchDischarge = aIsSearchDischarge;
	}

	/** Искать в выписанных */
	private Boolean theIsSearchDischarge;
	/** String */
	
	public String getCheckedDischarge() {
		return theCheckedDischarge;
	}

	public void setCheckedDischarge(String aCheckedDischarge) {
		theCheckedDischarge = aCheckedDischarge;
	}

	/** String */
	private String theCheckedDischarge;
}
