package ru.ecom.expomc.web.actions.check;

import ru.ecom.expomc.ejb.services.form.check.CheckForm;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

public class CheckTableDecorator implements ITableDecorator {

	public String getId(Object aRow) {
		CheckForm form = (CheckForm) aRow ;
		return String.valueOf(form.getId());
	}

	public String getRowCssClass(Object aRow) {
		CheckForm form = (CheckForm) aRow ;
		if(form.getDisabled()) return "disabled" ;
		return "checkType"+form.getCheckType() ;
	}
	

}
