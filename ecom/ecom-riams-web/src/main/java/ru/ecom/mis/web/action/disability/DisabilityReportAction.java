package ru.ecom.mis.web.action.disability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class DisabilityReportAction  extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        //SearchForm form = (SearchForm) aForm;
        //form.validate(aMapping, aRequest);
    	try {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
    	String beginDateR = aRequest.getParameter("beginDate") ;
    	String endDateR = aRequest.getParameter("endDate") ;
    	System.out.println("begin="+beginDateR) ;
    	System.out.println("end="+endDateR) ;
    	DisabilitySearchForm form = (DisabilitySearchForm) aForm;
    	beginDateR = form.getBeginDate() ;
    	endDateR = form.getEndDate() ;
    	System.out.println("beginF="+beginDateR) ;
    	System.out.println("endF="+endDateR) ;
    	service.createF16vn(beginDateR,endDateR) ;
    	} catch (Exception e) {
			e.printStackTrace() ;
		}

        return aMapping.findForward("success");
    }
}
