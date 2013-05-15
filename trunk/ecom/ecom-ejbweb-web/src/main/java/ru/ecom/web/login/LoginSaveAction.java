package ru.ecom.web.login;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBAccessException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.messages.UserMessage;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.StringSafeEncode;

/**
 *
 */
public class LoginSaveAction extends LoginExitAction {

    private final static Log LOG = LogFactory.getLog(LoginSaveAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse) ;
        LoginForm form = (LoginForm) aForm ;

        //String password = getHashPassword(form.getUsername(), form.getPassword()) ;
        String password = form.getPassword() ;
        LoginInfo loginInfo = new LoginInfo(form.getUsername(), password);
        
        HttpSession session = aRequest.getSession(true) ;
        loginInfo.saveTo(session) ;

        if (CAN_TRACE) LOG.trace("Сохранение в сесии " + loginInfo.getUsername());
        try {

            ILoginService service = Injection.find(aRequest).getService(ILoginService.class) ;
            if(service==null) throw new IllegalStateException("Невозможно получить сервис "+ILoginService.class.getSimpleName()) ;

            logLoginUserInvironment(aRequest) ;
            
            Set<String> roles = service.getUserRoles() ;
            
            if(roles==null) throw new NullPointerException("Нет ролей у пользователя roles==null") ;
            loginInfo.setUserRoles(service.getUserRoles());

        } catch (Exception e) {
            LOG.error("Ошибка при входе: "+getErrorMessage(e),e);
            e.printStackTrace() ;
            LoginErrorMessage.setMessage(aRequest, getErrorMessage(e));
            return aMapping.getInputForward() ;
        }
        checkMessage(aRequest,form.getUsername()) ;
        if(StringUtil.isNullOrEmpty(form.getNext())) {
            return aMapping.findForward("success") ;
        } else {
            String next = form.getNext() ; //.substring(form.getNext().indexOf('/',2)) ;
            try {
                LOG.debug("next(1) = "+next) ;
                next = new StringSafeEncode().decode(next);
                LOG.debug("next(2) = "+next) ;
                next = next.substring(next.indexOf('/',2)) ;
                LOG.debug("next(3) = "+next) ;
            } catch (Exception e) {
            	LOG.warn("next в URLEncode: "+next, e);
            	
            	next = form.getNext().substring(form.getNext().indexOf('/',2)) ;
                LOG.debug("next(4) = "+next) ;
            }
            
            if (next.length()>1900) {
                if(next.indexOf('?')>0) {
                    String path  = next.substring(1,next.indexOf('?'));
                    String param = next.substring(next.indexOf('?')+1) ;
                    
                    
                    
                    String[] paramM=param.split("&") ;
                    StringBuilder res = new StringBuilder() ;
                    res.append("<form method='post' action='"+path+"'>") ;
                    //ArrayList<WebQueryResult> list = new ArrayList<WebQueryResult>() ;
                    for (int i=0;i<paramM.length;i++) {
                		String val = paramM[i] ;
                        String valN  = val.substring(0,val.indexOf('='));
                        String valV = val.substring(val.indexOf('=')+1) ;
                        String valV1 = URLDecoder.decode(valV,"utf-8") ;
                        //WebQueryResult wqr = new WebQueryResult() ; 
                        //wqr.set1(valN) ;
                        //wqr.set2(valV) ;
                        //wqr.set3(valV1) ;
                        res.append("<textarea name='"+valN+"' >") ;
                        res.append(valV1.trim()) ;
                        res.append("</textarea>") ;
                        res.append(""+valN+"=") ;
                        res.append(valV1) ;
                        //list.add(wqr) ;
                        
                    }
                    res.append("Загрузка...");
                    //System.out.print(res) ;
                    res.append("</form>");
                    //StringBuilder resS = new StringBuilder() ;
                    aRequest.setAttribute("textScript","<script type='text/javascript'>document.forms[0].submit() ;</script>") ;
                    //aRequest.setAttribute("listParam", list) ; 
                    aRequest.setAttribute("textParam", res) ; 
                    
                    aRequest.setAttribute("path", path.replaceFirst("/", "")) ;
                    aRequest.setAttribute("next", param) ;
                    aRequest.getRequestDispatcher("ecom_redirectMany.do").forward(aRequest, aResponse) ;
                }
            	
            	
            } 
            	
            return new ActionForward(next,true) ;
            
        }
    }
    public static void checkMessage(HttpServletRequest aRequest,String aUsername) throws JspException, NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	StringBuilder sqlA = new StringBuilder() ;
		sqlA.append("select id,messagetitle,messageText,to_char(datereceipt,'dd.mm.yyyy')||' '||cast(timereceipt as varchar(5)) as inforeceipt,messageUrl from CustomMessage") ;
		sqlA.append(" where recipient='").append(aUsername).append("'") ;
		sqlA.append(" and readDate is null");
		sqlA.append(" and username!='system_message'");
		sqlA.append(" and (validitydate is null or validitydate>=current_date)");
	
		Collection<WebQueryResult> list1 =service.executeNativeSql(sqlA.toString()) ;
		//StringBuilder res = new StringBuilder() ;
		if (!list1.isEmpty()) {
	    	for (WebQueryResult wqr:list1) {
	    		Long id = ConvertSql.parseLong(wqr.get1()) ;
		    	serviceLogin.dispatchMessage(id) ;
		    	UserMessage.addMessage(aRequest,id,""+wqr.get2(),""+wqr.get3(),wqr.get5()!=null?""+wqr.get5():null) ;
	    	}
		}
		sqlA = new StringBuilder() ;
		sqlA.append("select su.id as suid,w.lpu_id as depuser,wf.id as wfid from SecUser su left join WorkFunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id where su.login='").append(aUsername).append("'") ;
    	list1.clear() ;
    	list1 =service.executeNativeSql(sqlA.toString(),1) ;
    	Long secUserId = null , wfId = null, depId = null ;
    	if (!list1.isEmpty()) {
    		WebQueryResult wqr = list1.iterator().next() ;
    		secUserId = ConvertSql.parseLong(wqr.get1()) ;
    		wfId =  ConvertSql.parseLong(wqr.get3()) ;
    		depId =  ConvertSql.parseLong(wqr.get2()) ;
    	}
    	if (RolesHelper.checkRoles("/Policy/Config/ViewMessages/Hospital", aRequest)) {
    		StringBuilder sql = new StringBuilder() ;
    		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
	    		sql.append("select")
	    		//.append(",pat.lastname||' '||pat.middlename||' '||pat.firstname as pat")
	    			.append(" case when wf.isAdministrator='1' then owp.lastname||' '||owp.firstname||' '||owp.middlename else '' end as lechvr")
	    			.append(" ,count(distinct slo.id) as cntSlo")
	    			//.append(",count(distinct diag.id) as cntDiag")
	    			//.append(" ,current_date-max(p.dateRegistration) as cntDays,max(p.dateRegistration) as maxdateReg") 
	    			.append(" from MedCase slo")
	    			//.append(" left join Diagnosis diag on diag.medcase_id=slo.id")
	    			//.append(" left join Diary p on slo.id=p.medcase_id")
	    			.append(" left join Patient pat on slo.patient_id=pat.id")
	    			.append(" left join Worker w on w.lpu_id=slo.department_id")
	    			.append(" left join WorkFunction wf on w.id=wf.worker_id")
	    			.append(" left join SecUser su on wf.secUser_id=su.id")
	    			.append(" left join WorkFunction owf on slo.ownerFunction_id=owf.id")
	    			.append(" left join Worker ow on owf.worker_id=ow.id")
	    			.append(" left join Patient owp on ow.person_id=owp.id")
	    			.append(" where su.id='").append(secUserId).append("'")
	    			.append(" and (wf.isAdministrator='1' or (wf.isAdministrator is null or wf.isAdministrator='0') and slo.ownerFunction_id=wf.id)")
	    			.append(" and slo.dtype='DepartmentMedCase'")
	    			.append(" and slo.dateFinish is null and slo.transferDate is null")
	    			.append(" and (select max(p.dateRegistration) from diary p where p.medcase_id=slo.id)<(current_date-2)")
	    			.append(" group by wf.isAdministrator")
	    			//.append(",pat.lastname,pat.middlename,pat.firstname")
	    			.append(" ,owp.lastname,owp.middlename,owp.firstname")
	    			//.append(" having max(p.dateRegistration)<current_date-2 or count(diag.id)=0")
	    			.append(" order by owp.lastname,owp.middlename,owp.firstname")
	    			//.append(",pat.lastname,pat.middlename,pat.firstname") 
    			;
    		} else {
    			sql.append("select ml.name");
    			sql.append(" ,count(distinct slo.id)");
    			sql.append(" from MedCase slo");
    			//sql.append(" left join Diary p on slo.id=p.medcase_id");
    			sql.append(" left join MisLpu ml on slo.department_id=ml.id");
    			sql.append(" where slo.dateFinish is null ");
    			sql.append(" and slo.dtype='DepartmentMedCase'");
    			sql.append(" and slo.transferDate is null");
    			sql.append(" and slo.dateStart < current_date-2");
    			sql.append(" and (select max(p.dateRegistration) from diary p where p.medcase_id=slo.id)<(current_date-2)") ;
    			sql.append(" group by ml.name");
    			//sql.append(" having max(p.dateRegistration)<current_date-2") ;
    			sql.append(" order by ml.name");
    		}
	    	Collection<WebQueryResult> list =service.executeNativeSql(sql.toString()) ;
	    	StringBuilder res1 = new StringBuilder() ;
	    	if (list.size()>0) {
		    	for (WebQueryResult wqr:list) {
		    		res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append("<br>") ;
		    	}
		    	System.out.println("get id message") ;
		    	Long id=serviceLogin.createSystemMessage("Не заполнялись данные по пациентам более 2х дней:", res1.toString(), aUsername) ;
		    	System.out.println("id="+id) ;
		    	UserMessage.addMessage(aRequest,id,"Не заполнялись данные по пациентам более 2х дней:", res1.toString(),"stac_report_cases_not_filled.do") ;
	    	}
	    	
    	}
    	if ( RolesHelper.checkRoles("/Policy/Config/ViewMessages/ReceivedWithoutPolicy", aRequest)) {
    		StringBuilder sql = new StringBuilder() ;
    		sql.append("select case when dmc.id is not null then ml1.name else ml.name end as mlname, count(distinct hmc.id) ")
	    		.append(" ,count(distinct case when current_date-hmc.dateStart>3 then hmc.id else null end) ")
	    		.append(" from Medcase hmc ")
	    		.append(" left join mislpu ml on ml.id=hmc.department_id")
	    		.append(" left join MedCase dmc on dmc.parent_id=hmc.id")
	    		.append(" left join mislpu ml1 on ml1.id=dmc.department_id")

	    		.append(" left join vocservicestream vss on vss.id=hmc.servicestream_id ")
	    		.append(" left join medcase_medpolicy pol on pol.medCase_id=hmc.id ")
	    		.append(" left join medpolicy polI on polI.id=pol.policies_id ")
	    		.append(" left join reg_ic ri on ri.id=polI.company_id ")
	    		.append(" where ")
	    		.append(" hmc.DTYPE='HospitalMedCase' ")
	    		.append(" and hmc.dateFinish is null ")
	    		.append(" and hmc.deniedHospitalizating_id is null ") ;
    		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
				sql.append(" and (dmc.id is null and hmc.department_id='").append(depId) 
		    		.append("' or dmc.department_id=").append(depId)
		    		.append(" and dmc.dtype='DepartmentMedCase' and dmc.dateFinish is null and dmc.transferDate is null) ") ;
    		}
    		sql.append(" and (vss.code = 'OBLIGATORYINSURANCE' and (pol.id is null or (polI.dtype = 'MedPolicyOmc' and ri.omcCode='10')) ")
    			.append(" or vss.code='OTHER') ") ;
    		  sql.append(" group by case when dmc.id is not null then ml1.name else ml.name end") ;
  	    	Collection<WebQueryResult> list =service.executeNativeSql(sql.toString()) ;
  	    	StringBuilder res1 = new StringBuilder() ;
  	    	if (list.size()>0) {
  		    	for (WebQueryResult wqr:list) {
  		    		res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append(" лежат более 3х дней: ").append(wqr.get3()).append("<br>") ;
  		    	}
  		    	Long id=serviceLogin.createSystemMessage("Без полисные:", res1.toString(), aUsername) ;
  		    	//System.out.println(res1.toString()) ;
  		    	UserMessage.addMessage(aRequest,id,"Без полисные:", res1.toString(),"stac_receivedWithoutPolicy_list.do") ;
  	    	}
    	}
    	if (RolesHelper.checkRoles("/Policy/Config/ViewMessages/DirectHospital", aRequest)) {
    		//TODO доделать
			SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd") ;
			Date cur = new Date() ;
        	Calendar cal = Calendar.getInstance() ;
        	cal.setTime(cur) ;
        	cal.add(Calendar.DAY_OF_MONTH, -2) ;
        	String dateTo = FORMAT_1.format(cal.getTime()) ;
        	cal.add(Calendar.MONTH, -1) ;
        	Date prev = cal.getTime() ;
        	String dateFrom = FORMAT_1.format(cal.getTime()) ;
        	StringBuilder sql = new StringBuilder() ;
    		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
;
            	
    			sql.append("select ml.name,count(distinct m.id) as cntAll");
    			sql.append(" from MedCase  m ");
    			sql.append(" left join MedCase d on d.parent_id=m.id and d.dtype='DepartmentMedCase'");
    			sql.append(" left join worker w on w.lpu_id=m.department_id ") ;
    			sql.append("  left join workFunction wf on wf.worker_id=w.id ") ;
    			sql.append("  left join secuser su on su.id=wf.secuser_id ") ;
    			sql.append(" left join MisLpu ml on m.department_id=ml.id");
    			sql.append(" where su.id='").append(secUserId)
    				.append("' and m.dateStart between to_date('").append(dateFrom).append("','yyyy-mm-dd')  and to_date('").append(dateTo).append("','yyyy-mm-dd')");
    			sql.append(" and m.DTYPE='HospitalMedCase'");
    			sql.append("	 and d.id is null");
    			sql.append("	 and m.deniedHospitalizating_id is null");
    			sql.append("	and (m.noActuality is null or m.noActuality='0')"); 
    			sql.append("	group by ml.name");
    			sql.append("	order by ml.name");
	    			//.append(",pat.lastname,pat.middlename,pat.firstname") 
    			;
    		} else {
    			sql.append("select ml.name,count(distinct m.id) as cntAll");
    			sql.append(" from MedCase  m ");
    			sql.append(" left join MedCase d on d.parent_id=m.id and d.dtype='DepartmentMedCase'");
    			sql.append(" left join MisLpu ml on m.department_id=ml.id");
    			sql.append(" where m.dateStart between to_date('").append(dateFrom).append("','yyyy-mm-dd')  and to_date('").append(dateTo).append("','yyyy-mm-dd')");
    			sql.append(" and m.DTYPE='HospitalMedCase'");
    			sql.append("	 and d.id is null");
    			sql.append("	 and m.deniedHospitalizating_id is null");
    			sql.append("	and (m.noActuality is null or m.noActuality='0')"); 
    			sql.append("	group by ml.name");
    			sql.append("	order by ml.name");
    		}
	    	Collection<WebQueryResult> list =service.executeNativeSql(sql.toString()) ;
	    	StringBuilder res1 = new StringBuilder() ;
	    	if (list.size()>0) {
		    	for (WebQueryResult wqr:list) {
		    		res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append("<br>") ;
		    	}
		    	Long id=serviceLogin.createSystemMessage("Не оформлены данные по пациентам поступившим в приемное отделение более 2х дней назад:", res1.toString(), aUsername) ;
		    	
		    	UserMessage.addMessage(aRequest,id,"Не оформлены данные по пациентам поступившим в приемное отделение более 2х дней назад:", res1.toString(),"stac_journalByDepartmentAdmission.do") ;
		    	
	    	}
	    	
    	}

    }

    public static String getHashPassword(String aUsername, String aPassword) {
    	String hash = String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
    	//System.out.println(hash) ;
    	return hash;
    }

	private String getErrorMessage(Throwable aException) {
        if(aException instanceof FailedLoginException || aException instanceof EJBAccessException) {
            return "Неправильное имя пользователя или неверный пароль" ;
        } else if (aException instanceof CommunicationException){
            return "Ошибка подключения к серверу приложений" ;
        } else {
            if(aException.getCause()!=null) {
                 return getErrorMessage(aException.getCause()) ;
            } else {
                return aException.getMessage() ;
            }
        }
    }

	private void logLoginUserInvironment(HttpServletRequest aRequest) {
        LOG.info("Login user env : {") ;
        for(Map.Entry entry : createUserEnvironmentInfo(aRequest).entrySet() ) {
        	LOG.info("    "+entry.getKey()+" = "+entry.getValue()) ;
        }
        LOG.info("}") ;
		
	}

    private Properties createUserEnvironmentInfo(HttpServletRequest aRequest) {
    	Properties prop = new Properties() ;
    	Enumeration headers = aRequest.getHeaderNames();
    	while(headers.hasMoreElements()) {
    		String header = (String) headers.nextElement() ;
    		prop.setProperty(header, aRequest.getHeader(header));
    	}
    	return prop ;
    }
}
