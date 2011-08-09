package ru.ecom.mis.web.action.birthreport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

public class BirthReportRowEditAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		// TODO Auto-generated method stub
		return aMapping.findForward(SUCCESS);
	}

}
