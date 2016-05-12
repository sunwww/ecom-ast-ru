package ru.ecom.poly.web.action.medcard;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;
import ru.nuzmsh.forms.validator.validators.MinLength;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 25.01.2007
 * Time: 21:03:25
 * To change this template use File | Settings | File Templates.
 */
public class MedcardSearchForm extends BaseValidatorForm {
    /**
     * @return Поиск по фамилии *
     */
    @DoUpperCase
    @DoTrimString
    public String getLastname() {
        return theLastname;
    }

    public void setLastname(String aLastname) {
        theLastname = aLastname;
    }

    /**
     * @return Номер медкарты *
     */
    @DoTrimString @MinLength(value = 1)
    public String getNumber() {
        return theNumber;
    }

    public void setNumber(String aNumber) {
        theNumber = aNumber;
    }
    
    /** ИД */
	public String getId() {
		return theId;
	}

	public void setId(String aId) {
		theId = aId;
	}

	/** ИД */
	private String theId;

    /**
     * Номер медкарты *
     */
    private String theNumber;

    /**
     * Поиск по фамилии *
     */
    private String theLastname;
}
