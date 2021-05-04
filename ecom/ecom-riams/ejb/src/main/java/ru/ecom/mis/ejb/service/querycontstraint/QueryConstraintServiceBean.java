package ru.ecom.mis.ejb.service.querycontstraint;

import org.jboss.annotation.security.SecurityDomain;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;


@Stateless
@Remote(IQueryConstraintService.class)
@SecurityDomain("other")
public class QueryConstraintServiceBean implements IQueryConstraintService {

	
	public String getQueryConstraint(String aKey) {
	    if(context.getCallerPrincipal()==null) throw new IllegalStateException("Principal was not found") ;
        String user = context.getCallerPrincipal().getName();
        return "SecLpu.login = '"+user+"'" ;
	}
	
	public	Boolean isConstrainted(String aKey) {
		Boolean result ;
		if(aKey.equalsIgnoreCase("patient")) {
            result = !context.isCallerInRole("/Policy/Mis/DisablePatientAttachedCheck") ;
        } else if (aKey.equalsIgnoreCase("mislpu")) {
        	result = true;
        	}
        	else if (aKey.equalsIgnoreCase("privilege")) {
        		result = true;
        		}
        		else result =  false;
		return result;
	}
	private @Resource SessionContext context;
}
