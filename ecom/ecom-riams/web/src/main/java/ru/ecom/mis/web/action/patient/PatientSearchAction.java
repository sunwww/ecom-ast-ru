package ru.ecom.mis.web.action.patient;

import java.util.Collection;

import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.util.StringUtil;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class PatientSearchAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        /*IWebQueryService serviceWeb = Injection.find(aRequest).getService(IWebQueryService.class);
        String remoteAddress = aRequest.getRemoteAddr() ;
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        Collection<WebQueryResult> listComPort = serviceWeb.executeNativeSql("select wp.id,wp.comPort from workPlace wp left join SecUser su on su.id=wp.user_id where wp.dtype='UserComputer' and (wp.remoteAddress='"+remoteAddress+"' or wp.dynamicIp='1' and su.login='"+username+"')") ;
        if (!listComPort.isEmpty()) {
        	String comport = ""+listComPort.iterator().next().get2();
        	aRequest.setAttribute("port_com", "???"+comport) ;
            System.out.println("portcom:") ;
        } else {
        	aRequest.setAttribute("port_com", "----------") ;
            System.out.println("portcom:") ;
        }
        System.out.println("portcom:") ;
        */
        PatientSearchForm form = (PatientSearchForm) aForm;
        form.validate(aMapping, aRequest) ;
        IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
        
//        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        if (!form.getLastname().equals("") && form.getLastname().length()>3) {
        	aRequest.setAttribute("list"
                , service.findPatient(form.getLpu(), form.getLpuArea(), form.getLastname()));
        }
        return aMapping.findForward("success");
    }
}
