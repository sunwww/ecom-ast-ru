package ru.ecom.mis.web.action.disability;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisabilityReportAction  extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        //SearchForm form = (SearchForm) aForm;
        //form.validate(aMapping, aRequest);
    	try {
        IDisabilityService service = Injection.find(aRequest).getService(IDisabilityService.class);
    	DisabilitySearchForm form = (DisabilitySearchForm) aForm;
			String beginDateR = form.getBeginDate() ;
			String endDateR = form.getEndDate() ;
    	service.createF16vn(beginDateR,endDateR) ;
    	} catch (Exception e) {
			e.printStackTrace() ;
		}
        return aMapping.findForward(SUCCESS);
    }
}