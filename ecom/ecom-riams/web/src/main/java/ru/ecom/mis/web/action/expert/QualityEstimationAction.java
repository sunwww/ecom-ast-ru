package ru.ecom.mis.web.action.expert;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QualityEstimationAction  extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
      //  String type = aRequest.getParameter("type") ;
    //    String id =aRequest.getParameter("id") ;
        
    	//IExpertService service = Injection.find(aRequest).getService(IExpertService.class);
        //aRequest.setAttribute("list", service.findDocumentBySeriesAndNumber(form.getNumberTicket() ));

        return aMapping.findForward(SUCCESS);
    }
}