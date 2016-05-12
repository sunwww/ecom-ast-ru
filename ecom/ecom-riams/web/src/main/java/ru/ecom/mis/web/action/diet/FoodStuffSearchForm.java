package ru.ecom.mis.web.action.diet;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;

/**
 * 
 */
public class FoodStuffSearchForm extends BaseValidatorForm {
    
	/** Наименование */
    @DoTrimString 
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

   
    /** Наименование */
    private String theName ;
}
