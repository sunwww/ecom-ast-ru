package ru.ecom.expomc.web.actions.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.actions.parententity.ListAction;

public class ChecksListAction extends ListAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		
		ActionForward ret = super.myExecute(aMapping, aForm, aRequest, aResponse);
		
		
		return ret ;
		
	}
	

}
