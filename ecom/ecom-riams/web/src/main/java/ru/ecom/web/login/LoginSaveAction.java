package ru.ecom.web.login;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.messages.ClaimMessage;
import ru.nuzmsh.web.messages.UserMessage;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.StringSafeEncode;

import javax.ejb.EJBAccessException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 *
 */
public class LoginSaveAction extends LoginExitAction {

    private static final Logger LOG = Logger.getLogger(LoginSaveAction.class) ;

    private static boolean needChangePasswordAtLogin (String aUsername, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String res ;
        try {
            res = service.executeNativeSql("select case when changePasswordAtLogin='1' then '1' else '0' end" +
                    " from secuser where login='"+aUsername+"'").iterator().next().get1().toString();
        } catch (Exception e) {
            LOG.error("error login = "+aUsername, e); //почему-то случается java.util.NoSuchElementException, напр mkostenko
			res = null;
        }
		return "1".equals(res);
    }
    private static int getPasswordAge (String username, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		int passwordAge ;
		try {
			Integer passwordLifetime = Integer.valueOf(service.executeNativeSql("select sc.KeyValue from SoftConfig sc where sc.key='PASSWORD_CHANGE_PERIOD'").iterator().next().get1().toString());
			Date passwordStartDate  = DateFormat.parseDate(service.executeNativeSql("select case when su.passwordChangedDate is not null then to_char(su.passwordChangedDate,'dd.MM.yyyy') else to_char(coalesce(su.editdate,su.createdate),'dd.MM.yyyy') end as sudate from secuser su where su.login='"+username+"'").iterator().next().get1().toString());
			passwordAge= (int)(passwordLifetime - ru.nuzmsh.util.date.AgeUtil.calculateDays(passwordStartDate, null));
			if (passwordAge<0) {
				passwordAge = 0;
			}
		} catch (Exception e){
			passwordAge = -1;
		}
		return passwordAge;
	}

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        super.myExecute(aMapping, aForm, aRequest, aResponse) ;
        LoginForm form = (LoginForm) aForm ;

        String password = form.getPassword() ;
        String username = form.getUsername().trim();
        LoginInfo loginInfo = new LoginInfo(username, password);

        HttpSession session = aRequest.getSession(true) ;
        loginInfo.saveTo(session) ;

        try {
            ILoginService service = Injection.find(aRequest).getService(ILoginService.class) ;
            if(service==null) throw new IllegalStateException("Невозможно получить сервис "+ILoginService.class.getSimpleName()) ;
            String[] urls=service.getConfigUrl() ;
			service.createRecordInAuthJournal(username, aRequest.getRemoteAddr(), aRequest.getLocalAddr(), aRequest.getServerName(), true,null,null) ;

			loginInfo.setUrlMainBase(urls[0],session) ;
            loginInfo.setUrlReportBase(urls[1],session) ;

            Set<String> roles = service.getUserRoles() ;

            if(roles==null) throw new NullPointerException("Нет ролей у пользователя roles==null") ;

            int age = getPasswordAge(username,aRequest);
            loginInfo.setUserRoles(service.getUserRoles());
            boolean changePasswordAtLogin = needChangePasswordAtLogin(form.getUsername(), aRequest);
			if (age > 0 && age < 8) {
				UserMessage.addMessage(aRequest, (long) age,"Срок действия вашего пароля истекает через "+age+" дней. ", "Сменить пароль","js-secuser-changePassword.do") ;

			} else if (age == 0 || changePasswordAtLogin){
            	return aMapping.findForward("new_password") ;
            }
        } catch (Exception e) {
            LOG.error("Ошибка при входе: "+getErrorMessage(e)+": "+username,e);
            LoginErrorMessage.setMessage(aRequest, getErrorMessage(e));
            return aMapping.getInputForward() ;
        }
        checkMessage(aRequest,username) ;
        if(StringUtil.isNullOrEmpty(form.getNext())) {
            return aMapping.findForward(SUCCESS) ;
        } else {
            String next = form.getNext() ; //.substring(form.getNext().indexOf('/',2)) ;
            try {
                next = new StringSafeEncode().decode(next);
                next = next.substring(next.indexOf('/',2)) ;
            } catch (StringIndexOutOfBoundsException ex) { // Если вдруг приложение запущено как корневое
				next = next.substring(next.indexOf('/')) ;
			} catch (Exception e) {
            	LOG.warn("next в URLEncode: "+next, e);
            	next = form.getNext().substring(form.getNext().indexOf('/',2)) ;
            }

            if (next.length()>1900) {
                if(next.indexOf('?')>0) {
                    String path  = next.substring(1,next.indexOf('?'));
                    String param = next.substring(next.indexOf('?')+1) ;
                    String[] paramM=param.split("&") ;
                    StringBuilder res = new StringBuilder() ;
                    res.append("<form method='post' action='").append(path).append("'>");
                    //ArrayList<WebQueryResult> list = new ArrayList<WebQueryResult>() ;
					for (String val : paramM) {
						String valN = val.substring(0, val.indexOf('='));
						String valV = val.substring(val.indexOf('=') + 1);
						String valV1 = URLDecoder.decode(valV, "utf-8");
						//WebQueryResult wqr = new WebQueryResult() ;
						//wqr.set1(valN) ;
						//wqr.set2(valV) ;
						//wqr.set3(valV1) ;
						res.append("<textarea name='").append(valN).append("' >");
						res.append(valV1.trim());
						res.append("</textarea>");
						res.append(valN).append("=");
						res.append(valV1);
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
    
    
    /** Поиск сообщений при входе в систему */
    public static void checkMessage(HttpServletRequest aRequest,String aUsername) throws JspException, NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	StringBuilder sqlA = new StringBuilder() ;
		sqlA.append("select id,messagetitle,messageText,to_char(datereceipt,'dd.mm.yyyy')||' '||cast(timereceipt as varchar(5)) as inforeceipt,messageUrl,username from CustomMessage") ;
		sqlA.append(" where recipient='").append(aUsername).append("'") ;
		sqlA.append(" and username!='system_message' ");
		sqlA.append(" and ((validitydate is null and readDate is null) or validitydate>=current_date) and (isEmergency is null or isEmergency='0')");
	
		Collection<WebQueryResult> list1 =service.executeNativeSql(sqlA.toString()) ;
		//StringBuilder res = new StringBuilder() ;
		if (!list1.isEmpty()) {
	    	for (WebQueryResult wqr:list1) {
	    		Long id = ConvertSql.parseLong(wqr.get1()) ;
		    	serviceLogin.dispatchMessage(id) ;
		    	UserMessage.addMessage(aRequest,id,""+wqr.get2(),""+wqr.get3(),wqr.get5()!=null?""+wqr.get5():null) ;
	    	}
		}
		if (RolesHelper.checkRoles("/Policy/Mis/Claim/View", aRequest)) { //Поиск выполненых заявок (но не подтвержденных)
			sqlA= new StringBuilder() ;
			sqlA.append("select cl.id,cl.description,to_char(cl.createdate,'dd.mm.yyyy') as createdate from claim cl left join workfunction wf on wf.id=cl.workfunction left join secuser su on su.id=wf.secuser_id where su.login='")
				.append(aUsername).append("' and cl.finishdate is not null and (cl.completeconfirmed is null or cl.completeconfirmed='0')");
			list1.clear() ;
	    	list1 =service.executeNativeSql(sqlA.toString(),10) ;
			if (!list1.isEmpty()) {
		    	for (WebQueryResult wqr:list1) {
		    		Long id = ConvertSql.parseLong(wqr.get1()) ;
			    	serviceLogin.dispatchMessage(id) ;
			    	ClaimMessage.addMessage(aRequest,id,"Подтверждение выполнения заявки от "+wqr.get3(),""+wqr.get2(),null) ;
		    	}
			}
		}
		sqlA = new StringBuilder() ;
		sqlA.append("select su.id as suid,w.lpu_id as depuser,wf.id as wfid from SecUser su left join WorkFunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id where su.login='").append(aUsername).append("'") ;
    	list1.clear() ;
    	list1 =service.executeNativeSql(sqlA.toString(),1) ;
    	Long secUserId = null , depId = null ;
    	if (!list1.isEmpty()) {
    		WebQueryResult wqr = list1.iterator().next() ;
    		secUserId = ConvertSql.parseLong(wqr.get1()) ;
    		depId =  ConvertSql.parseLong(wqr.get2()) ;
    	}
    	if (RolesHelper.checkRoles("/Policy/Config/ViewMessages/ShortProtocol", aRequest)) {
    		StringBuilder sql = new StringBuilder() ;
    		String cntDays = ActionUtil.getDefaultParameterByConfig("message_cnt_days_by_protocol", "2",service); 
    		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) { //Нет записей по стац. пациента более  Х (2) дней
    			sql.append("select")
    			.append(" case when wf.isAdministrator='1' then owp.lastname||' '||owp.firstname||' '||owp.middlename else '' end as lechvr")
    			.append(" ,count(distinct slo.id) as cntSlo")
    			.append(" from MedCase slo")
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
    			.append(" and (select max(p.dateRegistration) from diary p where p.medcase_id=slo.id and p.dtype='Protocol')<(current_date-").append(cntDays).append(") ")
    			.append(" group by wf.isAdministrator")
    			.append(" ,owp.lastname,owp.middlename,owp.firstname")
    			.append(" order by owp.lastname,owp.middlename,owp.firstname")
    			;
    		} else {
    			sql.append("select ml.name");
    			sql.append(" ,count(distinct slo.id)");
    			sql.append(" from MedCase slo");
    			sql.append(" left join MisLpu ml on slo.department_id=ml.id");
    			sql.append(" where slo.dateFinish is null ");
    			sql.append(" and slo.dtype='DepartmentMedCase'");
    			sql.append(" and slo.transferDate is null");
    			sql.append(" and slo.dateStart < current_date-").append(cntDays);
    			sql.append(" and (select max(p.dateRegistration) from diary p where p.medcase_id=slo.id and p.dtype='Protocol')<(current_date-").append(cntDays).append(") ") ;
    			sql.append(" group by ml.name");
    			sql.append(" order by ml.name");
    		}
    		Collection<WebQueryResult> list =service.executeNativeSql(sql.toString()) ;
    		StringBuilder res1 = new StringBuilder() ;
    		if (!list.isEmpty()) {
    			for (WebQueryResult wqr:list) {
    				res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append("<br>") ;
    			}
    			//System.out.println("get id message") ;
    			Long id=serviceLogin.createSystemMessage("Не заполнялись данные по пациентам более "+cntDays+" дней:", res1.toString(), aUsername) ;
    			//System.out.println("id="+id) ;
    			UserMessage.addMessage(aRequest,id,"Не заполнялись данные по пациентам более "+cntDays+" дней:", res1.toString(),"stac_report_cases_not_filled.do") ;
    		}
    	}
    	if (RolesHelper.checkRoles("/Policy/Config/ViewMessages/DirectMedicalCommission", aRequest)) { //Превышены сроки ожидания направления на ВК
    		StringBuilder sql = new StringBuilder() ;
    		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments", aRequest)) {
	    		sql.append("select")
	    			.append(" case when wf.isAdministrator='1' then owp.lastname||' '||owp.firstname||' '||owp.middlename else '' end as lechvr")
	    			.append(" ,count(distinct slo.id) as cntSlo, count(distinct case when (current_date - sls.dateStart)=(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloOfen")
	    			.append(" , count(distinct case when (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloLast")
	    			.append(" from MedCase slo")
	    			.append(" left join MedCase sls on sls.id=slo.parent_id")
	    			.append(" left join ClinicExpertCard cec on slo.id=cec.medcase_id")
	    			.append(" left join Patient pat on slo.patient_id=pat.id")
	    			.append(" left join VocSocialStatus vss on vss.id=pat.socialStatus_id") 
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
	    			.append(" and (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 12 else 27 end) ")
	    			.append(" group by wf.isAdministrator")
	    			.append(" ,owp.lastname,owp.middlename,owp.firstname")
	    			.append(" order by owp.lastname,owp.middlename,owp.firstname")
    			;
    		} else {
    			sql.append("select ml.name");
    			sql.append(" ,count(distinct slo.id) as cntSlo, count(distinct case when (current_date - sls.dateStart)=(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloOfen");
    			sql.append(" , count(distinct case when (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloLast");
    			sql.append(" from MedCase slo");
    			sql.append(" left join MedCase sls on sls.id=slo.parent_id") ;
    			sql.append(" left join ClinicExpertCard cec on slo.id=cec.medcase_id");
    			sql.append(" left join Patient pat on slo.patient_id=pat.id");
    			sql.append(" left join VocSocialStatus vss on vss.id=pat.socialStatus_id"); 
    			sql.append(" left join MisLpu ml on slo.department_id=ml.id");
    			sql.append(" where slo.dateFinish is null ");
    			sql.append(" and slo.dtype='DepartmentMedCase'");
    			sql.append(" and slo.transferDate is null");
    			sql.append(" and (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 13 else 28 end) ") ;
    			sql.append(" group by ml.name");
    			sql.append(" order by ml.name");
    		}
	    	Collection<WebQueryResult> list =service.executeNativeSql(sql.toString()) ;
	    	StringBuilder res1 = new StringBuilder() ;
	    	if (!list.isEmpty()) {
		    	for (WebQueryResult wqr:list) {
		    		res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append(" ") ;
		    		res1.append("; необходимо сегодня делать направление: ").append(wqr.get3()).append(" ") ;
		    		res1.append("; просрочены сроки подачи на ВК: ").append(wqr.get4()).append("<br>") ;
		    	}
		    	//System.out.println("get id message") ;
		    	Long id=serviceLogin.createSystemMessage("Длительность лечения пациентов более 13 дней (для безработных 28 дн):", res1.toString(), aUsername) ;
		    	//System.out.println("id="+id) ;
		    	UserMessage.addMessage(aRequest,id,"Направления на ВК:", res1.toString(),"stac_report_direct_medical_commission.do") ;
	    	}
	    	
    	}
    	if (RolesHelper.checkRoles("/Policy/Config/ViewMessages/ReceivedWithoutPolicy", aRequest)) { //Лежащие в стационаре без полисов
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
  	    	if (!list.isEmpty()) {
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
        //	Date prev = cal.getTime() ;
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
	    	if (!list.isEmpty()) {
		    	for (WebQueryResult wqr:list) {
		    		res1.append(wqr.get1()).append(" кол-во пациентов: ").append(wqr.get2()).append("<br>") ;
		    	}
		    	Long id=serviceLogin.createSystemMessage("Не оформлены данные по пациентам поступившим в приемное отделение более 2х дней назад:", res1.toString(), aUsername) ;
		    	
		    	UserMessage.addMessage(aRequest,id,"Не оформлены данные по пациентам поступившим в приемное отделение более 2х дней назад:", res1.toString(),"stac_journalByDepartmentAdmission.do") ;
		    	
	    	}
	    	
    	}
    	if (RolesHelper.checkRoles("/Policy/MainMenu/LaboratoryJournal/ChiefLabJournal", aRequest)) { //Lab chief
    		
    		String[][] labReports = {
    				{"Бракованные назначения", " and p.canceldate is not null","&typeState=1"}
    				,{"Невыполненные назначения"," and p.transferdate is not null and p.canceldate is null and p.medcase_id is null","&typeState=2"}
    				,{"Неподтвержденные назначения"," and p.transferdate is not null and p.canceldate is null and p.medcase_id is not null and mc.datestart is null","&typeState=3"}
    			};
			for (String[] labReport : labReports) {
				String labReportSql = "select 'beginDate='||to_char(current_date-1,'dd.MM.yyyy')||'&endDate='||to_char(current_date,'dd.MM.yyyy')||'&department='||ml.id||'&typeReestr=1&typeGroup=1" + labReport[2] + "'" +
						",ml.name, count(p.id)" +
						" from prescription p" +
						" left join WorkFunction wf on wf.id=p.prescriptSpecial_id" +
						" left join Worker w on w.id=wf.worker_id" +
						" left join MisLpu ml on ml.id=w.lpu_id" +
						" left join medcase mc on mc.id=p.medcase_id" +
						" left join vocprescripttype vpt on vpt.id=p.prescripttype_id " +
						" where p.dtype='ServicePrescription' and p.intakedate between current_date-1  and current_date" +
						labReport[1] +
						" group by ml.id, ml.name" +
						" order by ml.name";

				Collection<WebQueryResult> list = service.executeNativeSql(labReportSql);
				StringBuilder ret = new StringBuilder();
				if (!list.isEmpty()) {
					for (WebQueryResult wqr : list) {
						ret.append(wqr.get2()).append(": <a href='lab_chief_report.do?").append(wqr.get1()).append("'>").append(wqr.get3()).append("</a><br>");
					}
					Long id = serviceLogin.createSystemMessage(labReport[0], ret.toString(), aUsername);
					UserMessage.addMessage(aRequest, id, labReport[0], ret.toString(), "");
				}
			}
    		String labReportSql = " select 'beginDate='||to_char(current_date-1,'dd.MM.yyyy')||'&endDate='||to_char(current_date,'dd.MM.yyyy')||'&prescriptType='||vpt.id||'&typeReestr=1&typeGroup=2' as fldId " +
    				",vpt.name as f2_name" +
    				" ,count (case when p.medcase_id is null then p.id else null end) as cntNotReady " +
    				" ,count (case when p.medcase_id is not null and mc.datestart is null then p.id else null end) as cntReady" +
    				" ,count (case when mc.datestart is not null then p.id else null end) as cntConfirmed" +
    				" from prescription p" +
    				" left join medcase mc on mc.id=p.medcase_id" +
    				" left join vocprescripttype vpt on vpt.id=p.prescripttype_id" +
    				" left join WorkFunction wf on wf.id=p.prescriptSpecial_id" +
    				" left join Worker w on w.id=wf.worker_id" +
    				" left join MisLpu ml on ml.id=w.lpu_id" +
    				" where p.dtype='ServicePrescription' and p.transferdate between current_date-1" +
    				" and current_date and p.canceldate is null " +
    				" group by vpt.id, vpt.name";
    		Collection<WebQueryResult> list =service.executeNativeSql(labReportSql) ;
			StringBuilder ret = new StringBuilder();
			if (!list.isEmpty()) {
				for (WebQueryResult wqr: list) {
					ret.append(wqr.get2())
					.append("Не выполнено: <a href='lab_chief_report.do?").append(wqr.get1()).append("&typeState=0'>").append(wqr.get3()).append("</a>")
					.append("  Не подтвержден: <a href='lab_chief_report.do?").append(wqr.get1()).append("&typeState=1'>").append(wqr.get4()).append("</a>")
					.append("  Выполнено: <a href='lab_chief_report.do?").append(wqr.get1()).append("&typeState=2'>").append(wqr.get5()).append("</a>")
						
						.append("<br>");
				}
				Long id = serviceLogin.createSystemMessage("Отчет по выполнению исследований", ret.toString(), aUsername) ;
				UserMessage.addMessage(aRequest,id,"Отчет по выполнению исследований",ret.toString(),"");
			} 
		}

    }

    public static String getHashPassword(String aUsername, String aPassword) {
    	return String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
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
}
