package ru.ecom.web.poly.action.visit.record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.web.poly.action.visit.prerecord.PreRecordAction;
import ru.nuzmsh.web.struts.BaseAction;

public class InfoAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String addParam=PreRecordAction.saveData(aRequest) ;
		return aMapping.findForward("success") ;
    }
}