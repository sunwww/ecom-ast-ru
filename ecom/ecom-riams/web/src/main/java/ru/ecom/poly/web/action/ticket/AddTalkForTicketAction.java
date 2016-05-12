package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.util.ForwardUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class AddTalkForTicketAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		long id = Long.valueOf(aRequest.getParameter("id")) ;
		return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), id);
	}

}
