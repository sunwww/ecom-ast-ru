package ru.ecom.jaas.web.action.role;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.ejb.service.ISecUserService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;


public class RolePoliciesServiceJs  {

	/* Получаем историю входов пользователя в систему */
	public String getUserLoginJounal(String username, int limit, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return service.executeSqlGetJson("select to_char(aj.authDate,'dd.MM.yyyy') ||' '||cast(aj.authTime as varchar(5)) as authDate, aj.servername, aj.remoteadd as remoteaddress, aj.localadd as localaddress" +
				" from AuthenticationJournal aj where aj.username ='"+username+"' order by id desc" +(limit > 0 ? " limit "+limit : "")) ;
	}
	public static Long getPasswordAge (HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		try {
			long passwordLifetime = Long.parseLong(service.executeNativeSql("select sc.KeyValue from SoftConfig sc where sc.key='PASSWORD_CHANGE_PERIOD'").iterator().next().get1().toString());
			Date passwordStartDate = DateFormat.parseDate(service.executeNativeSql("select case when passwordChangedDate is not null then to_char(passwordChangedDate,'dd.MM.yyyy') else to_char(coalesce(editdate,createdate),'dd.MM.yyyy') end as date from secuser where login='"+username+"'").iterator().next().get1().toString());
			long passwordAge = ru.nuzmsh.util.date.AgeUtil.calculateDays(passwordStartDate, null);
			long diff = passwordLifetime - passwordAge;
			return diff < 0 ? 0L :diff;
		} catch (Exception e){
			return null;
		}
	}
	public String changePassword (String aNewPassword, String aOldPassword, HttpServletRequest aRequest) throws NamingException, IOException {
		ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService");
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.changePassword(aNewPassword, aOldPassword, username);
		
	}
	public void defaultPassword (String aUsername,  String password, HttpServletRequest aRequest) throws NamingException, IOException {
		ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService");
		String whoChangeUser = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		service.setDefaultPassword(password!=null && !password.equals("") ? password : "1", aUsername,whoChangeUser);
	}

    public void savePolicies(long aRoleId, String[] aAdds, String[] aRemoves, HttpServletRequest aRequest) throws Exception {
	long[] adds = RolePoliciesSaveAction.getLongs(aAdds) ;
	long[] removes = RolePoliciesSaveAction.getLongs(aRemoves) ;
	
	ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService");
        service.saveRolePolicies(aRoleId, adds, removes) ;
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

	/**
	 * Добавить пользователя в отделение с должностью через персону
	 * @param aPatientId Long Пользователь
	 * @param aLpuId Long Госпиталь
	 * @param avWfId Long VocWorkFunction
	 * @param userCopy Long Пользователь, у которого скопировать роли
	 * @param newPsw String Пароль
	 * @param username String Логин
	 */
	public String addUserToHospShort(Long aPatientId, Long aLpuId, Long avWfId, String newPsw,  Long userCopy, String username, HttpServletRequest aRequest) throws NamingException, IOException {
		ISecUserService service = (ISecUserService) Injection.find(aRequest).getService("SecUserService");
		return service.addUserToHospShort(aPatientId, aLpuId, avWfId, newPsw, userCopy, username);
	}


	/**
	 * Получить персону по пользователю #200
	 * @param secUserId SecUser.id
	 * @return patientId
	 */
	public String getPatientBySecUser(Long secUserId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select w.person_id from SecUser su" +
				" left join WorkFunction wf on su.id=wf.secUSer_id" +
				" left join Worker w on wf.worker_id=w.id" +
				" where su.id=" + secUserId) ;
		return l.isEmpty()? "" : l.iterator().next().get1().toString();
	}
}