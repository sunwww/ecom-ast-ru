package ru.ecom.web.poly.actions.table.diag;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class ListDiagGroupAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean remoteUser = true ;
		//boolean aManyIs = false ;
		
		//String depId= aRequest.getParameter("department") ;
		
		// // String addParam=PreRecordAction.saveData(aRequest) ;
		String addParam="" ;
		sql.append("select id,name from PricePosition where dtype='PriceGroup' and isViewInfomat='1' order by name") ;
		
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),50);
		aRequest.setAttribute("listFunctions", list) ;
        return aMapping.findForward("success") ;

    }
}