package ru.ecom.jaas.web.action.role;

import java.io.IOException;
import java.util.Date;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.ejb.service.ISecUserService;
import ru.ecom.jaas.web.action.service.ServiceExportJbossAction;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;


public class RolePoliciesServiceJs  {
	public static Long getPasswordAge (HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		ISecUserService sec = (ISecUserService) Injection.find(aRequest).getService("SecUserService");
		Long passwordLifetime = null;
		Long diff = null;
		Long passwordAge = null;
		Date passwordStartDate = null;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		try {
			passwordLifetime = Long.valueOf(service.executeNativeSql("select KeyValue from SoftConfig where key='PASSWORD_CHANGE_PERIOD'").iterator().next().get1().toString());
		} catch (Exception e){}
		try {
			passwordStartDate  = DateFormat.parseDate(service.executeNativeSql("select case when passwordChangedDate is not null then to_char(passwordChangedDate,'dd.MM.yyyy') else to_char(coalesce(editdate,createdate),'dd.MM.yyyy') end as date from secuser where login='"+username+"'").iterator().next().get1().toString());
		} catch (Exception e){}
		if (passwordStartDate !=null) {
			passwordAge = ru.nuzmsh.util.date.AgeUtil.calculateDays(passwordStartDate, null);
		}
		if (passwordLifetime!=null) {
		//	System.out.println("PasswordAAAA , passLT = "+passwordLifetime+", passAge = "+passwordAge+", passStartdate = "+passwordStartDate);
			diff = passwordLifetime - passwordAge;
			if (diff<=0) {
				diff = Long.valueOf(0);
			} 
		}
		
		
		return diff;
	}
	public String changePassword (String aNewPassword, String aOldPassword, HttpServletRequest aRequest) throws NamingException, InstanceNotFoundException, MalformedObjectNameException, ReflectionException, MBeanException, IOException {
		ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService");
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.changePassword(aNewPassword, aOldPassword, username);
		
	}
    public void savePolicies(long aRoleId, String[] aAdds, String[] aRemoves, HttpServletRequest aRequest) throws Exception {
	long[] adds = RolePoliciesSaveAction.getLongs(aAdds) ;
	long[] removes = RolePoliciesSaveAction.getLongs(aRemoves) ;
	
	ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService");

        //long roleId = getLongId(aRequest, "Идентификатор роли");

        service.saveRolePolicies(aRoleId
                , adds
                , removes) ;

        //ServiceExportJbossAction.export(aRequest);

        //new InfoMessage(aRequest, "Политики роли сохранены и экспортированы") ;
	
    }
    public Long addPolicy(String aPolicy, String aName, HttpServletRequest aRequest) throws NamingException {
    	ISecPolicyImportService service = (ISecPolicyImportService)Injection.find(aRequest).getService("SecPolicyImportService") ;
    	return service.addPolicy(aPolicy, aName) ;
    }
    public String standartPolicy(long aParentPolicy,HttpServletRequest aRequest) throws Exception {
    	ISecPolicyImportService service = (ISecPolicyImportService)Injection.find(aRequest).getService("SecPolicyImportService") ;
    	service.standartPolicyByParent(aParentPolicy) ;
    	return "Добавлено" ;
    }
}