package ru.nuzmsh.forms.validator;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Универсальная форма 'Map-backed ActionForms'
 */
public class MapForm extends BaseValidatorForm {

	/** Название формы */
	public String getStrutsFormName() {
		return theStrutsFormName;
	}

	public void setStrutsFormName(String aName) {
		theStrutsFormName = aName;
	}

	/** Данные формы */
	public Object getValue(String aKey) {
		return theValues.get(aKey);
	}

	public void setValue(String aKey, Object aValue) {
		theValues.put(aKey, aValue);
	}

	
	public ActionErrors validate(ActionMapping aMapping,
			HttpServletRequest aRequest) {
		// FIXME
		return null;
		// return ValidateUtil.validate(this);
	}

	
	// для копирования
	public HashMap<String,Object> getPrivateValues() {
		return theValues; 
	}
	public void setPrivateValues(HashMap<String,Object> aValues) {
		theValues = aValues ;
	}
	
    /** Порядковый номер */
	public Integer getSn() {return theSn;}
	public void setSn(Integer aSn) {theSn = aSn;}

	/** Порядковый номер */
	private Integer theSn;
	/** Название формы */
	private String theStrutsFormName;
	/** Данные формы */
	private HashMap<String,Object> theValues = new HashMap<String, Object>();

}
