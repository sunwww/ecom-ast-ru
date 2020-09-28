package ru.ecom.web.actions.quickquery;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.quickquery.IQuickQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение файла
 */
public class ListQuickQueriesAction extends BaseAction {
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		
		String key = aRequest.getParameter("id") ; 
		IQuickQueryService service = Injection.find(aRequest).getService(
				IQuickQueryService.class);
		aRequest.setAttribute("map", service.listQuickQueries()) ;
		return aMapping.findForward(SUCCESS);
	}
}
