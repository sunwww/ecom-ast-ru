package ru.ecom.jaas.web.action.service.genuser;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.jaas.ejb.form.SecUserForm;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.BaseAction;
/**
 * Создание пользователей из файла
 */
public class ServiceGenUsersAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ServiceGenUsersForm form = (ServiceGenUsersForm) aForm ;
        
        ISecPolicyImportService service = (ISecPolicyImportService) Injection.find(aRequest).getService("SecPolicyImportService") ;
        aRequest.setAttribute("service", service);
        IEntityFormService formService = EntityInjection.find(aRequest).getEntityFormService() ; 

        LineNumberReader in = new LineNumberReader(
        		new InputStreamReader(form.getFile().getInputStream(), "utf-8")
        		) ; 
        String line = null ;
        
        while ( (line=in.readLine())!=null) {
        	StringTokenizer st = new StringTokenizer(line, ";") ;
        	String userLogin = st.hasMoreTokens() ? st.nextToken().trim() : null ;
        	if(StringUtil.isNullOrEmpty(userLogin)) {
        		String name = st.hasMoreTokens() ? st.nextToken().trim() : null ;
        		// FIXME проверка на существование пользователя
        		SecUserForm user = formService.prepareToCreate(SecUserForm.class) ;
        		user.setLogin(userLogin) ;
        		user.setFullname(name) ;
        		user.setComment(name) ;
        		long id = formService.create(user) ;
        		
        	}
        }
        return aMapping.findForward("success") ;
    }
    
    
}
