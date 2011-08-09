package ru.ecom.jaas.web.action.role;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.web.util.Injection;


public class RolePoliciesServiceJs  {
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