package ru.ecom.mis.web.action.synclpufond;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.lpu.ILpuService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class CreateLpusFromOmcLpuAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	
    	ILpuService service = Injection.find(aRequest).getService(ILpuService.class) ;
    	service.createMisLpuFromOmcLpu(aRequest.getParameter("type")) ;
    	return aMapping.findForward(SUCCESS) ;
    }
}
