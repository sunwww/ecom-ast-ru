package ru.ecom.diary.web.action.protocol;

import ru.nuzmsh.web.struts.BaseAction;
import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.web.util.Injection;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 18.12.2006
 * Time: 15:40:51
 * To change this template use File | Settings | File Templates.
 */
public class ProtocolSearchRedirectAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IDiaryService service = Injection.find(aRequest).getService(IDiaryService.class) ;
        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
//            aRequest.getSession(true).setAttribute("protocolSearchResult", service.findProtocol(1));
        aRequest.setAttribute("list",service.findProtocol(1));
//        aRequest.setAttribute("list",new ArrayList());
        return aMapping.findForward("success");
    }
}
