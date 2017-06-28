package ru.ecom.mis.ejb.service.querycontstraint;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.jboss.annotation.security.SecurityDomain;


@Stateless
@Remote(IQueryConstraintService.class)
@SecurityDomain("other")
public class QueryConstraintServiceBean implements IQueryConstraintService {

	
	public String getQueryConstraint(String aKey) {
	    if(theContext.getCallerPrincipal()==null) throw new IllegalStateException("Principal was not found") ;
        String user = theContext.getCallerPrincipal().getName();
        return "SecLpu.login = '"+user+"'" ;
	}
	
	public	Boolean isConstrainted(String aKey) {
		Boolean result = false;
		if(aKey.toLowerCase().equals("patient")) {
            result = theContext.isCallerInRole("/Policy/Mis/DisablePatientAttachedCheck") ? false : true ;
        } else if (aKey.toLowerCase().equals("mislpu")) {
        	result = true;
        	}
        	else if (aKey.toLowerCase().equals("privilege")) {
        		result = true;
        		}
        		else result =  false;
		return result;
	}
	private @Resource SessionContext theContext;
}
