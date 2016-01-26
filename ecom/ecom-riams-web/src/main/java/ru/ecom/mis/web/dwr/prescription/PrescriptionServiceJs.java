package ru.ecom.mis.web.dwr.prescription;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.ecs.xhtml.comment;
import org.jdom.IllegalDataException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.services.vocentity.VocEntityPropertyInfo;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * Сервис назначений
 * @author STkacheva
 */
public class PrescriptionServiceJs {

	// 	public String createNewDirectionFromPrescription(Long aPrescriptionListId, 
//Long aWorkFunctionPlanId, Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, 
//String aUsername, Long aOrderWorkFunction) {

	
	public String createVisitByPrescription(Long aPrescriptListId, Long aWorkFunctionPlanId,  
		Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, HttpServletRequest aRequest )throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Long wf = null;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
		try {
			wf =Long.valueOf(wqs.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '"+username+"'").iterator().next().get1().toString());
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new IllegalDataException(e.toString());
		}
		 
		String visit = service.createNewDirectionFromPrescription(aPrescriptListId, aWorkFunctionPlanId
			,aDatePlanId, aTimePlanId, aMedServiceId, username, wf);
		
		
		return visit;
	}
	/**
	 * Получение списка предварительных записей на услуги по пациенту 
	 */
	public String getDirections (Long aId, String aIdType, String aDateFrom, String aServiceType, HttpServletRequest aRequest ) throws NamingException {
		//medserviceId:mcName:cabinetID:cabinetName:wctID, WCDName
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String fromSql = "", leftJoinSql = "", whereSql = "";
		StringBuilder ret = new StringBuilder();
		if (aIdType.equals("PrescriptionList")) {
			fromSql = "prescriptionlist pl ";
			leftJoinSql=" left join medcase mc on mc.id=pl.medcase_id left join patient p on p.id=mc.patient_id";
			whereSql = "pl.id="+aId;
		} else if (aIdType.equals("Medcase")) {
			fromSql = "medcase mc ";
			leftJoinSql=" left join patient p on p.id=mc.patient_id";
			whereSql = "mc.id="+aId;
		} else {
			return "Плохой ID тип = "+aIdType;
		}
			
		String sql = "select p.id from "+fromSql+" "+leftJoinSql+" where "+whereSql;
		
		String patientId=null;
		try{
			patientId = service.executeNativeSql(sql).iterator().next().get1().toString();
		} catch (Exception e) {e.printStackTrace();}
		if (patientId!=null) {
			StringBuilder pz = new StringBuilder().append("select ms.id as msId, ms.code||' ' ||ms.name as msName" +
					", to_char(wcd.calendardate, 'dd.MM.yyyy') as calDateName, wf.id as wfId , wf.groupname as wfGroupName, wct.id as wctId " +
					" ,cast (wct.timefrom as varchar(5)) as timeName");
			pz.append(" from patient pat ")
			.append(" left join workcalendartime wct on wct.prepatient_id=pat.id")
			.append(" left join workcalendarday wcd on wcd.id=wct.workcalendarday_id")
			.append(" left join prescription p on p.id=wct.prescription")
			.append(" left join medservice ms on ms.id=coalesce(wct.service, p.medservice_id)")
			.append(" left join vocservicetype vst on vst.id=ms.servicetype_id")
			.append(" left join workcalendar wc on wc.id=wcd.workcalendar_id")
			.append(" left join workfunction wf on wf.id=wc.workfunction_id")
			.append(" where pat.id="+patientId+" and vst.code='"+aServiceType+"'");
			if (aDateFrom!=null&&!aDateFrom.equals("")){
				pz.append(" and wcd.calendardate>=to_date('"+aDateFrom+"','dd.MM.yyyy')");
			} else {
				//pz.append(" and wcd.calendardate>=current_date");
			}
			System.out.println("===== PZ = "+pz);
			Collection <WebQueryResult> res = service.executeNativeSql(pz.toString());
			if (aServiceType!=null&&aServiceType.equals("OPERATION")) {aServiceType="surg";}
			if (!res.isEmpty()) {
				boolean isFirst = true;
				for (WebQueryResult r: res) {
					String msID = r.get1().toString();
					String msCode = r.get2().toString();
					String wfID = r.get4().toString();
					String wfName = r.get5().toString();
					String timeId = r.get6().toString();
					String dateName = r.get3().toString();
					String timeName = r.get7().toString();
				if (!isFirst) ret.append("#");
				isFirst=false;
					ret.append(aServiceType).append(":").append(msID).append(":").append(msCode)
					.append(":").append(dateName)
					.append(":").append(wfID).append(":").append(wfName).append(":::").append(timeId).append(":").append(timeName.replace(":", "-"));
				}
			}
		}
		return ret.toString();
	}
	
	public boolean  isSLSClosed (String aId, HttpServletRequest aRequest) throws NamingException {
		String sql = "select case when mc.dtype='HospitalMedCase' then" +
				" case when mc.datefinish is not null and mc.dischargetime is not null then 1 else 0 end" +
				" when mc.dtype='DepartmentMedCase' then" +
				" case when mcP.datefinish is not null and mcP.dischargetime is not null then 1 else 0 end end" +
				" from medcase mc" +
				" left join medcase mcP on mcP.id=mc.parent_id" +
				" where mc.id=" + aId;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String res = service.executeNativeSql(sql, 1).iterator().next().get1().toString();
		if (res!=null&&res.equals("1")) {
			return true;
		}
		return false;
		
	}
	public boolean isMedcaseIsDepartment(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql("select dtype from medcase where id="+aMedcaseId);
		String dtype = !res.isEmpty()?res.iterator().next().get1().toString():null;
		System.out.println("isMedcaseIsDepartment, dtype="+dtype);
		if (dtype!=null&&dtype.equals("DepartmentMedCase")) {
			return true;
		} else if (dtype!=null&&dtype.equals("HospitalMedCase")) {
			return false;
		} else {
			System.out.println("isMedcaseIsDepartment, STRANGE dtype="+dtype);
			return false;
		}
	}
    public String listProtocolsByUsername(String aFunctionTemp, HttpServletRequest aRequest) throws NamingException, JspException {
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql.append("select pl.id as tid,case when su.login!='").append(login).append("' then '(общ) ' else '' end || pl.name as ttile") ; 
		sql.append(" from PrescriptionList pl");
		sql.append(" left join SecUser su on pl.createusername=su.login");
		sql.append(" left join PrescriptionList_secgroup tg on pl.id=tg.prescriptionList_id");
		sql.append(" left join SecGroup_secUser gu on gu.secgroup_id=tg.secgroups_id");
		sql.append(" left join SecUser gsu on gsu.id=gu.secUsers_id");
		sql.append(" where pl.dtype='PrescriptListTemplate' and su.login='").append(login).append("' or gsu.login='").append(login).append("'");
		sql.append(" group by pl.id,pl.name,su.login");
		sql.append(" order by pl.name") ;
		
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		res.append("<table>");
		res.append("<tr><td colspan='1'><h2>Выбор осуществляется двойным нажатием мыши</h2></td></tr>") ;
		res.append("<tr><td colspan='1' valign='top'>") ;
		res.append("<h2>Список своих шаблонов</h2>") ;
		res.append("</td></tr><tr><td valign='top'>") ;
		
		res.append("<ul>");
		for (WebQueryResult wqr:list) {
			res.append("<li class='liTemp' onclick=\"").append(aFunctionTemp).append("('")
			.append(wqr.get1()).append("',0)\" ondblclick=\"").append(aFunctionTemp).append("('")
			.append(wqr.get1()).append("',1)\">") ;
			res.append(wqr.get2()) ;
			res.append("</li>") ;
		}
		res.append("</ul></td>") ;
		res.append("</tr></table>") ;
		return res.toString() ;
	}
	public Long createTempPrescriptList(String aName,String aComment,String aCategories,String aSecGroups,HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ; 
		return service.createTempPrescriptList(aName,aComment,aCategories,aSecGroups) ;
	}
	public String removePrescriptionFromList (String aPrescriptList, String aMedService, HttpServletRequest aRequest) throws NamingException {
		return removePrescriptionFromListWCT(aPrescriptList,aMedService,null,aRequest);
	}
	public String removePrescriptionFromListWCT (String aPrescriptList, String aMedService,String aWorkCalendarTime, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder str = new StringBuilder();
		str.append("delete from prescription where medservice_id=").append(aMedService).append(" and prescriptionlist_id=")
			.append(aPrescriptList);
		if (aWorkCalendarTime!=null&&!aWorkCalendarTime.equals("")) {
			str.append(" and calendartime_id=").append(aWorkCalendarTime) ;
			service.executeUpdateNativeSql("update workcalendartime set prescription=null where id="+aWorkCalendarTime);
		}
		return "Выполнено: "+service.executeUpdateNativeSql(str.toString());
	}
	public String addPrescriptionToList (String aPrescriptList, String aMedService,String aDepartment, String aCabinet,String dType, HttpServletRequest aRequest) throws NamingException {
		return addPrescriptionToListWCT (aPrescriptList, aMedService, aDepartment, aCabinet, dType,null,null,"", aRequest);
	}
	
	/**
	 * Создаем назначение. Если на это время есть предварительное направление, то заменяем его на назначение
	 * @param aPrescriptList - лист назначений
	 * @param aMedService - мед. услуга
	 * @param aDepartment - Отдел для забора биоматериала
	 * @param aCabinet - рабочая функция для направления
	 */
	public String addPrescriptionToListWCT (String aPrescriptList, String aMedService,String aDepartment, String aCabinet,String dType, String aDateStart, String aWorkCalendarTime, String aComments, HttpServletRequest aRequest) throws NamingException {
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String wf = null;
		try {
			wf = service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '"+login+"'").iterator().next().get1().toString();
		} catch (Exception e) {e.printStackTrace(); throw new IllegalDataException(e.toString());}
		StringBuilder str = new StringBuilder();
		StringBuilder values = new StringBuilder();
		StringBuilder valuesData = new StringBuilder();
		if (wf==null) {throw new IllegalDataException("Нет рабочей функции!!!");}
		values.append("prescriptionlist_id");valuesData.append("'").append(aPrescriptList).append("'");
		values.append(",dtype");valuesData.append(",'").append(dType).append("'");
		values.append(",medservice_id");valuesData.append(",'").append(aMedService).append("'");
		if (aDepartment!=null&&!aDepartment.equals("")) {values.append(",department_id");valuesData.append(",'").append(aDepartment).append("'");}
		if (aCabinet!=null&&!aCabinet.equals("")) {values.append(",prescriptcabinet_id");valuesData.append(",'").append(aCabinet).append("'");}
		if (aWorkCalendarTime!=null&&!aWorkCalendarTime.equals("")) {values.append(",calendartime_id");valuesData.append(",'").append(aWorkCalendarTime).append("'");}
		if (aDateStart!=null&&!aDateStart.equals("")) {values.append(",planstartdate");valuesData.append(" ,to_date('").append(aDateStart).append("','dd.MM.yyyy')"); }
		values.append(",comments");valuesData.append(" ,'").append(aComments).append("'");
		values.append(",createusername");valuesData.append(" ,'").append(login).append("'");
		values.append(",createdate");valuesData.append(" ,to_date('").append(formatD.format(date)).append("','dd.MM.yyyy')");
		values.append(",createtime");valuesData.append(" ,cast ('").append(formatT.format(date)).append("' as time)");
		values.append(",prescriptspecial_id");valuesData.append(" ,").append(wf);
		str.append("insert into prescription (").append(values).append(") values (").append(valuesData).append(")");	
		//System.out.println("===== PresJS, str ="+str.toString());
		service.executeUpdateNativeSql(str.toString());
		if (aWorkCalendarTime!=null&&!aWorkCalendarTime.equals("")){
			String presId = service.executeNativeSql("select id from prescription where calendartime_id="+aWorkCalendarTime).iterator().next().get1().toString();
			 service.executeUpdateNativeSql("update workcalendartime set prescription="+presId+", prepatient_id=null where id = "+aWorkCalendarTime);
		}
		return "Выполнено: ";
	}
	
	public String cancelService(String aPrescript,Long aReasonId,String aReason,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		sql.append("update Prescription set cancelReason_id='"+aReasonId+"', cancelReasonText='").append(aReason).append("', cancelDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),cancelTime=cast('").append(formatT.format(date)).append("' as time),cancelUsername='").append(username).append("' where id in (").append(aPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		
		sql = new StringBuilder() ;
		List<Object[]> list = service.executeNativeSqlGetObj("select pl.id,p.createusername,to_char(p.planstartdate,'dd.mm.yyyy')  as dt,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ms.code||' '||ms.name from prescription p left join medservice ms on ms.id=p.medservice_id left join prescriptionlist pl on pl.id=p.prescriptionlist_id left join medcase mc on mc.id=pl.medcase_id left join patient pat on pat.id=mc.patient_id where p.id='"+aPrescript+"'") ;
		if (list.size()>0) {
			Object[] obj = list.get(0) ;
			String usernameO=""+obj[1] ;
			
			sql = new StringBuilder() ;
			
			sql.append("insert into CustomMessage (messageText,messageTitle,recipient")
				.append(",dispatchDate,dispatchTime,username,validityDate,messageUrl)") 
				.append("values ('").append("Брак биоматериала").append("','")
				.append(obj[2]).append(" пациент ").append(obj[3]).append(" услуга ").append(obj[4]).append("','").append(usernameO)
				.append("',current_date,current_time,'").append(username).append("',current_date,'")
				.append("entityView-pres_prescriptList.do?id="+obj[0]).append("')") ;
			service.executeUpdateNativeSql(sql.toString()) ;
		}

		return "1" ;
	}
	
	/**Возвращает ID листа назначений, если он существует в заданном Medcase 
	 * 
	 * UPD 11.2015 - Если ЛН не существует, то создает его
	 * 	
	 * @param aMedcase - ИД СЛО
	 * @param aRequest
	 * @return - ИД листа назначений
	 * @throws NamingException
	 */
		
	public String isPrescriptListExists(String aMedcase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String req = "Select pl.id from prescriptionlist pl where pl.medcase_id='"+aMedcase+"' order by id ";
		boolean isMedcaseClosed = isSLSClosed(aMedcase, aRequest);
		Collection <WebQueryResult> wrt = service.executeNativeSql(req, 1);
		String plId = null;
		if (wrt.size()>0) {
			WebQueryResult obj = wrt.iterator().next();
			//System.out.println("---------------------in isPrescriptListExists, id="+obj.get1().toString());
			plId = (isMedcaseClosed?"0":"1") + obj.get1().toString();
		} else {
			if (isMedcaseClosed) { return plId;}
			java.util.Date date = new java.util.Date() ;
			SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
			SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
			String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
			String wf = null;
			try {
				wf = service.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '"+username+"'").iterator().next().get1().toString();
			} catch (Exception e) {e.printStackTrace(); throw new IllegalDataException(e.toString());}
			if (wf==null) {throw new IllegalDataException("Нет рабочей функции!!!");}
			
			String sqlCreate = "insert into prescriptionlist (dtype,medcase_id, createusername, createdate, createtime, workfunction_id) values ('PrescriptList',"
					+aMedcase+", '"+username+"',to_date('"+formatD.format(date)+"','dd.MM.yyyy')"
					+", cast('"+formatT.format(date)+"' as time), "+wf+")";
			System.out.println(" ========= "+sqlCreate);
			service.executeUpdateNativeSql(sqlCreate);
			return isPrescriptListExists(aMedcase, aRequest);
		}
		return plId;
	}
	/**
	 * Поиск СЛО по ИД листа назначения
	 * @param aPrescList - ИД листа назначения
	 * @param aRequest
	 * @return ИД MedCase
	 * @throws NamingException
	 */
	public Long getMedcaseByPrescriptionList(String aPrescList, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String req = "Select pl.medcase_id from prescriptionList pl where pl.id='"+aPrescList+"' ";
		Collection<WebQueryResult> list = service.executeNativeSql(req,1) ;
		if (!list.isEmpty()) {
			WebQueryResult obj = list.iterator().next() ; 
		//	System.out.println("res.get1 ================================ "+obj.get1());
				return ConvertSql.parseLong(obj.get1());
			
		} 
		return Long.valueOf(0);
	}
	/**
	 * Проверка назначений на наличие дублей (имеются назначения на такие же 
	 * исследования в том же СЛО, в котором создается назначение)
	 * **** upd 12.12.2014 - Поиск осуществляется во всех СЛО, которые относятся к тому же СЛС, что и указаное СЛО.
	 */
	
	public String getDuplicatePrescriptions(String aMedCase, String aData, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder req = new StringBuilder();
		req.append("select distinct p.medservice_id ");
		req.append(",ms.code ||' ' || ms.name as labName ");
		//req.append(",count(p.id) as cnt1 ");
		req.append("from medcase mc ");
		req.append("left join medcase mc2 on mc2.parent_id = mc.parent_id ");
		req.append("left join prescriptionList pl on pl.medcase_id = mc2.id ");
		req.append("left join prescription p on p.prescriptionList_id = pl.id ");
	//	req.append("left join patient pat on pat.id = mc.patient_id ");
		req.append("left join medservice ms on ms.id = p.medservice_id ");
		req.append("where mc.id ='").append(aMedCase).append("' ");
		req.append("and p.dtype='ServicePrescription' ");
		req.append("and p.medservice_id is not null ");
		req.append("and p.fulfilmentstate_id is null ");
		req.append("and p.canceldate is null ");
	//	req.append("group by p.medservice_id, labName ");
		//req.append("having count(p.id)>1 ");
	//	System.out.println("--------------------getDuplicatePrescriptions Request is = "+req.toString());
		Collection<WebQueryResult> list = service.executeNativeSql(req.toString().toString()) ;
	//	System.out.println("-------------in PS - start working!!!");
		StringBuilder res = new StringBuilder();
		if (list.size()>0) {
	//		System.out.println("-------------list_size>0"+list.size());
			String[] aDataArr  = aData.split(":");
			for (WebQueryResult obj: list) {
				for (int i=0; i<aDataArr.length;i++) {
					if (obj.get1().toString().equals(aDataArr[i])) {
						res.append(obj.get1().toString()).append(":").append(obj.get2().toString()).append("#");
					}
				}
			}
		}
	//	System.out.println("in getDuplicatePrescriptions Result is = "+res.toString());
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	
	/*
	 * Возвращаем тип исследование, его код и имя, код и имя кабинета
	 * в формате: medserviceID:msName:msType:date:cabinetName# 
	 * На входе берем список исследований в формате ID:date:cabinet#
	 */
	public String getPresLabTypes(String aPresIDs, HttpServletRequest aRequest) throws NamingException {
		System.out.println(aPresIDs);
		return getPresLabTypes(aPresIDs, 0,aRequest);
	}
	
	
	/*
	 * Проверяем полученные исследования на соответствие типу листа назначения 
	 * и возвращаем те, которые соответствуют типу ЛН.
	 */
	public String getPresLabTypes(String aPresIDs, int aPrescriptListType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		aPresIDs = aPresIDs.substring(0,aPresIDs.length()-1); // Обрезаем #
		System.out.println("    presIds="+aPresIDs) ;
		StringBuilder sqlMS = new StringBuilder() ;
		StringBuilder sqlCab = new StringBuilder();
		StringBuilder res = new StringBuilder() ;
		String[] labListArr = aPresIDs.split("#");
		if (labListArr.length>0) {
			for (int i=0; i<labListArr.length;i++) {
				String[] param = labListArr[i].split(":");
			//	System.out.println("For="+i+" data ID= "+param[0]);
			//	System.out.println("For="+i+" RES "+res.toString());
				String msID = param.length>0&&param[0]!=null? param[0] : null;
				String date = param.length>1&&param[1]!=null ? param[1]: "";
				String cabID = param.length>2&&param[2]!=null? param[2] : null;
				String departmentID = param.length>3&&param[3]!=null? param[3] : null;
				if (msID!=null && msID!=""){
					sqlMS.setLength(0);
					sqlMS.append("select vst.code, ms.id, ms.code ||' ' ||ms.name from medservice ms ")
					.append("left join vocservicetype vst on vst.id=ms.servicetype_id ")
					.append("where ms.id='").append(msID).append("' ");
					if (aPrescriptListType>0) {
					sqlMS.append("and ms.id not in ");
					sqlMS.append("(select mss.id from medservice mss");
					sqlMS.append(" left join workfunctionservice wfss on wfss.medservice_id=mss.id");
					sqlMS.append(" left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id");
					sqlMS.append(" left join vocservicetype vsts on vsts.id=mss.servicetype_id where vpts.id='");
					sqlMS.append(aPrescriptListType).append("' ");
					sqlMS.append(" and mss.dtype='MedService' and vsts.code='LABSURVEY')");
	
					}
					
					Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString()) ;
					for (WebQueryResult wqr :listMS) {
						res.append(wqr.get1()).append(":")
						.append(wqr.get2()).append(":")
						.append(wqr.get3()).append(":")
						.append(date).append(":");
						
						if (cabID!=null &&!cabID.equals("")){
							sqlCab = new StringBuilder() ;
							sqlCab.append("Select wf.id,wf.groupname from workfunction wf where wf.id='").append(cabID).append("' ");
							Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(),1) ;
							for (WebQueryResult wqr2 :listCab) {
								res.append(wqr2.get1()).append(":");
								res.append(wqr2.get2()).append(":");
							}
						} else res.append("::");
						if (departmentID!=null && !departmentID.equals("")){
							sqlCab = new StringBuilder() ;
							sqlCab.append("Select ml.id,ml.name from mislpu ml where ml.id='").append(departmentID).append("' ");
							Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(),1) ;
							for (WebQueryResult wqr2 :listCab) {
								res.append(wqr2.get1()).append(":");
								res.append(wqr2.get2()).append(":");
							}
						} else res.append("::");
						res.append("::" );
						res.append("#") ;
					}
					System.out.println("Соответствуют анализы: "+ res.toString());
					
					
				}
			}
		}
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	public String getDescription(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		System.out.println("Получить описание шаблона: "+aIdTemplateList);
		return service.getDescription(aIdTemplateList) ;
	}
	public String getDefectByBiomaterial(String aPrescript, String aBiomaterialType, String aPrefixMethod,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select id,name,case when additionData='1' then additionData else null end as addData from VocPrescriptCancelReason where serviceType='LABSURVEY' and biomaterial='"+aBiomaterialType+"'") ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append("<table>") ;
		for (WebQueryResult wqr:l) {			
			ret.append("<tr>") ;
			ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()\" colspan=\"4\">");
    		ret.append("	<input name=\"typeDefect\" value=\"5\" type=\"radio\" onchange=\"cancel"+aPrefixMethod+"InLab('"+aPrescript+"','"+wqr.get1()+"','"+(wqr.get3()!=null?"1":"0")+"')\">  "+wqr.get2()); 
    		ret.append("</td>") ;
    		ret.append("</tr>") ;
		}
		ret.append("</table>") ;
		return ret.toString() ;
	}
	public String getLabCabinetByPres(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.id as p1msid,pms.code||' '||pms.name as p2msname,wf.id as w3fid,wf.groupname as w4fgroup,replace(list(''||ms.id),' ','') as m5slist") ;
		sql.append(" from WorkFunction wf") ;
		sql.append(" left join WorkFunctionService wfs on wfs.workfunction_id=wf.id") ;
		sql.append(" left join MedService pms on pms.id=wfs.medservice_id") ;
		sql.append(" left join MedService ms on ms.parent_id=pms.id") ;
		sql.append(" left join Prescription p on ms.id=p.medService_id") ;
		sql.append(" where p.id in (").append(aListPrescript).append(") ") ;
		sql.append(" group by pms.id,pms.code,pms.name,wf.id,wf.groupname") ;
		sql.append(" order by pms.name,wf.groupname");
		StringBuilder ret = new StringBuilder() ;
		Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString()) ;
		String oldi = "" ;
		StringBuilder listCab = new StringBuilder() ;
		ret.append("<form name='frmCabinet' id='frmCabinet'><table>") ;
		for (WebQueryResult wqr:lwqr) {
			String newi = String.valueOf(wqr.get1()) ;
			if (!newi.equals(oldi)) {
				oldi=newi ;
				if (listCab.length()>0)listCab.append(",") ;
				listCab.append(newi) ;
				ret.append("<tr>") ;
				ret.append("<th colspan='2'>").append(wqr.get2()).append("</th>") ;
				ret.append("</tr>") ;
			}
			ret.append("<tr>") ;
			ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
    		ret.append("	<input name=\"typeCabinet").append(wqr.get1()).append("\" id=\"typeCabinet").append(wqr.get1()).append("\" value=\"").append(wqr.get1()).append("#").append(wqr.get3()).append("#").append(aListPrescript).append("#").append(wqr.get5()).append("\" type=\"radio\" />  "+wqr.get4()); 
    		ret.append("</td>") ;
			ret.append("</tr>") ;

		}
		ret.append("<tr>") ;
		ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
		ret.append("	<input name=\"btnOk\" value=\"Принять\" type=\"button\" onclick=\"transferInLabCheck('").append(listCab).append("')\"/>" ); 
		ret.append("</td>") ;
		ret.append("</tr>") ;
		ret.append("</table></form>") ;
		return ret.toString() ;
	}
	public String checkLabAnalyzed(Long aPrescript,HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		service.checkLabAnalyzed(aPrescript,username) ;
		return "1" ;
	}
	public String checkTransferService(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String[] l = aListPrescript.split(":") ;
		for (String r:l) {
			String[] cab = r.split("#") ;
			sql = new StringBuilder() ;
			sql.append("update Prescription set prescriptCabinet_id='"+cab[1]+"',transferDate=to_date('").append(formatD.format(date))
			.append("','dd.mm.yyyy'),transferTime=cast('").append(formatT.format(date)).append("' as time)")
			.append(",transferUsername='").append(username).append("' ")
			//.append(",intakeSpecial_id='").append(spec).append("' ")
			.append(" where id in (").append(cab[2]).append(") and medservice_id in (").append(cab[3]).append(")");
			service.executeUpdateNativeSql(sql.toString()) ;
		}
		return "1" ;
	}
	public String intakeService(String aListPrescript,String aDate,String aTime,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		/*java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;*/
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Long spec  = null ;
		Collection<WebQueryResult> specL = service.executeNativeSql("select wf.id from secuser su left join workFunction wf on wf.secuser_id=su.id where su.login='"+username+"'",1) ;
		if (!specL.isEmpty()) {
			spec = ConvertSql.parseLong(specL.iterator().next().get1()) ;
		}
		if (spec==null) new IllegalDataException("У пользователя "+username+" нет соответствия с рабочей функцией") ;
		sql.append("update Prescription set intakeDate=to_date('").append(aDate).append("','dd.mm.yyyy'),intakeTime=cast('").append(aTime).append("' as time)")
			.append(",intakeUsername='").append(username).append("' ")
			.append(",intakeSpecial_id='").append(spec).append("' ")
			.append(" where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String receptionIntakeService(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		Long spec  = null ;
		Collection<WebQueryResult> specL = service.executeNativeSql("select wf.id from secuser su left join workFunction wf on wf.secuser_id=su.id where su.login='"+username+"'",1) ;
		if (!specL.isEmpty()) {
			spec = ConvertSql.parseLong(specL.iterator().next().get1()) ;
		}
		if (spec==null) new IllegalDataException("У пользователя "+username+" нет соответствия с рабочей функцией") ;
		sql.append("update Prescription set transferSpecial_id='").append(spec).append("',transferDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),transferTime=cast('").append(formatT.format(date)).append("' as time),transferUsername='").append(username).append("' where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String intakeServiceRemove(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("update Prescription set intakeSpecial_id=null,intakeDate=null,intakeTime=null,intakeUsername=null where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public boolean checkMedCaseEmergency(Long aIdTemplateList, String idType, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.checkMedCaseEmergency(aIdTemplateList, idType) ;
	}
	
	public String getPrescriptionTypes(boolean isEmergency, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getPrescriptionTypes(isEmergency) ;
	}
	
	public String getLabListFromTemplate(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getLabListFromTemplate(aIdTemplateList) ;
	}
	
	public String savePrescription(Long aIdParent,Long aIdTemplateList, Long aFlag, HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplateList);
		System.out.println("Флаг: "+aFlag);
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		if (aFlag==1) return savePrescriptExists(aIdTemplateList,aIdParent, service) ;
		if (aFlag==2) return savePrescriptNew(aIdTemplateList,aIdParent, service) ;
		return "" ;
		
	}
	private String savePrescriptExists(Long aIdTemplateList, Long aIdPrescript, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptExists(aIdTemplateList, aIdPrescript)) ret ="Сохранено в текущий лист назначений" ;
			else ret = "Ошибка при сохранении  в текущий лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в текущий лист назначений"+e.getMessage() ;
		}
		
		return ret ;
	}
	public String getTemplateByService(Long aSmoId,Long aPrescriptId, Long aService, String aFunctionGo,String aFunctionSave, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder ret = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		Long aProtocolId = null ;
		Collection<WebQueryResult> list = null ;
		if (aSmoId!=null && !aSmoId.equals(Long.valueOf(0))) { 
			list = service.executeNativeSql("select id from diary where medcase_id="+aSmoId) ;
		}
		if (list!=null && !list.isEmpty()) {
			aProtocolId = ConvertSql.parseLong(list.iterator().next().get1()) ;
		}
		if (aProtocolId!=null && !aProtocolId.equals(Long.valueOf(0))) {
			ret.append(2).append("#").append("").append(aProtocolId) ;
		} else {
			sql = new StringBuilder() ;
			sql.append("select ms.id as msid,ms.name as msname,tp.id as tpid,tp.title as tptitle") ;
			sql.append(" ,list(pg.name||': '||p.name)") ;
			sql.append(" from templateprotocol tp") ;
			sql.append(" left join parameterbyform pf on pf.template_id = tp.id") ;
			sql.append(" left join parameter p on p.id=pf.parameter_id") ;
			sql.append(" left join parametergroup pg on pg.id=p.group_id") ;
			sql.append(" left join medservice ms on ms.id=tp.medservice_id") ;
			//sql.append(" left join medcase mc1 on mc1.medservice_id=ms.id") ;
			//sql.append(" left join medcase mc2 on mc2.id=mc1.parent_id") ;
			sql.append(" where tp.medservice_id='").append(aService).append("'") ;
			sql.append(" group by tp.id,tp.title,ms.id,ms.name") ;
			Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString()) ;
			
			
			String oldi = "" ;
			StringBuilder listCab = new StringBuilder() ;
			if (lwqr.isEmpty()) {
				ret.append("0#") ;
				ret.append("<form name='frmTemplate' id='frmTemplate'>") ;
				ret.append("<a target='_blank' href='entityView-mis_medService.do?id=").append(aService).append("'>").append("НЕТ ШАБЛОНА НА УСЛУГУ</a>") ;
				ret.append("</form>") ;
			} else if (lwqr.size()==1) {
				WebQueryResult wqr = lwqr.iterator().next() ;
				ret.append("1#").append(wqr.get3()).append("##").append(wqr.get2()) ;
			} else {
				ret.append("0#") ;
				ret.append("<form name='frmTemplate' id='frmTemplate'><table>") ;
				for (WebQueryResult wqr:lwqr) {
					String newi = String.valueOf(wqr.get1()) ;
					if (!newi.equals(oldi)) {
						oldi=newi ;
						if (listCab.length()>0)listCab.append(",") ;
						listCab.append(newi) ;
						ret.append("<tr>") ;
						ret.append("<th colspan='2'>").append(wqr.get2()).append("</th>") ;
						ret.append("</tr>") ;
					}
					ret.append("<tr>") ;
					ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';").append(aFunctionGo).append("('")
					.append(aSmoId).append("','").append(aPrescriptId).append("','").append(aService)
					.append("','").append(wqr.get2())
					.append("','").append(aProtocolId!=null?aProtocolId:"")
					.append("','").append(wqr.get3())
					.append("','").append(aFunctionSave)
					.append("')\" colspan=\"4\">");
		    		ret.append("	<input name=\"typeTemplate\" id=\"typeTemplate\" value=\"").append(wqr.get1()).append("#").append(wqr.get3()).append("#").append(aSmoId).append("#").append(wqr.get5()).append("\" type=\"radio\" />  "+wqr.get4()); 
		    		ret.append("</td>") ;
					ret.append("</tr>") ;
	
				}
				
				ret.append("</table></form>") ;
			}
		}
		
		return ret.toString() ;
	}
	public String saveParameterByProtocol(Long aSmoId,Long aPrescriptId,Long aProtocolId, String aParams, HttpServletRequest aRequest) throws NamingException, JSONException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		
		return service.saveLabAnalyzed(aSmoId,aPrescriptId,aProtocolId,aParams,username) ;
	}
	public String checkLabControl(Long aSmoId,Long aProtocol, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Long spec  = null ;
		Collection<WebQueryResult> specL = service.executeNativeSql("select wf.id from secuser su left join workFunction wf on wf.secuser_id=su.id where su.login='"+username+"'",1) ;
		if (!specL.isEmpty()) {
			spec = ConvertSql.parseLong(specL.iterator().next().get1()) ;
		}
		if (spec==null) new IllegalDataException("У пользователя "+username+" нет соответствия с рабочей функцией") ;
		
		sql.append("update MedCase set workFunctionExecute_id='"+spec+"',dateStart=to_date('").append(formatD.format(date))
		.append("','dd.mm.yyyy'),timeExecute=cast('").append(formatT.format(date)).append("' as time)")
		.append(",editUsername='").append(username).append("' ,editdate=to_date('").append(formatD.format(date))
		.append("','dd.mm.yyyy'),edittime=cast('").append(formatT.format(date)).append("' as time)")
		.append(" where id in (").append(aSmoId).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		sql = new StringBuilder() ;
		sql.append("update Diary set dtype='Protocol',specialist_id='"+spec+"',dateRegistration=to_date('").append(formatD.format(date))
		.append("','dd.mm.yyyy'),timeRegistration=cast('").append(formatT.format(date)).append("' as time)")
		.append(" where id in (").append(aProtocol).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "" ;
	}
	public String getParameterByTemplate(Long aSmoId, Long aPrescript, Long aServiceId, Long aProtocolId, Long aTemplateId, HttpServletRequest aRequest) throws NamingException, JspException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		Collection<WebQueryResult> lwqr = null ;
		
		Long wfId = Long.valueOf(0) ;
		String wfName = "" ;
		if (aProtocolId!=null && !aProtocolId.equals(Long.valueOf(0))) {
			sql.append("select p.id as p1id,p.name as p2name") ;
			sql.append(" , p.shortname as p3shortname,p.type as p4type") ;
			sql.append(" , p.minimum as p5minimum, p.normminimum as p6normminimum") ;
			sql.append(" , p.maximum as p7maximum, p.normmaximum as p8normmaximum") ;
			sql.append(" , p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd") ;
			sql.append(" , p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd") ;
			sql.append(" , vmu.id as v13muid,vmu.name as v14muname") ;
			sql.append(" , vd.id as v15did,vd.name as v16dname") ;
			sql.append(" ,p.cntdecimal as p17cntdecimal") ;
			sql.append(" , ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid") ;
			sql.append(" , case when p.type in ('3','5')  then pf.valueText") ; 
			sql.append(" when p.type ='4' then to_char(round(pf.valueBD,case when p.cntdecimal is null then 0 else cast(p.cntdecimal as int) end),'fm99990.'||repeat('0',cast(p.cntdecimal as int)))"); 
			sql.append(" when p.type ='1' then to_char(round(pf.valueBD,case when p.cntdecimal is null then 0 else cast(p.cntdecimal as int) end),'fm99990') ");
			sql.append(" when p.type='2' then ''||pf.valueVoc_id end as p19val") ;
			sql.append(" ,vv.name as d20val4v") ;
			sql.append(" from FormInputProtocol pf") ;
			sql.append(" left join Diary d on pf.docProtocol_id = d.id") ;
			sql.append(" left join parameter p on p.id=pf.parameter_id") ;
			sql.append(" left join userDomain vd on vd.id=p.valueDomain_id") ;
			sql.append(" left join userValue vv on vv.id=pf.valueVoc_id") ;
			sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id") ;
			sql.append(" where d.id='").append(aProtocolId).append("'") ;
			sql.append(" order by pf.position") ;
			lwqr = service.executeNativeSql(sql.toString()) ;
			
		} 
		if (lwqr==null || lwqr.isEmpty()) {
			sql = new StringBuilder() ;
			sql.append("select p.id as p1id,p.name as p2name") ;
			sql.append(" , p.shortname as p3shortname,p.type as p4type") ;
			sql.append(" , p.minimum as p5minimum, p.normminimum as p6normminimum") ;
			sql.append(" , p.maximum as p7maximum, p.normmaximum as p8normmaximum") ;
			sql.append(" , p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd") ;
			sql.append(" , p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd") ;
			sql.append(" , vmu.id as v13muid,vmu.name as v14muname") ;
			sql.append(" , vd.id as v15did,vd.name as v16dname") ;
			sql.append(" ,p.cntdecimal as p17cntdecimal") ;
			sql.append(" , ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid") ;
			sql.append(" , case when p.type in ('3','5')  then p.valueTextDefault else '' end as p19valuetextdefault") ;
			//sql.append(", null as d18val1v,null as d19val2v,null as d20val3v,null as d21val4v") ;
			sql.append(" from parameterbyform pf") ;
			sql.append(" left join templateprotocol tp on pf.template_id = tp.id") ;
			sql.append(" left join parameter p on p.id=pf.parameter_id") ;
			sql.append(" left join userDomain vd on vd.id=p.valueDomain_id") ;
			sql.append(" left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id") ;
			sql.append(" where tp.id='").append(aTemplateId).append("'") ;
			sql.append(" order by pf.position") ;
			lwqr = service.executeNativeSql(sql.toString()) ;
		} else {
			sql = new StringBuilder() ;
			sql.append("select mc.workFunctionexecute_id, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as vwfname from diary d left join medcase mc on mc.id=d.medcase_id left join workfunction wf on wf.id=mc.workfunctionexecute_id left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf on vwf.id=wf.workfunction_id where d.id="+aProtocolId+" and mc.workFunctionExecute_id is not null") ;
			Collection<WebQueryResult> lwf=service.executeNativeSql(sql.toString()) ;
			if (!lwf.isEmpty()) {
				WebQueryResult wqr = lwf.iterator().next() ;
				wfId = ConvertSql.parseLong(wqr.get1()) ;
				wfName = ""+wqr.get2() ;
			}
		}
			
			
		StringBuilder sb = new StringBuilder() ;
		StringBuilder err = new StringBuilder() ;
			sb.append("{");
			sb.append("\"workFunction\":\""+wfId+"\",") ;
			sb.append("\"workFunctionName\":\""+wfName+"\",") ;
			if (RolesHelper.checkRoles("/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory", aRequest)) {
				sb.append("\"isdoctoredit\":\"1\",") ;
			} else {
				sb.append("\"isdoctoredit\":\"0\",") ;
			}
			sb.append("\"params\":[") ;
			boolean firstPassed = false ;
			boolean firstError = false ;
			String[][] props = {{"1","id"},{"2","name"},{"3","shortname"}
			,{"4","type"},{"5","min"},{"6","nmin"},{"7","max"},{"8","nmax"}
			,{"9","minbd"},{"10","nminbd"},{"11","maxbd"},{"12","nmaxbd"}
			,{"13","unitid"},{"14","unitname"}
			,{"15","vocid"},{"16","vocname"},{"17","cntdecimal"}
			,{"18","idEnter"},{"19","value"},{"20","valueVoc"}
			} ;
			for(WebQueryResult wqr : lwqr) {
				
				StringBuilder par = new StringBuilder() ;
				par.append("{") ;
				boolean isFirtMethod = false ;
				boolean isError = false ;
				//System.out.println("-------*-*-*errr--"+wqr.get4()+"-------*-*-*errr--"+wqr.get15()) ;
				if (String.valueOf(wqr.get4()).equals("2")) {
					//System.out.println("-------*-*-*errr--"+wqr.get1()) ;
					if (wqr.get15()==null) {
						isError = true ;
						//System.out.println("-------*-*-*errr--"+wqr.get1()) ;
					}
				}
				try {
					
					for(String[] prop : props) {
						Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
						String strValue = value!=null?value.toString():"";
						
						if(isFirtMethod) par.append(", ") ;else isFirtMethod=true;
						par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
						
					}
					
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				par.append("}") ;
				if (isError) {
					if(firstError) err.append(", ") ;else firstError=true;
					err.append(par) ;
				}else{
					if(firstPassed) sb.append(", ") ;else firstPassed=true;
					sb.append(par) ;
				}
			}
			sb.append("]") ;
			sb.append(",\"errors\":[").append(err).append("]") ;
			sb.append(",\"template\":\"").append(aTemplateId).append("\"") ;
			sb.append(",\"protocol\":\"").append(aProtocolId).append("\"") ;
			sb.append("}") ;
			return sb.toString();
		
	}
	private String str(String aValue) {
    	if (aValue.indexOf("\"")!=-1) {
    		aValue = aValue.replaceAll("\"", "\\\\\"") ;
    	}
    	return aValue ;
    }
	private String savePrescriptNew(Long aTemplateList, Long aMedCase, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptNew(aTemplateList, aMedCase)>0) ret ="Сохранено в новый лист назначений" ;
			else ret = "Ошибка при сохранении  в новый лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в новый лист назначений:"+e.getMessage() ;
		}
		return ret;	}

	public String createTemplateFromList(Long aPrescriptList, String aName, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ; 
			
		return ""+service.savePrescriptNew(aPrescriptList, Long.valueOf(0),aName).toString();	
	}
	
}
