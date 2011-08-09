package ru.ecom.poly.web.action.ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class TicketByNonresidentListAction   extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		String typePat =ActionUtil.updateParameter("TicketByNonresident","typePatient","3", aRequest) ;
		ActionUtil.updateParameter("TicketByNonresident","period","2", aRequest) ;
		
		if (typePat.equals("2")) {
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePat.equals("1")){
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}
		
		return aMapping.findForward("success");
	}
	
	
}
