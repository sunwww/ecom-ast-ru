package ru.ecom.mis.web.dwr.medcase;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.poly.web.dwr.TicketServiceJs;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Сервис по случаю лечения в стационаре
 * @author Tkacheva Sveltana
 */
public class HospitalMedCaseServiceJs {
	private static final Logger LOG = Logger.getLogger(HospitalMedCaseServiceJs.class);


	/**Календарь с предварительной госпитализацией*/

	public String getPreHospCalendar( Integer aYear, Integer aMonth, Long aDepartment, Boolean isOpht, HttpServletRequest aRequest) throws NamingException {
		JSONArray preHosps = new JSONArray(getPreHospByMonth( aYear,aMonth,aDepartment,isOpht,aRequest));
		StringBuilder res = new StringBuilder();
		res.append("<form name='frmDate' id='frmDate' action='javascript:step5()'>");
		res.append("<span class = 'spanNavigMonth'>");
		if (aMonth == 1) {
			res.append("<a href=\"javascript:showPreHospCalendar('")
					.append(getMonth(12, false))
					.append("','").append(aYear - 1);
			res.append("');\">")
					.append("<-")
					//.append(getMonth(12,true)).append(" ").append(Integer.valueOf(aYear)-1)
					.append("</a> ");
		} else {
			res.append("<a href=\"javascript:showPreHospCalendar('")
					.append(getMonth(aMonth - 1, false))
					.append("','").append(aYear);
			res.append("');\">").append("<-")
					//.append(getMonth(month-1,true)).append(" ").append(Integer.valueOf(aYear))
					.append("</a> ");
		}
		res.append(" ").append(getMonth(aMonth, true).toUpperCase()).append(" ").append(aYear);
		if (aMonth == 12) {
			res.append(" <a href=\"javascript:showPreHospCalendar('")
					.append(getMonth(1, false))
					.append("','").append(aYear + 1);
			res.append("');\">")
					//.append("").append(getMonth(1,true))
					//.append(" ").append(Integer.valueOf(aYear)+1)
					.append("-></a>");
		} else {
			res.append("<a href=\"javascript:showPreHospCalendar('")
					.append(getMonth(aMonth + 1, false))
					.append("','").append(aYear);
			res.append("');\">-></a> ");
		}
		res.append("</span>");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, aYear);
		aMonth--;
		cal.set(Calendar.MONTH, aMonth);
		cal.set(Calendar.DATE, 1);
		int day = 1;
		int oldday = 0;
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0) {
			week = 7;
		}
		week--;

		res.append("<table class='listDates'>");
		res.append("<tr>")
				.append("<th>Пон</th>")
				.append("<th>Вт</th>")
				.append("<th>Ср</th>")
				.append("<th>Чет</th>")
				.append("<th>Пят</th>")
				.append("<th>Суб</th>")
				.append("<th>Вос</th>")
				.append("</tr>");

		res.append("<tr>");
		res.append(getFreeDay(0, week, false, 1));
		for (int i=0;i<preHosps.length();i++) {
			JSONObject preHosp = preHosps.getJSONObject(i);
			//oldday = Integer.valueOf(""+wqr.get3()) ;
            int monthDate = preHosp.getInt("monthDate");
            int amount = preHosp.getInt("amount");
			oldday =  monthDate;
			res.append(getFreeDay(day, oldday, true, week));
			week = (week + oldday - day) % 7;
			if (week == 0) week = 7;
			week++;
			if (week > 7) {
				res.append("</tr><tr>");
			}
			boolean isBusy = amount == 0;
			res.append("<td id='tdDay").append(monthDate).append("'");
			//if (true) {
			res.append("onclick=\"showPreHospByDate(this,'").append(preHosp.getString("calendarDate"));
			res.append("',").append(isOpht).append(")\"");
			res.append(" class='").append(isBusy ? "busyDay" : "visitDay").append("'>");
			res.append(isBusy ? "" : "<b>").append(monthDate);
			res.append(" <br>(").append(amount).append(")");
			res.append(isBusy ? "" : "</b>").append("</td>");
			day = oldday + 1;
		}
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		res.append(getFreeDay(day, max + 1, true, week));
		if (oldday > 0) {
			week = (week + max - day) % 7;
			if (week == 0) week = 7;
		} else {
			week = (week + max) % 7;
			if (week == 0) week = 7;
		}
		week++;
		res.append(getFreeDay(week, 7, false, 1));
		res.append("</tr>");

		res.append("</table></form>");
		return res.toString();
	}
	private String getMonth(int aMonth, boolean aFullname) {
		String month;
		switch (aMonth) {
			case 1:
				month = aFullname ? "Январь" : "01";
				break;
			case 2:
				month = aFullname ? "Февраль" : "02";
				break;
			case 3:
				month = aFullname ? "Март" : "03";
				break;
			case 4:
				month = aFullname ? "Апрель" : "04";
				break;
			case 5:
				month = aFullname ? "Май" : "05";
				break;
			case 6:
				month = aFullname ? "Июнь" : "06";
				break;
			case 7:
				month = aFullname ? "Июль" : "07";
				break;
			case 8:
				month = aFullname ? "Август" : "08";
				break;
			case 9:
				month = aFullname ? "Сентябрь" : "09";
				break;
			case 10:
				month = aFullname ? "Октябрь" : "10";
				break;
			case 11:
				month = aFullname ? "Ноябрь" : "11";
				break;
			case 12:
				month = aFullname ? "Декабрь" : "12";
				break;
			default:
				month = aFullname ? "" : "" + aMonth;
				break;
		}
		return month;
	}

	private StringBuilder getFreeDay(int aFrom, int aTo, boolean aView, int aWeek) {

		StringBuilder res = new StringBuilder();
		for (int i = aFrom; i < aTo; i++) {

			aWeek = aWeek % 7;
			if (aWeek == 0) aWeek = 7;
			aWeek++;

			if (aWeek > 7) {
				res.append("</tr><tr>");
				aWeek = 1;
			}
			if (aView) {
				res.append("<td id='tdDay").append(getMonth(i, false)).append("'").append("class='freeDay'").append(">").append(i).append("</td>");
			} else {
				res.append("<td>&nbsp;</td>");
			}
		}
		return res;
	}
	/*Количество пред. госпитализаций за месяц*/
	public String getPreHospByMonth(Integer aYear, Integer aMonth, Long aDepartment, Boolean isOpht, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String date = isOpht? "createDate" : "dateFrom";
		String sql = "select to_char(pre."+date+",'dd.MM.yyyy'), cast(to_char(pre."+date+",'dd') as int) as dat, count(pre.id) as cnt" +
				" from workcalendarhospitalbed pre" +
				" left join voceye e on e.id=pre.eye_id" +
				" where to_char(pre."+date+",'MM.yyyy')='"+(aMonth>9?aMonth:"0"+aMonth)+"."+aYear+"'" +
				(aDepartment!=null && aDepartment>0L ? " and pre.department_id="+aDepartment:"") +
				(isOpht? " and e.id is not null " : "") +
				" group by pre." + date +
				" order by pre." + date;
		return service.executeNativeSqlGetJSON(new String[] {"calendarDate","monthDate","amount"},sql,31);
	}
	/**
	 * Информация о направлении на госпитализацию для автоматического заполнения госпитализации
	 * */
	public String getInfoByPreHosp(Long aPreHospId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select" +
				" vss.id as vssId, vss.name as vssName" +
				" ,oMl.id as orderId, oml.name as orderName" +
				" ,ml.id as depId, ml.name as depName" +
				" ,mkb.id as dsId,mkb.code||' '||mkb.name as dsName,wchb.diagnosis as diagnos" +
				" from WorkCalendarHospitalBed wchb" +
				" left join vocservicestream vss on vss.id=wchb.servicestream_id" +
				" left join mislpu oMl on oMl.id=wchb.orderlpu_id" +
				" left join VocIdc10 mkb on mkb.id=wchb.idc10_id" +
				" left join MisLpu ml on ml.id=wchb.department_id" +
				" where wchb.id ="+aPreHospId;
		return service.executeNativeSqlGetJSON(new String[] {"serviceStream","serviceStreamName","orderLpu","orderLpuName"
		,"department","departmentName","orderMkb","orderMkbName","orderDiagnos"},sql,1);

	}

	public boolean isAbortRequiredByOperation(Long aMedServiceId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String ret ;
				try {
					ret=service.executeNativeSql("select case when isAbortRequired='1' then '1' else '0' end from medservice where id="+aMedServiceId).iterator().next().get1().toString();
				} catch (Exception e) {
					ret = null;
				}
		return "1".equals(ret);
	}


	public String getDiagnosisAndModelByVMPMethod(Long aMethodId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		return service.executeNativeSqlGetJSON(new String[] {"diagnosis","patientModel"},"select diagnosis as f1, patientModel as f2 from vocmethodhighcare where id="+aMethodId,1);

	}

	public String getMedcaseCost(String aDateFrom, String aDateTo, String aType, String aLpuCode, HttpServletRequest aRequest ) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
		return service.makeReportCostCase(aDateFrom,aDateTo,aType,aLpuCode);
	}
	public String getAllServicesByMedCase(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
		return service.getAllServicesByMedCase(aMedcaseId);
	}
	public Long checkIsEndoscopyMethod (Long aMethodId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select case when endoscopyUse='1' then '1' else '0' end from VocOperationMethod where id="+aMethodId) ;
		return Long.valueOf(l.isEmpty() ? "0" : l.iterator().next().get1().toString());
	}
	public String getCriminalPhoneMessageByTrauma(Long aMedCase, Long aDeathReason, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select id from VocDeathReason where id="+aDeathReason+" and code='9'") ;
		if (!l.isEmpty()) {
			l = service.executeNativeSql("select to_char(pm.WhenDateEventOccurred,'dd.mm.yyyy'),pm.Place,substring(pm.Comment,0,200) from PhoneMessage pm where pm.dtype='CriminalPhoneMessage' and pm.medcase_id="+aMedCase) ;
			if (!l.isEmpty()) {
				WebQueryResult wqr = l.iterator().next() ;
				return wqr.get1()+"@#@"+wqr.get2()+"@#@"+wqr.get3()+"@#@";
			}
		}
		return null ;
	}
	public String toNull (String aValue) {
		if (aValue==null ||aValue.equals("")||aValue.trim().equals("")) return "null";
		return aValue.trim();
	}
	public String createTemperatureCurve (Long aMedCase, String aParams, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		String[] par = aParams.split(":");
	//	LOG.info("=== == medcase = "+aMedCase+" = " +aParams+" = length "+par.length);
		String takingDate = toNull(par[0]);
		String pulse = toNull(par[6]);
		String bloodPressureDown = toNull(par[5]);
		String bloodPressureUp = toNull(par[4]);
		String weight = toNull(par[9]);
		String respirationRate = toNull(par[8]);
		String degree = toNull(par[7]);
		String illnessDayNumber = toNull(par[1]);
		String dayTime = toNull(par[3]);
		String stool = par.length>10 ? toNull(par[10]) : "null";
		
		//String hospDayNumber = par[2())
		StringBuilder sql = new StringBuilder();
		sql.append("insert into temperatureCurve (takingDate, pulse, bloodPressureDown, bloodPressureUp, weight, respirationRate, degree")
		.append(", illnessdaynumber, daytime_id, medcase_id, stool_id) values (");
		
		sql.append("to_date('").append(takingDate).append("','dd.MM.yyyy'),").append(pulse).append(",").append(bloodPressureDown).append(",").append(bloodPressureUp).append(",").append(weight).append(",").append(respirationRate);
		sql.append(", ").append(degree).append(", ").append(illnessDayNumber).append(", ").append(dayTime).append(", ").append(aMedCase).append(", ").append(stool);
		sql.append(")");
	//	LOG.info("=== === "+sql);
		return "" + service.executeUpdateNativeSql(sql.toString());
		
	}
	public String getServiceStreamAndMkbByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select smc.serviceStream_id as ssId, vss.name as vssname")
			.append(", max(case when vpd.code='1' and vdrt.code='3' then mkb.id else null end) as mkbId")
			.append(", max(case when vpd.code='1' and vdrt.code='3' then mkb.code||' '||mkb.name else null end) as mkbname")
			.append(", max(case when vpd.code='1' and vdrt.code='4' then mkb.id else null end) as mkbid1")
			.append(", max(case when vpd.code='1' and vdrt.code='4' then mkb.code||' '||mkb.name else null end) as mkbname1")
			.append(",smc.dtype as dtype , vss.code as vssCode")
			.append(" from medcase smc")
			.append(" left join Diagnosis d on d.medCase_id=smc.id")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id")
			.append(" left join VocIdc10 mkb on mkb.id=d.idc10_id")
			.append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
			.append(" where smc.id='").append(aMedCase).append("' group by smc.serviceStream_id,vss.name,vss.code, smc.dtype") ;
		List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString()) ;
		JSONObject ret = new JSONObject();
		if (!resL.isEmpty()) {
			Object[] obj = resL.get(0) ;
			ret.put("medcaseType",obj[6]);
			if (obj[0]!=null) { //поток обслуживания
				ret.put("serviceStreamId",obj[0]);
				ret.put("serviceStreamName",obj[1]);
				ret.put("serviceStreamCode",obj[7]);
			}
				ret.put("mkbId",obj[2]!=null ? obj[2] : (obj[4]!=null ? obj[4] : ""));
				ret.put("mkbName",obj[2]!=null ? obj[3] : (obj[4]!=null ? obj[5] : ""));
		}
		return ret.toString() ;
	}
	public String getServiceByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select smc.medservice_id,ms.code||' '||ms.name as serviceName")
			.append(",smc.workfunctionexecute_id,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo")
			.append(",smc.idc10_id,mkb.code||' '||mkb.name as mkb")
			.append(",to_char(coalesce(smc.datestart,vis.datestart),'dd.mm.yyyy') as datestart,coalesce(smc.medserviceAmount,1)")
			.append(",coalesce(smc.serviceStream_id,vis.serviceStream_id),coalesce(vss.name, visSS.name) as vssname")
			.append(" from medcase smc")
			.append(" left join medcase vis on vis.id=smc.parent_id")
			.append(" left join MedService ms on ms.id=smc.medservice_id")
			.append(" left join WorkFunction wf on wf.id=smc.workFunctionExecute_id")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join Patient wp on wp.id=w.person_id")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" left join VocIdc10 mkb on mkb.id=smc.idc10_id")
			.append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
			.append(" left join VocServiceStream visSs on visSs.id=vis.serviceStream_id")
			.append(" where smc.parent_id='").append(aMedCase).append("' and smc.dtype='ServiceMedCase' order by smc.id") ;
		List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString()) ;
		for (Object[] objects : resL) {
			for (int j = 0; j < 10; j++) {
				res.append(objects[j]).append("@#@");
			}
			res = new StringBuilder(res.length() > 0 ? res.toString().trim().substring(0, res.length() - 3) : "");
			res.append("#@#");
		}
		return res.length()>0 ? res.toString().trim().substring(0,res.length()-3) : "" ;
	}
	public String setAccountBySmo(Long aSmo, Long aAccount, HttpServletRequest aRequest) throws NamingException, ParseException {
		
		IWebQueryService serviceW = Injection.find(aRequest).getService(IWebQueryService.class) ;
		IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
		Collection<WebQueryResult> l = serviceW.executeNativeSql("select upper(mc.dtype) from medcase mc where mc.id="+aSmo) ;
		if (l.isEmpty()) throw new ParseException("НЕОПРЕДЕЛЕН СМО",0) ;
		String dtype = ""+l.iterator().next().get1() ;
		service.setSmoByAccount(aAccount,dtype,aSmo,true,true) ;
		return "" ;
	}
	public String saveServiceByMedCase(Long aMedCase, String aServices, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
	//	service.executeUpdateNativeSql("delete from medcase where parent_id="+aMedCase+" and dtype='ServiceMedCase'");
		if (aServices!=null&&!aServices.equals("")) {
			String[] otherServs = aServices.split("#@#");
			if (otherServs.length>0) {
				for (String otherServ : otherServs) {
					String[] serv = otherServ.split("@#@");
					StringBuilder sql = new StringBuilder();
					sql.append("insert into medcase (noActuality,dtype,createdate,createtime,username,parent_id")
							.append(",medservice_id,workfunctionexecute_id,idc10_id")
							.append(",datestart,medserviceAmount,serviceStream_id) values (");
					sql.append("'0','ServiceMedCase',current_date,current_time,'").append(login)
							.append("','").append(aMedCase).append("','").append(serv[0]).append("','").append(serv[1]).append("','").append(serv[2])
							.append("',to_date('").append(serv[3]).append("','dd.mm.yyyy'),'").append(serv[4]).append("','").append(serv[5])
							.append("')");
					service.executeUpdateNativeSql(sql.toString());
				}
			}
		}

		return "" ;
	}
	
	public String checkEditProtocolControl(Long aDiaryMessage, Long aDiary, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		List<Object[]> list = service.executeNativeSqlGetObj("select dm.diary_id,dm.record from diarymessage dm left join diary d on d.id=dm.diary_id where (dm.validitydate>current_date or dm.validitydate=current_date and dm.validitytime>=current_time) and d.username='"+login+"' and d.id='"+aDiary+"'") ;
		if (!list.isEmpty()) {
			Object[] obj=list.get(0) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("update diary set record='").append(obj[1]).append("',editdate=current_date,edittime=current_time where id=").append(aDiary);
			service.executeUpdateNativeSql(sql.toString()) ;
			service.executeUpdateNativeSql("update diarymessage dm set IsDoctorCheck='1' where dm.diary_id="+aDiary) ;
		}
		return "" ;
	}
	
	
	public String getDiaryDefects(Long aDiaryId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res = new StringBuilder();
		String req = "select vdd.id, vdd.name from VocDefectDiary vdd " +
				"order by vdd.id ";
		List<Object[]> list = service.executeNativeSqlGetObj(req) ;
		for (Object[] obj : list) {
			res.append(obj[0]).append(":").append(obj[1]).append("#");
		}
		
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	public String getDiaryText(Long aDiaryId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		List<Object[]> list = service.executeNativeSqlGetObj("select id,record from diary where id="+aDiaryId) ;
		return list.isEmpty() ? "" : list.get(0)[1].toString() ;
	}
	public String setDiaryDefect(Long aDiaryId, Long aDefectId, String aComment,String aRecord,String aVk, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Calendar cal = Calendar.getInstance() ;
		cal.add(Calendar.HOUR, 24) ;
		boolean vk = aVk!=null && aVk.equals("1") ;
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy") ;
		sql.append("insert into DiaryMessage (isVk,diary_id,defect_id,comment,record,createusername,createdate,createtime,validitydate,validitytime) ")
			.append("values ('").append(vk?"1":"0").append("','").append(aDiaryId).append("','")
			.append(aDefectId).append("','").append(aComment).append("','").append(aRecord)
			.append("','").append(login).append("',current_date,current_time,to_date('").append(f.format(cal.getTime())).append("','dd.mm.yyyy'),current_time)") ;
		service.executeUpdateNativeSql(sql.toString()) ;
		List<Object[]> list = service.executeNativeSqlGetObj("select d.id,d.username,to_char(d.dateregistration,'dd.mm.yyyy')||' '||pat.lastname from diary d left join medcase mc on mc.id=d.medcase_id left join patient pat on pat.id=mc.patient_id where d.id='"+aDiaryId+"'") ;
		if (!list.isEmpty()) {
			Object[] obj = list.get(0) ;
			String username=""+obj[1] ;
			
			sql = new StringBuilder() ;
			
			sql.append("insert into CustomMessage (messageText,messageTitle,recipient")
				.append(",dispatchDate,dispatchTime,username,validityDate,messageUrl)") 
				.append("values ('").append("На исправление дневник").append("','")
				.append(obj[2]).append("','").append(username)
				.append("',current_date,current_time,'").append(login).append("',current_date,'")
				.append("js-stac_slo-list_edit_protocol.do?id=").append(aDiaryId).append("')") ;
			service.executeUpdateNativeSql(sql.toString()) ;
		}
		return "1" ;
	}

	/**Возвращаем список дневников, исследований, лаб. анализов по госпитализации */
	public String getDiariesByHospital(Long aMedcaseId, String aServiceType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String addSql = "LABSURVEY".equalsIgnoreCase(aServiceType) ? " and vst.code='LABSURVEY'" : "" ;
		String sql = "select d.id as id, to_char(d.dateregistration,'dd.MM.yyyy') as recordDate"+
			" ,to_char(d.timeregistration,'HH24:MI') as recordTime,"+
			" list(mc.code) as serviceCode, list(mc.name) as serviceName"+
			" ,d.record as recordText"+
			" from medcase sls " +
			" left join medcase vis on vis.patient_id=sls.patient_id" +
			" left join medcase servmc on servmc.parent_id=vis.id"+
			" left join medservice mc on mc.id=servmc.medservice_id" + 
			" left join diary d on d.medcase_id= vis.id" +
			" left join VocServiceType vst on vst.id=mc.serviceType_id" +
			" where sls.id="+aMedcaseId +
			" and d.dtype='Protocol'" +
			" and (vis.dtype='Visit' or vis.dtype='HospitalMedCase' or vis.dtype='DepartmentMedCase')" +
			" and d.dateregistration is not null and d.dateregistration>=sls.datestart" +
			" and case when vis.dtype='Visit' and vis.parent_id!=sls.id then (select case when vwf.isNoDiagnosis='1' then '1' else '0' end from medcase v" +
			" left join workfunction wf on wf.id=v.workfunctionexecute_id" +
			" left join vocworkfunction vwf on vwf.id=wf.workfunction_id where v.id=vis.id) else '1' end = '1'" +
			addSql +
			" group by d.id, d.dateregistration, d.timeregistration, d.record " +
			" order by d.dateregistration desc, d.timeregistration desc";
		return service.executeNativeSqlGetJSON(new String[]{"id", "recordDate","recordTime","serviceCode","serviceName","recordText"}, sql,null);
	}
	
	public String getPrefixByProtocol(Long aDiaryId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select vtp.prefixprint from Diary d left join voctypeprotocol vtp on vtp.id=d.type_id where d.id="+aDiaryId+" and vtp.prefixprint is not null") ;
        return list.isEmpty() ? null : list.iterator().next().get1().toString() ;
	}
	public String viewTable263sls(Long aHDFid,String aPreHospDate,String aLastname,String aFirstname, String aMiddlename, String aBirthday, String aMode, String aDenied, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder sql = new StringBuilder() ; 
		sql.append(" select sls.id as f1slsid,pat.lastname||' '||pat.firstname||' '||pat.middlename") ; 
		sql.append(" 	||' '||to_char(pat.birthday,'dd.mm.yyyy') as f2fio") ;
		sql.append(" ,case when sls.emergency='1' then 'экстр' else 'план' end as e3mer") ;
		sql.append(" ,vbt.codeF||' '||vbt.name as f4") ;
		sql.append(" ,to_char(sls.dateStart,'dd.mm.yyyy') as f5") ;
		sql.append(" ,to_char(sls.orderdate,'dd.mm.yyyy') as o6rderdate, olpu.codef||' '||olpu.name as o7lpuname") ;
		sql.append(" ,pat.phone as p8,(select list(mkb.code) from diagnosis diag left join VocIdc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4') as f9") ;
		sql.append(" ,ss.code as f10") ;
		sql.append(" ,case when vdh.id is not null then vdh.name when hdf.id is not null then 'Исп. в др. напр.'") ;
		sql.append(" when vss.code not in ('OBLIGATORYINSURANCE','OTHER') then vss.name else null end f11") ;
		sql.append(" from medcase sls ") ;
		sql.append(" left join MisLpu lpu on lpu.id=sls.lpu_id") ;
		sql.append(" left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'") ;
		sql.append(" left join BedFund bf on bf.id=slo.bedFund_id") ;
		sql.append(" left join MisLpu olpu on olpu.id=sls.orderlpu_id") ;
		sql.append(" left join MisLpu ml on ml.id=slo.department_id") ;
		sql.append(" left join VocBedType vbt on vbt.id = bf.bedType_id") ;
		sql.append(" left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id") ;
		sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id") ;
		sql.append(" left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id") ;
		sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id") ;
		sql.append(" left join Patient pat on pat.id=sls.patient_id") ;
		sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id") ;
		sql.append(" left join MisLpu oml on oml.id=sls.orderLpu_id") ;
		sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('").append(f.format(ConvertSql.parseDate(aPreHospDate, -7))).append("','dd.mm.yyyy') and to_date('").append(f.format(ConvertSql.parseDate(aPreHospDate, 28))).append("','dd.mm.yyyy') and hdf.id is null") ;
		sql.append(" and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')") ;
		sql.append(" ") ;
		sql.append(" ") ;

		if (aDenied==null||aDenied.equals("1")) {
			sql.append(" and sls.deniedHospitalizating_id is null") ;
		} else if (aDenied.equals("2")) {
			sql.append(" and sls.deniedHospitalizating_id is not null") ;
		}
		if (aMode==null||aMode.equals("1")) {
			sql.append(" and pat.lastname='").append(aLastname).append("'") ;
			sql.append(" and pat.firstname='").append(aFirstname).append("'") ;
			sql.append(" and pat.middlename='").append(aMiddlename).append("'") ;
			sql.append(" and pat.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy')") ;
		} else if (aMode.equals("2")) {
			sql.append(" and pat.lastname='").append(aLastname).append("'") ;
		} else if (aMode.equals("3")) {
			sql.append(" and pat.firstname='").append(aFirstname).append("'") ;
		} else if (aMode.equals("4")) {
			sql.append(" and coalesce(sls.datestart)=to_date('").append(aPreHospDate).append("','dd.mm.yyyy')") ;
		}
		sql.append(" order by pat.lastname,pat.firstname,pat.middlename,sls.dateStart") ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append("<table border='1'><tr>") ;
		ret.append("<th></th>") ;
		ret.append("<th>Примечание</th>") ;
		ret.append("<th>ФИО</th>") ;
		ret.append("<th>Показания</th>") ;
		ret.append("<th>Профиль</th>") ;
		ret.append("<th>Дата госп.</th>") ;
		ret.append("<th>Дата напр.</th>") ;
		ret.append("<th>ЛПУ напр.</th>") ;
		ret.append("<th>Телефон</th>") ;
		ret.append("<th>Диагноз</th>") ;
		ret.append("<th>Стат.карта</th>") ;
		ret.append("</tr>") ;
		for (WebQueryResult wqr : l) {
			ret.append("<tr>") ;
			if (wqr.get11()==null) {
				ret.append("<td><input type='button' value='Выбор' title='Установить соответствие с этой госпитализаций' onclick=\"setHospByHDF('").append(aHDFid).append("','").append(wqr.get1()).append("')\"></td>") ;
			} else {
				ret.append("<td><input type='button' value='Отказ' title='Установить отказ от госпитализации' onclick=\"showDiag263denied(").append(aHDFid).append(")\"></td>") ;
			}
			ret.append("<td>").append(wqr.get11()!=null?wqr.get11():"").append("</td>") ;
			ret.append("<td>").append(wqr.get2()!=null?wqr.get2():"").append("</td>") ;
			ret.append("<td>").append(wqr.get3()!=null?wqr.get3():"").append("</td>") ;
			ret.append("<td>").append(wqr.get4()!=null?wqr.get4():"").append("</td>") ;
			ret.append("<td>").append(wqr.get5()!=null?wqr.get5():"").append("</td>") ;
			ret.append("<td>").append(wqr.get6()!=null?wqr.get6():"").append("</td>") ;
			ret.append("<td>").append(wqr.get7()!=null?wqr.get7():"").append("</td>") ;
			ret.append("<td>").append(wqr.get8()!=null?wqr.get8():"").append("</td>") ;
			ret.append("<td>").append(wqr.get9()!=null?wqr.get9():"").append("</td>") ;
			ret.append("<td>").append(wqr.get10()!=null?wqr.get10():"").append("</td>") ;
			
			ret.append("</tr>") ;
		}
		ret.append("</table>") ;
		return ret.toString() ;
	}
	public String deleteTableHDFEmpty(String aMode,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		if (aMode==null || aMode.equals("1")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where isTable4='1'") ;
		} else if (aMode.equals("2")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2 is null or isTable2='0') and (isTable3 is null or isTable3='0') and (isTable4 is null or isTable4='0') and (isTable5 is null or isTable5='0')") ;
		} else if (aMode.equals("3")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2='1' or isTable3='1') and (isTable5 is null or isTable5='0')") ;
		}
		return null;
	}
	public String deleteHDF(String aNapr,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("delete from HospitalDataFond where id='"+aNapr+"'") ;
		return null;
	}
	public String getInfoByHDF(Long aHDF, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select (select min(lpu.id||'###@###'||lpu.name) from mislpu lpu where lpu.codef=hdf.OrderLpuCode) as orderLpuCode") ;
		sql.append(", hdf.numberFond as numberFond") ;
		sql.append(", to_char(hdf.directDate,'dd.mm.yyyy') as directDate") ;
		sql.append(", (select min(mkb.id||'###@###'||mkb.code||' '||mkb.name) from vocidc10 mkb where mkb.code=hdf.Diagnosis) as mkb") ;
		sql.append(" from hospitaldatafond hdf where hdf.id='").append(aHDF).append("'") ;
		List<Object[]> r = service.executeNativeSqlGetObj(sql.toString()) ;
		if (r.isEmpty()) {
			return null ;
		} else {
			Object[] o = r.get(0) ;
			StringBuilder res = new StringBuilder() ;
			if (o[0]==null) {res.append("###@###") ;}else{res.append(o[0]);}
			res.append("###@###") ;
			if (o[1]!=null) {res.append(o[1]);}
			res.append("###@###") ;
			if (o[2]!=null) {res.append(o[2]);}
			res.append("###@###") ;
			if (o[3]==null) {res.append("###@###") ;}else{res.append(o[3]);}
			return res.toString() ;
		}
		
	}
	public String viewTable263narpByPat(Long aPATid,String aPreHospDate, String aMode, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sqlP = new StringBuilder() ;
		sqlP.append("select lastname,firstname,middlename,to_char(birthday,'dd.mm.yyyy') from patient where id='").append(aPATid).append("'") ;
		List<Object[]> lP=service.executeNativeSqlGetObj(sqlP.toString()) ;
		if (lP.isEmpty()) return "###@###" ;
		Object[] pat = lP.get(0) ;
		String lastname=ConvertSql.parseString(pat[0]) ;
		String firstname=ConvertSql.parseString(pat[1]) ;
		String middlename=ConvertSql.parseString(pat[2]) ;
		String birthday=ConvertSql.parseString(pat[3]) ;
		StringBuilder sql = new StringBuilder() ; 
		sql.append(" select hdf.id,hdf.numberfond,hdf.lastname||' '||hdf.firstname||' '||hdf.middlename") ; 
		sql.append(" 	||' '||to_char(hdf.birthday,'dd.mm.yyyy') as fio") ;
		sql.append(" ,hdf.formHelp") ;
		sql.append(" ,hdf.profile as f5") ;
		sql.append(" ,to_char(coalesce(hdf.prehospdate,hdf.hospdate),'dd.mm.yyyy') as prehospdate") ;
		sql.append(" ,hdf.directdate,hdf.snils as f8snils") ;
		sql.append(" ,hdf.phone,hdf.diagnosis") ;
		sql.append(" ,hdf.orderlpucode") ;
		sql.append(" ,hdf.directlpucode") ;
		sql.append(" ,hdf.statcard as f13") ;
		sql.append(" ,hdf.deniedHospital") ;
		sql.append(" ,''''||hdf.id||''','''||coalesce(''||hdf.deniedHospital,'')||'''' as idden") ;
		sql.append(" from HospitalDataFond hdf") ;
		sql.append(" where hdf.hospitalMedCase_id is null and (case when  hdf.isTable4 ='1' then '1' when hdf.IsTable4='1' then '1' else null end) is null") ;
		sql.append(" and hdf.deniedHospital is null") ;
		if (aMode==null||aMode.equals("1")) {
			sql.append(" and hdf.lastname='").append(lastname).append("'") ;
			sql.append(" and hdf.firstname='").append(firstname).append("'") ;
			sql.append(" and hdf.middlename='").append(middlename).append("'") ;
			sql.append(" and hdf.birthday=to_date('").append(birthday).append("','dd.mm.yyyy')") ;
		} else if (aMode.equals("2")) {
			sql.append(" and hdf.lastname='").append(lastname).append("'") ;
		} else if (aMode.equals("3")) {
			sql.append(" and hdf.firstname='").append(firstname).append("'") ;
		} else if (aMode.equals("4")) {
			sql.append(" and coalesce(hdf.prehospdate,hdf.hospdate)=to_date('").append(aPreHospDate).append("','dd.mm.yyyy')") ;
		}
		sql.append(" order by hdf.lastname,hdf.firstname,hdf.middlename,hdf.id") ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append(lastname).append(" ");
		ret.append(firstname).append(" ");
		ret.append(middlename).append(" ");
		ret.append(birthday);
		ret.append("###@###") ;
		ret.append("<table border='1'><tr style='padding:10px'>") ;
		ret.append("<th></th>") ;
		ret.append("<th>№ по фонду</th>") ;
		ret.append("<th>ФИО</th>") ;
		ret.append("<th>Форма помощи</th>") ;
		ret.append("<th>Профиль</th>") ;
		ret.append("<th>Пред. дата госп.</th>") ;
		ret.append("<th>Диагноз</th>") ;
		ret.append("<th>ЛПУ направителя</th>") ;
		ret.append("</tr>") ;
		for (WebQueryResult wqr : l) {
			ret.append("<tr>") ;
			ret.append("<td><input type='button' value='Выбор' title='Установить соответствие с этим направлением' onclick=\"setHospByHDF('").append(wqr.get1()).append("','").append(aPATid).append("')\"></td>") ;
			ret.append("<td>").append(wqr.get2()).append("</td>") ;
			ret.append("<td>").append(wqr.get3()).append("</td>") ;
			ret.append("<td>").append(wqr.get4()).append("</td>") ;
			ret.append("<td>").append(wqr.get5()).append("</td>") ;
			ret.append("<td>").append(wqr.get6()).append("</td>") ;
			ret.append("<td>").append(wqr.get10()).append("</td>") ;
			ret.append("<td>").append(wqr.get11()).append("</td>") ;
			ret.append("</tr>") ;
		}
		ret.append("</table>") ;
		return ret.toString() ;
	}
	public String viewTable263narp(Long aSLSid,String aPreHospDate,String aLastname,String aFirstname, String aMiddlename, String aBirthday, String aMode, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ; 
		sql.append(" select hdf.id,hdf.numberfond,hdf.lastname||' '||hdf.firstname||' '||hdf.middlename") ; 
		sql.append(" 	||' '||to_char(hdf.birthday,'dd.mm.yyyy') as fio") ;
		sql.append(" ,hdf.formHelp") ;
		sql.append(" ,hdf.profile as f5") ;
		sql.append(" ,coalesce(hdf.prehospdate,hdf.hospdate)") ;
		sql.append(" ,hdf.directdate,hdf.snils as f8snils") ;
		sql.append(" ,hdf.phone,hdf.diagnosis") ;
		sql.append(" ,hdf.orderlpucode") ;
		sql.append(" ,hdf.directlpucode") ;
		sql.append(" ,hdf.statcard as f13") ;
		sql.append(" ,hdf.deniedHospital") ;
		sql.append(" ,''''||hdf.id||''','''||coalesce(''||hdf.deniedHospital,'')||'''' as idden") ;
		sql.append(" from HospitalDataFond hdf") ;
		sql.append(" where hdf.hospitalMedCase_id is null and (case when  hdf.isTable4 ='1' then '1' when hdf.IsTable4='1' then '1' else null end) is null") ;
		sql.append(" and hdf.deniedHospital is null") ;
		if (aMode==null||aMode.equals("1")) {
			sql.append(" and hdf.lastname='").append(aLastname).append("'") ;
			sql.append(" and hdf.firstname='").append(aFirstname).append("'") ;
			sql.append(" and hdf.middlename='").append(aMiddlename).append("'") ;
			sql.append(" and hdf.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy')") ;
		} else if (aMode.equals("2")) {
			sql.append(" and hdf.lastname='").append(aLastname).append("'") ;
		} else if (aMode.equals("3")) {
			sql.append(" and hdf.firstname='").append(aFirstname).append("'") ;
		} else if (aMode.equals("4")) {
			sql.append(" and coalesce(hdf.prehospdate,hdf.hospdate)=to_date('").append(aPreHospDate).append("','dd.mm.yyyy')") ;
		}
		sql.append(" order by hdf.lastname,hdf.firstname,hdf.middlename,hdf.id") ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append("<table border='1'><tr>") ;
		ret.append("<th></th>") ;
		ret.append("<th></th>") ;
		ret.append("<th>№ по фонду</th>") ;
		ret.append("<th>ФИО</th>") ;
		ret.append("<th>Форма помощи</th>") ;
		ret.append("<th>Профиль</th>") ;
		ret.append("<th>Пред. дата госп.</th>") ;
		ret.append("</tr>") ;
		for (WebQueryResult wqr : l) {
			ret.append("<tr>") ;
			ret.append("<td><input type='button' value='Выбор' title='Установить соответствие с этим направлением' onclick=\"setHospByHDF('").append(wqr.get1()).append("','").append(aSLSid).append("')\"></td>") ;
			ret.append("<td><input type='button' value='Отказ' title='Установить отказ от госпитализации' onclick=\"showDiag263denied(").append(wqr.get15()).append(")\"></td>") ;
			ret.append("<td>").append(wqr.get2()).append("</td>") ;
			ret.append("<td>").append(wqr.get3()).append("</td>") ;
			ret.append("<td>").append(wqr.get4()).append("</td>") ;
			ret.append("<td>").append(wqr.get5()).append("</td>") ;
			ret.append("<td>").append(wqr.get6()).append("</td>") ;
			ret.append("</tr>") ;
		}
		ret.append("</table>") ;
		return ret.toString() ;
	}
	public String updateTable(String aTable, String aFldId, String aValId, String aFldSet, String aValSet, String aWhereAdd, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String add = aWhereAdd!=null &&!aWhereAdd.equals("")?" and "+aWhereAdd:"" ;
		if (aValSet==null || aValSet.equals("null")) {
			service.executeUpdateNativeSql("update "+aTable+" set "+aFldSet+"=null where "+aFldId+"='"+aValId+"' "+add) ;
		} else {
			service.executeUpdateNativeSql("update "+aTable+" set "+aFldSet+"='"+aValSet+"' where "+aFldId+"='"+aValId+"' "+add) ;
		}
		return "" ;
	}
	public String isCanDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		StringBuilder sql = new StringBuilder() ; 
		sql.append("select slo.id as sloid,ml.name||' '||to_char(slo.dateStart,'dd.mm.yyyy')||coalesce('-'||to_char(slo.transferDate,'dd.mm.yyyy'),'') as info from medcase sls ")
				.append(" left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'")
				.append(" left join mislpu ml on ml.id=slo.department_id")
				.append(" left join diagnosis d on slo.id = d.medcase_id")
				.append(" left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id")
				.append(" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id").append(" where sls.id='").append(aMedCaseId).append("' and (ml.isnoomc is null or ml.isnoomc='0') ")
			.append(" group by sls.id,slo.id,ml.name,slo.dateStart,slo.transferDate	")
			.append(" having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  ") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		if (!list.isEmpty()) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("Не полностью заполнены данные по диагнозам в отделениях!!! ") ;
			for (WebQueryResult wqr:list) {
				ret.append(" <a href='entitySubclassView-mis_medCase.do?id=")
					.append(wqr.get1())
					.append("' onclick='return  msh.util.FormData.getInstance().isChangedForLink() ;'>" )
					.append(wqr.get2())
					.append("</a>") ;
			}
			return ret.toString() ;
		}
		return null ;
	}
	
	public String getYesNo(Long aYesNoId, HttpServletRequest aRequest) throws NamingException {
		if (aYesNoId != null && !aYesNoId.equals(0L)) {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list=service.executeNativeSql("select code from vocyesno where id='"+aYesNoId+"' ",1) ;
			if (!list.isEmpty()) {
				return ""+list.iterator().next().get1() ;
			}
		}
		return "-1" ;
	}
	public static Object getDefaultParameterByConfig(String aParameter, String aValueDefault, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'");
		if (l.isEmpty()) {
			return aValueDefault ;
		} else {
			return l.iterator().next().get2() ;
		}
		
	}
	
	public static String getDataByReference(Long aMedCase,String aType, HttpServletRequest aRequest) throws Exception {
		return getDataByReferencePrint( aMedCase, aType, false, aRequest) ;
	}
	public static String getDataByReferenceUrl(Long aMedCase,String aType, HttpServletRequest aRequest) throws Exception {
		return getDataByReferencePrint( aMedCase, aType, true, aRequest) ;
	}
	public static String getDataByReferencePrint(Long aMedCase,String aType, boolean aIsUrl, HttpServletRequest aRequest) throws Exception {
		return getDataByReferencePrintNotOnlyOMS(aMedCase, aType, aIsUrl,"",aRequest);
	}
	public static String getDataByReferencePrintNotOnlyOMS(Long aMedCase,String aType, boolean aIsUrl, String aSqlAdd, HttpServletRequest aRequest) throws Exception {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		//ISoftConfigService sservice = Injection.find(aRequest).getService(ISoftConfigService.class) ;
		//StringBuilder url = new StringBuilder() ;
		String code_page = ""+getDefaultParameterByConfig("code_csp_page", "CP1251", aRequest) ;
		String address_lpu = ""+getDefaultParameterByConfig("address_lpu", "_______________________________________________________", aRequest) ;
		String name_lpu = ""+getDefaultParameterByConfig("name_lpu", "_______________________________________________________", aRequest) ;
		name_lpu = name_lpu.replaceAll("\"", "%22");
		//url.append(aRequest.getServerName()) ;
		Object csp_port=getDefaultParameterByConfig("csp_post", "57772", aRequest) ;
		
		/*if (aRequest.getServerPort()>1000) {
			//url.append(!csp_port.equals("")?(":"+csp_port):"").append(aRequest.getServerPort()) ;
		}*/
		String cspurl = "expert"+(!csp_port.equals("")?(":"+csp_port):"")
				+"/csp/"+getDefaultParameterByConfig("data_base_namespace", "riams", aRequest) ;
		StringBuilder href = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		res.append(param("address_lpu",address_lpu)) ;
		res.append(param("name_lpu",name_lpu)) ;
		if (aSqlAdd!=null) {
			if (!aSqlAdd.equals("")) {
				aSqlAdd=" or vss.code in ('"+aSqlAdd+"') ";
			}
		} else {aSqlAdd = "";}
		boolean isf = true ;
		if ("HOSP".equals(aType)) {
			//Дополнительная диспансеризация
			href.append(param("AddDisp",null)) ;
			href.append(param("AddDispAge",null)) ;
			href.append(param("AddDispCases",null)) ;
			href.append(param("AddDispHealthGroup",null)) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select to_char(sls.dateStart,'yyyymmdd') as datestart");
			sql.append(" ,to_char(sls.dateFinish,'yyyymmdd') as dateFinish");
			sql.append(" ,to_char(pat.birthday,'yyyymmdd') as birthday");
			sql.append(" ,vs.omcCode as vsomccode");
			sql.append(" ,ml.omccode as mlomccode");
			sql.append(" ,case when sls.hotelServices='1' then '1' else '0' end as hotelserv");
			sql.append(" ,case when vrd1.code='1' then vhr.code when vrd.code='DIS_PAT' then '16' when vrd.code='DIS_LPU' then '17' when vho.code='2' then '5' when vho.code='3' then '4' when vho.code='4' then '14' when vhr.code is null then '2' else vhr.code end as result");
			sql.append(" ,case when pvss.code='И0' then '1' else '0' end as foreign");
			sql.append(" , to_char(sls.dateStart,'dd.mm.yyyy') as date5start");
			sql.append(" ,to_char(sls.dateFinish,'dd.mm.yyyy') as date5Finish");
			sql.append(" ,sls.patient_id as patient");
			sql.append(" ,coalesce(k.kinsman_id,sls.patient_id) as kinsman");
			sql.append(" ,mp.series||' '||mp.polNumber as polnumber");
			sql.append(" ,ss.code as sscode");
			
			sql.append(" from MedCase sls ");
			sql.append(" left join Kinsman k on k.id=sls.kinsman_id") ;
			sql.append(" left join Patient pat on pat.id=sls.patient_id") ;
			sql.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql.append(" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id") ;
			sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id") ;
			sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id") ;
			sql.append(" left join VocReasonDischarge vrd on vrd.id=ss.reasonDischarge_id") ;
			sql.append(" left join VocResultDischarge vrd1 on vrd1.id=ss.resultDischarge_id") ;
			sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id") ;
			sql.append(" left join medpolicy mp on mcmp.policies_id=mp.id") ;
			sql.append(" left join VocHospitalizationResult vhr on vhr.id=sls.result_id") ;
			sql.append(" left join VocHospitalizationOutcome vho on vho.id=sls.outcome_id") ;
			sql.append(" left join MisLpu ml on ml.id=sls.lpu_id") ;
			sql.append(" where sls.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' ").append(aSqlAdd).append(") and sls.dischargeTime is not null") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
			if (l.isEmpty()) throw new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ПО ВЫПИСАННЫМ ОМС БОЛЬНЫМ!!!") ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("BirthDate",wqr.get3())) ;
			href.append(param("Lpu",wqr.get5())) ;
			href.append(param("Foreigns",wqr.get8())) ;
			href.append(param("HotelServices",wqr.get6())) ;
			href.append(param("Sex",wqr.get4())) ;
			href.append(param("Reason",null)) ;
			href.append(param("ReasonC",null)) ;
			href.append(param("Render",null)) ;
			href.append(param("Result",wqr.get7())) ;
			href.append(param("Yet",null)) ;
			res.append("&dateFrom=").append(wqr.get9()).append("&dateTo=").append(wqr.get10())
				.append("&patient=").append(wqr.get11())
				.append("&kinsman=").append(wqr.get12())
				.append("&polNumber=").append(wqr.get13())
				.append("&card=").append(wqr.get14())
				;
			StringBuilder sql_bt = new StringBuilder() ;
			sql_bt.append("select to_char(min(slo.dateStart),'yyyymmdd') as datestart");
			sql_bt.append(" ,to_char(max(coalesce(slo.dateFinish,slo.transferDate)),'yyyymmdd') as dateFinish");
			sql_bt.append(" ,max(case when slo.emergency='1' then '1' else '0' end) as emergency");
			sql_bt.append(" ,list(case when ms.isNoOmc='1' then null else vms.code end) as operation ") ;
			sql_bt.append(" ,list(distinct mkb.code) as diagnosis ") ;
			sql_bt.append(" ,vlf.code as vlfcode") ;
			sql_bt.append(" ,list(distinct case when ml.isnoomc='1' then null else vbt.omccode end) as vbtcode") ;
			sql_bt.append(" ,list(distinct case when ml.isnoomc='1' then null else vbst.code end) as vbstfcode") ;
			sql_bt.append(" ,sum(case when ml.isnoomc='1' then 0 else case when (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)=0 then 1") 
               .append(" when bf.addCaseDuration='1' then (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)+1")
               .append(" else (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)")
               .append(" end end) as beddays") ;
			sql_bt.append(" ,list(case when ml.isnoomc='1' then null else vodp.code end) as vodpcode") ;
			sql_bt.append(" ,list(case when ml.isnoomc='1' then null else vodt.code end) as vodtcode") ;
			sql_bt.append(" ,list(distinct case when vpd.code='1' and vdrt.code='4' and (ml.isnoomc='0' or ml.isnoomc is null) then mkb.code else null end) as diag1nosis ") ;
			sql_bt.append(" ,list(distinct case when vpd.code='3' and vdrt.code='4' and (ml.isnoomc='0' or ml.isnoomc is null) then mkb.code else null end) as diag3nosis ") ;
			sql_bt.append(" ,coalesce(max(vkhc.code),'') as vkhccode");
			sql_bt.append(", to_char(min(slo.dateStart),'dd.mm.yyyy') as date5start");
			sql_bt.append(" ,to_char(max(coalesce(slo.dateFinish,slo.transferdate)),'dd.mm.yyyy') as date5Finish") ;
			sql_bt.append(" ,list(distinct ml.name) as mlname") ;
			sql_bt.append(" ,case when vbst.code='2' then case when vlf.code='2' or vlf.code='4' then 'APUHOSP' else 'LPUHOSP' end when vbst.code='3' then 'HOMEHOSP' else 'HOSP' end as vidLpu") ;
			sql_bt.append(" ,case when ml.isnoomc='1' then null else 'HOSP' end as noprint") ;
			sql_bt.append(" from MedCase slo ");
			sql_bt.append(" left join VocKindHighCare vkhc on vkhc.id=slo.kindHighCare_id") ;
			sql_bt.append(" left join WorkFunction wf on wf.id=slo.ownerFunction_id") ;
			sql_bt.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id") ;
			sql_bt.append(" left join VocPost vp on vp.id=vwf.vocPost_id") ;
			sql_bt.append(" left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id") ;
			sql_bt.append(" left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id") ;
			sql_bt.append(" left join BedFund bf on bf.id=slo.bedFund_id") ;
			sql_bt.append(" left join VocBedType vbt on vbt.id=bf.bedType_id") ;
			sql_bt.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id") ;
			sql_bt.append(" left join Diagnosis diag on diag.medCase_id=slo.id") ;
			sql_bt.append(" left join VocDiagnosisRegistrationType vdrt on diag.registrationType_id=vdrt.id") ;
			sql_bt.append(" left join VocPriorityDiagnosis vpd on diag.priority_id=vpd.id") ;
			sql_bt.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
			sql_bt.append(" left join SurgicalOperation so on so.medCase_id=slo.id") ;
			sql_bt.append(" left join MedService ms on so.medService_id=ms.id") ;
			sql_bt.append(" left join VocMedService vms on ms.vocMedService_id=vms.id") ;
			sql_bt.append(" left join Patient pat on pat.id=slo.patient_id") ;
			sql_bt.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql_bt.append(" left join VocSocialStatus vss on vss.id=pat.sex_id") ;
			sql_bt.append(" left join MisLpu ml on ml.id=slo.department_id") ;
			sql_bt.append(" left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id") ;
			sql_bt.append(" where slo.parent_id=").append(aMedCase) ;
			sql_bt.append(" group by vbst.code,bf.addCaseDuration,vbt.code,vlf.code,ml.isnoomc") ;
			//sql_bt.append(" order by slo.dateStart,coalesce(slo.dateFinish,slo.transferDate)") ;
			
			
			sql = new StringBuilder() ;
			sql.append("select to_char(slo.dateStart,'yyyymmdd') as datestart");
			sql.append(" ,to_char(slo.dateFinish,'yyyymmdd') as dateFinish");
			sql.append(" ,case when slo.emergency='1' then '1' else '0' end as emergency");
			sql.append(" ,list(case when ms.isNoOmc='1' then null else vms.code end) as operation ") ;
			sql.append(" ,list(mkb.code) as diagnosis ") ;
			sql.append(" ,vlf.code as vlfcode") ;
			sql.append(" ,vbt.omccode as vbtcode") ;
			sql.append(" ,vbst.code as vbstfcode") ;
			sql.append(" ,case when (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)=0 then 1") 
               .append(" when bf.addCaseDuration='1' then (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)+1")
               .append(" else (coalesce(slo.dateFinish,slo.transferDate)-slo.dateStart)")
               .append(" end as beddays") ;
			sql.append(" ,vodp.code as vodpcode") ;
			sql.append(" ,vodt.code as vodtcode") ;
			sql.append(" ,list(distinct case when vpd.code='1' and vdrt.code='4' then mkb.code else null end) as diag1nosis ") ;
			sql.append(" ,list(distinct case when vpd.code='3' and vdrt.code='4' then mkb.code else null end) as diag3nosis ") ;
			sql.append(" ,coalesce(vkhc.code,'') as vkhccode");
			sql.append(", to_char(slo.dateStart,'dd.mm.yyyy') as date5start");
			sql.append(" ,to_char(coalesce(slo.dateFinish,slo.transferdate),'dd.mm.yyyy') as date5Finish") ;
			sql.append(" ,ml.name as mlname") ;
			sql.append(" ,case when vbst.code='2' then case when vlf.code='2' or vlf.code='4' then 'APUHOSP' else 'LPUHOSP' end when vbst.code='3' then 'HOMEHOSP' else 'HOSP' end as vidLpu") ;
			sql.append(" ,case when ml.isnoomc='1' then null else 'HOSP' end as noprint") ;
			sql.append(" from MedCase slo ");
			sql.append(" left join VocKindHighCare vkhc on vkhc.id=slo.kindHighCare_id") ;
			sql.append(" left join WorkFunction wf on wf.id=slo.ownerFunction_id") ;
			sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id") ;
			sql.append(" left join VocPost vp on vp.id=vwf.vocPost_id") ;
			sql.append(" left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id") ;
			sql.append(" left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id") ;
			sql.append(" left join BedFund bf on bf.id=slo.bedFund_id") ;
			sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id") ;
			sql.append(" left join VocBedSubType vbst on vbst.id=bf.bedSubType_id") ;
			sql.append(" left join Diagnosis diag on diag.medCase_id=slo.id") ;
			sql.append(" left join VocDiagnosisRegistrationType vdrt on diag.registrationType_id=vdrt.id") ;
			sql.append(" left join VocPriorityDiagnosis vpd on diag.priority_id=vpd.id") ;
			sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
			sql.append(" left join SurgicalOperation so on so.medCase_id=slo.id") ;
			sql.append(" left join MedService ms on so.medService_id=ms.id") ;
			sql.append(" left join VocMedService vms on ms.vocMedService_id=vms.id") ;
			sql.append(" left join Patient pat on pat.id=slo.patient_id") ;
			sql.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql.append(" left join VocSocialStatus vss on vss.id=pat.sex_id") ;
			sql.append(" left join MisLpu ml on ml.id=slo.department_id") ;
			sql.append(" left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id") ;
			sql.append(" where slo.parent_id=").append(aMedCase) ;
			sql.append(" group by slo.dateStart,slo.dateFinish,slo.transferDate,vlf.code,vbt.omccode,vbst.code,vkhc.code,bf.addCaseDuration,vodt.code,vodp.code,slo.emergency,ml.name") ;
			sql.append(" order by slo.dateStart,coalesce(slo.dateFinish,slo.transferDate)") ;
			
			Collection<WebQueryResult> l_slo = service.executeNativeSql(sql_bt.toString()) ;
			//res.append("&render=") ;
			for (WebQueryResult wqr_slo:l_slo) {
				if (wqr_slo.get19()!=null){
					
			
				StringBuilder href_slo = new StringBuilder() ;
				href_slo.append(href) ;
				href_slo.append(param("AdmissionDate",wqr_slo.get1())) ;
				href_slo.append(param("DischargeDate",wqr_slo.get2())) ;
				href_slo.append(param("BedDays",wqr_slo.get9())) ;
				href_slo.append(param("DepType",wqr_slo.get7())) ;
				href_slo.append(param("DiagnosisList",wqr_slo.get5())) ;
				href_slo.append(param("DiagnosisMain",wqr_slo.get12())) ;
				href_slo.append(param("DiagnosisConcomitant",wqr_slo.get13())) ;
				href_slo.append(param("DoctorPost",wqr_slo.get10())) ;
				href_slo.append(param("Emergency",wqr_slo.get3())) ;
				href_slo.append(param("Hts",wqr_slo.get14())) ;
				href_slo.append(param("LpuFunction",wqr_slo.get6())) ; //
				href_slo.append(param("VidLpu",wqr_slo.get18())) ;//1-stac, 2- polic
				href_slo.append(param("Operations",wqr_slo.get4())) ;
				StringBuilder url_csp = new StringBuilder() ;
				url_csp.append("http://").append(cspurl)
					.append("/getmedcasecost.csp?CacheUserName=_system&tmp=").append(Math.random()).append("&CachePassword=sys&")
					.append(href_slo) ;
				//LOG.info(url_csp) ;
				
				StringBuilder c ;
				if (aIsUrl) {
					res.append(" ").append(wqr_slo.get17()).append(" С ").append(wqr_slo.get15()).append(" ПО ").append(wqr_slo.get16()).append(" url=").append(url_csp) ;
				} else {
					String cost = ActionUtil.getContentOfHTTPPage(url_csp.toString().replaceAll(" ", ""),code_page);
					//String cost = "11#2222" ;
					//LOG.info("----cost--->"+cost) ;
					if (isf) {
						res.append("&render=") ;
						isf=false;
					} else {
						res.append("%23%23") ;
					}
					c = getRender(cost,new StringBuilder().append(wqr_slo.get17()).append(". КОЙКИ "),new StringBuilder().append(" С ").append(wqr_slo.get15()).append(" ПО ").append(wqr_slo.get16())) ;
					res.append(c.toString().replaceAll("#", "%23").replaceAll(" ", "%20")) ;
				}
				
				
				//LOG.info(res.toString()) ;
				}
			
			}
			//res.append("&render=21663%23ПУЛЬМОНОЛОГИЧЕСКОЕ ОТДЕЛЕНИЕ. КОЙКИ АЛЛЕРГОЛОГИЧЕСКИЕ. КРУГЛОСУТОЧНЫЙ СТАЦИОНАР, ПОЛНЫЙ СЛУЧАЙ,ВЗРОСЛЫЕ С 03.01.2014 ПО 14.01.2014") ;
		} else if ("PREVISIT".equals(aType)) {
			//Дополнительная диспансеризация
			href.append(param("AddDisp",null)) ;
			href.append(param("AddDispAge",null)) ;
			href.append(param("AddDispCases",null)) ;
			href.append(param("AddDispHealthGroup",null)) ;
			
			StringBuilder sql = new StringBuilder() ;
			sql.append("select to_char(coalesce(spo.dateStart,wcd.calendardate,vis.datefinish),'yyyymmdd') as f1datestart");
			sql.append(" ,to_char(case when vis.emergency='1' then coalesce(spo.datestart,wcd.calendardate,vis.datefinish) else coalesce(spo.dateFinish,spo.dateStart,wcd.calendardate,vis.datefinish) end,'yyyymmdd') as f2dateFinish");
			sql.append(" ,to_char(pat.birthday,'yyyymmdd') as f3birthday");
			sql.append(" ,vs.omcCode as f4vsomccode");
			sql.append(" ,spo.id as f5spo");
			sql.append(" ,case when pvss.code='И0' then '1' else '0' end as f6foreign");
			sql.append(" , to_char(coalesce(spo.dateStart,wcd.calendardate,vis.datefinish),'dd.mm.yyyy') as f7date5start");
			sql.append(" ,to_char(case when vis.emergency='1' then coalesce(spo.datestart,wcd.calendardate,vis.datefinish) else coalesce(spo.dateFinish,spo.dateStart,wcd.calendardate,vis.datefinish) end,'dd.mm.yyyy') as f8date5Finish");
			sql.append(" ,vis.patient_id as f9patient");
			sql.append(" ,coalesce(k.kinsman_id,vis.patient_id) as f10kinsman");
			sql.append(" ,mp.series||' '||mp.polNumber as f11policy") ;
			sql.append(" ,coalesce(card.number,pat.patientSync) as f12patientcode") ;
			sql.append(" ,case when vis.emergency='1' or spo.datefinish is null or vis.datestart is null or spo.datefinish-spo.datestart=0 then 1 else spo.datefinish-spo.datestart+1 end as f13beddays") ;
			sql.append(" from MedCase vis ");
			sql.append(" left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id") ;
			sql.append(" left join Medcard card on card.id=vis.medcard_id") ;
			sql.append(" left join MedCase spo on spo.id=vis.parent_id") ;
			sql.append(" left join Kinsman k on k.id=vis.kinsman_id") ;
			sql.append(" left join Patient pat on pat.id=vis.patient_id") ;
			sql.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql.append(" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id") ;
			sql.append(" left join VocServiceStream vss on vss.id=vis.serviceStream_id") ;
			sql.append(" left join MedPolicy mp on mp.patient_id=pat.id") ;
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' ").append(aSqlAdd).append(") and mp.actualdatefrom <=coalesce(vis.dateStart,wcd.calendardate,vis.datefinish) and coalesce(mp.actualdateto,vis.datestart,wcd.calendardate,vis.datefinish)>=coalesce(vis.datestart,wcd.calendardate,vis.datefinish) and mp.dtype like 'MedPolicyOm%'");
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) throw new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("BirthDate",wqr.get3())) ;
			
			href.append(param("Foreigns",wqr.get6())) ;
			href.append(param("HotelServices",null)) ;
			href.append(param("Sex",wqr.get4())) ;
			href.append(param("VidLpu","AMB")) ;//1-stac, 2- polic
			href.append(param("AdmissionDate",wqr.get1())) ;
			href.append(param("DischargeDate",wqr.get2())) ;
			href.append(param("BedDays",wqr.get13())) ;
			res.append("&dateFrom=").append(wqr.get7()).append("&dateTo=").append(wqr.get8())
			.append("&patient=").append(wqr.get9())
			.append("&kinsman=").append(wqr.get10())
			.append("&polNumber=").append(wqr.get11())
			.append("&card=").append(wqr.get12())
			;
			sql = new StringBuilder() ;
			sql.append(" select ");
			sql.append("  case when vis.emergency='1' then '1' else '0' end as f1emergency "); 
			sql.append("  ,list(case when smc.dtype='ServiceMedService' and ms.isNoOmc='1' then null else vms.code end) as f2medservce ");  
			sql.append("  ,vlf.code as f3lpufunction  ");
			sql.append("  ,ml.name as f4mlname  ");
			sql.append("  ,vr.code as f5vrcode  ");
			sql.append("  ,vr.omccode as f6vromccode  ");
			sql.append("  ,coalesce(vvr.omccode,'3') as f7vvrcode  ");
			sql.append("  ,vis.uet as f8uet  ");
			sql.append("  ,coalesce(list(mkb.code),'Z00') as f9diagnosis   ");
			sql.append(" ,coalesce(vodp.code,vodpe.code) as f10vodpcode") ;
			sql.append(" ,coalesce(vodt.code,vodte.code) as f11vodtcode") ;
			sql.append(" ,coalesce(list(distinct mkb.code),'Z00') as f12diag1nosis ") ;
			sql.append(" ,coalesce(list(distinct case when vpd.code='1' then mkb.code else null end),'Z00') as f13diag1nosis ") ;
			sql.append(" ,coalesce(list(distinct case when vpd.code='3' then mkb.code else null end),'Z00') as f14diag3nosis ") ;
			sql.append("  ,coalesce(ml.omccode,ml1.omccode,ml2.omccode,ml3.omccode,mle.omccode,ml1e.omccode,ml2e.omccode,ml3e.omccode) as f15mlomccode  ");
			sql.append("  ,vwpt.code as f16vwptcode  ");
			sql.append("  from MedCase vis   ");
			sql.append("  left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id ");
			sql.append("  left join VocWorkPlaceType vwpt on vwpt.id=vis.workPlaceType_id ");
			sql.append("  left join MedCase spo on spo.id=vis.parent_id ");
			sql.append("  left join WorkFunction wfe on wfe.id = vis.WorkFunctionExecute_id "); 
			sql.append("  left join WorkFunction wf on wf.id = vis.WorkFunctionPlan_id "); 
			sql.append("  left join Worker w on w.id=wf.worker_id ");
			sql.append("  left join Worker we on we.id=wfe.worker_id ");
			sql.append("  left join VocWorkFunction vwf on vwf.id=wf.workFunction_id "); 
			sql.append("  left join VocPost vp on vp.id=vwf.vocPost_id  ");
			sql.append("  left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id "); 
			sql.append("  left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id  ");
			sql.append("  left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id "); 
			sql.append("  left join VocPost vpe on vpe.id=vwfe.vocPost_id  ");
			sql.append("  left join VocOmcDoctorPost vodpe on vodpe.id=vpe.omcDoctorPost_id "); 
			sql.append("  left join VocOmcDepType vodte on vodte.id=vpe.omcDepType_id  ");
			sql.append("  left join Diagnosis diag on diag.medCase_id=vis.id  ");
			sql.append("  left join VocPriorityDiagnosis vpd on diag.priority_id=vpd.id "); 
			sql.append("  left join VocIdc10 mkb on mkb.id=diag.idc10_id  ");
			sql.append("  left join MedCase smc on vis.id=smc.parent_id ");
			sql.append("  left join MedService ms on smc.medService_id=ms.id "); 
			sql.append("  left join VocMedService vms on ms.vocMedService_id=vms.id "); 
			sql.append("  left join Patient pat on pat.id=vis.patient_id  ");
			sql.append("  left join VocSex vs on vs.id=pat.sex_id  ");
			sql.append("  left join VocSocialStatus vss on vss.id=pat.sex_id "); 
			sql.append("  left join MisLpu ml on ml.id=w.lpu_id  ");
			sql.append("  left join MisLpu ml1 on ml1.id=ml.parent_id  ");
			sql.append("  left join MisLpu ml2 on ml2.id=wf.lpu_id  ");
			sql.append("  left join MisLpu ml3 on ml3.id=ml1.parent_id  ");
			sql.append("  left join MisLpu mle on mle.id=we.lpu_id  ");
			sql.append("  left join MisLpu ml1e on ml1e.id=mle.parent_id  ");
			sql.append("  left join MisLpu ml2e on ml2e.id=wfe.lpu_id  ");
			sql.append("  left join MisLpu ml3e on ml3e.id=ml1e.parent_id  ");
			sql.append("  left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id "); 
			sql.append("  left join VocReason vr on vr.id=vis.visitReason_id "); 
			sql.append("  left join VocVisitResult vvr on vvr.id=vis.visitresult_id "); 
			sql.append("  where vis.id='").append(aMedCase).append("' ");
			sql.append("  group by vis.dateStart,vis.timeExecute,ml2.omccode,ml3.omccode,vwpt.code,ml.omccode,ml1.omccode,mle.omccode,ml1e.omccode,ml2e.omccode,ml3e.omccode,vlf.code,vis.emergency,ml.name,vr.code,vr.omccode,vvr.omccode,vis.uet,vodte.code,vodpe.code,vodp.code,vodt.code ");
			sql.append("  order by vis.datestart desc,vis.timeExecute desc ");
			
			
			Collection<WebQueryResult> l_vis = service.executeNativeSql(sql.toString(),1) ;
			//res.append("&render=") ;
			for (WebQueryResult wqr_vis:l_vis) {
				StringBuilder href_vis = new StringBuilder() ;
				href_vis.append(href) ;
				href_vis.append(param("Lpu",wqr_vis.get15())) ;
				href_vis.append(param("Reason",wqr_vis.get6())) ;
				href_vis.append(param("ReasonC",wqr_vis.get5())) ;
				href_vis.append(param("Render",wqr_vis.get2())) ;
				href_vis.append(param("Result",wqr_vis.get7())) ;
				href_vis.append(param("Yet",wqr_vis.get8())) ;
				href_vis.append(param("WorkPlaceType",wqr_vis.get16())) ;
				href_vis.append(param("DepType",wqr_vis.get11())) ;
				/*href_vis.append(param("DiagnosisList",wqr_vis.get12())) ;
				href_vis.append(param("DiagnosisMain",wqr_vis.get13())) ;
				href_vis.append(param("DiagnosisConcomitant",wqr_vis.get14())) ;*/
				href_vis.append(param("DiagnosisList","Z00")) ;
				href_vis.append(param("DiagnosisMain","Z00")) ;
				href_vis.append(param("DiagnosisConcomitant","Z00")) ;
				href_vis.append(param("DoctorPost",wqr_vis.get10())) ;
				href_vis.append(param("Emergency",wqr_vis.get1())) ;
				href_vis.append(param("Hts",null)) ;
				href_vis.append(param("LpuFunction",wqr_vis.get3())) ; //
				href_vis.append(param("Operations",null)) ;
				LOG.info("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
				String cost = ActionUtil.getContentOfHTTPPage("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys&"+href_vis.toString(),code_page);
				if (isf) {
					res.append("&render=") ;
					isf=false;
				} else {
					res.append("##") ;
				}
				StringBuilder c = getRender(cost,"","") ;
				res.append(c.toString().replaceAll("#", "%23")) ;
				
				
				//res.append("0#1111".replaceAll("#", "%23")) ;
			}
			
			
			
			/*
			Collection<WebQueryResult> l = service.executeNativeSql("select to_char(sls.dateStart,'yyyymmdd') as datestart,to_char(sls.dateFinish,'yyyymmdd') as dateFinish from MedCase sls where sls.id="+aMedCase) ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("AdmissionDate",null)) ;
			href.append(param("DischargeDate",null)) ;
			href.append(param("BedDays",null)) ;
			href.append(param("BirthDate",null)) ;
			href.append(param("DepType",null)) ;
			href.append(param("DiagnosisList",null)) ;
			href.append(param("DiagnosisMain",null)) ;
			href.append(param("DiagnosisConcomitant",null)) ;
			href.append(param("DoctorPost",null)) ;
			href.append(param("Emergency",null)) ;
			href.append(param("Foreigns",null)) ;
			href.append(param("HotelServices","0")) ;
			href.append(param("Hts",null)) ;
			href.append(param("Lpu",null)) ;
			href.append(param("LpuFunciton",null)) ; 
			href.append(param("Operations","")) ;
			href.append(param("Reason",null)) ;
			href.append(param("ReasonC",null)) ;
			href.append(param("Render",null)) ;
			href.append(param("Result",null)) ;
			href.append(param("Sex",null)) ;
			href.append(param("VidLpu","2")) ;//1-stac,2-polic
			href.append(param("Yet",null)) ;*/
		} else if ("VISIT".equals(aType)) {
			//Дополнительная диспансеризация
			href.append(param("AddDisp",null)) ;
			href.append(param("AddDispAge",null)) ;
			href.append(param("AddDispCases",null)) ;
			href.append(param("AddDispHealthGroup",null)) ;
			
			StringBuilder sql = new StringBuilder() ;
			sql.append("select to_char(spo.dateStart,'yyyymmdd') as f1datestart");
			sql.append(" ,to_char(case when vis.emergency='1' then spo.datestart else coalesce(spo.dateFinish,spo.dateStart) end,'yyyymmdd') as f2dateFinish");
			sql.append(" ,to_char(pat.birthday,'yyyymmdd') as f3birthday");
			sql.append(" ,vs.omcCode as f4vsomccode");
			sql.append(" ,spo.id as f5spo");
			sql.append(" ,case when pvss.code='И0' then '1' else '0' end as f6foreign");
			sql.append(" , to_char(spo.dateStart,'dd.mm.yyyy') as f7date5start");
			sql.append(" ,to_char(case when vis.emergency='1' then spo.datestart else coalesce(spo.dateFinish,spo.dateStart) end,'dd.mm.yyyy') as f8date5Finish");
			sql.append(" ,vis.patient_id as f9patient");
			sql.append(" ,coalesce(k.kinsman_id,vis.patient_id) as f10kinsman");
			sql.append(" ,mp.series||' '||mp.polNumber as f11policy") ;
			sql.append(" ,coalesce(card.number,pat.patientSync) as f12patientcode") ;
			sql.append(" ,case when vis.emergency='1' or spo.datefinish is null or spo.datefinish-spo.datestart=0 then 1 else spo.datefinish-spo.datestart+1 end as f13beddays") ;
			sql.append(" from MedCase vis ");
			sql.append(" left join Medcard card on card.id=vis.medcard_id") ;
			sql.append(" left join MedCase spo on spo.id=vis.parent_id") ;
			sql.append(" left join Kinsman k on k.id=vis.kinsman_id") ;
			sql.append(" left join Patient pat on pat.id=vis.patient_id") ;
			sql.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql.append(" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id") ;
			sql.append(" left join VocServiceStream vss on vss.id=vis.serviceStream_id") ;
			sql.append(" left join MedPolicy mp on mp.patient_id=pat.id") ;
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' ").append(aSqlAdd).append(") and mp.actualdatefrom <=vis.dateStart and coalesce(mp.actualdateto,vis.datestart)>=vis.datestart and mp.dtype like 'MedPolicyOm%'");
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) throw new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("BirthDate",wqr.get3())) ;
			
			href.append(param("Foreigns",wqr.get6())) ;
			href.append(param("HotelServices",null)) ;
			href.append(param("Sex",wqr.get4())) ;
			href.append(param("VidLpu","AMB")) ;//1-stac, 2- polic
			href.append(param("AdmissionDate",wqr.get1())) ;
			href.append(param("DischargeDate",wqr.get2())) ;
			href.append(param("BedDays",wqr.get13())) ;
			res.append("&dateFrom=").append(wqr.get7()).append("&dateTo=").append(wqr.get8())
			.append("&patient=").append(wqr.get9())
			.append("&kinsman=").append(wqr.get10())
			.append("&polNumber=").append(wqr.get11())
			.append("&card=").append(wqr.get12())
			;
			sql = new StringBuilder() ;
			sql.append(" select ");
			sql.append("  case when vis.emergency='1' then '1' else '0' end as f1emergency "); 
			sql.append("  ,list(case when smc.dtype='ServiceMedService' and ms.isNoOmc='1' then null else vms.code end) as f2medservce ");  
			sql.append("  ,vlf.code as f3lpufunction  ");
			sql.append("  ,ml.name as f4mlname  ");
			sql.append("  ,vr.code as f5vrcode  ");
			sql.append("  ,vr.omccode as f6vromccode  ");
			sql.append("  ,vvr.omccode as f7vvrcode  ");
			sql.append("  ,vis.uet as f8uet  ");
			sql.append("  ,list(mkb.code) as f9diagnosis   ");
			sql.append(" ,vodp.code as f10vodpcode") ;
			sql.append(" ,vodt.code as f11vodtcode") ;
			sql.append(" ,list(distinct mkb.code) as f12diag1nosis ") ;
			sql.append(" ,list(distinct case when vpd.code='1' then mkb.code else null end) as f13diag1nosis ") ;
			sql.append(" ,list(distinct case when vpd.code='3' then mkb.code else null end) as f14diag3nosis ") ;
			sql.append("  ,coalesce(ml.omccode,ml1.omccode) as f15mlomccode  ");
			sql.append("  ,vwpt.code as f16vwptcode  ");
			sql.append("  from MedCase vis   ");
			sql.append("  left join VocWorkPlaceType vwpt on vwpt.id=vis.workPlaceType_id ");
			sql.append("  left join MedCase spo on spo.id=vis.parent_id ");
			sql.append("  left join WorkFunction wf on wf.id = vis.WorkFunctionExecute_id "); 
			sql.append("  left join Worker w on w.id=wf.worker_id ");
			sql.append("  left join VocWorkFunction vwf on vwf.id=wf.workFunction_id "); 
			sql.append("  left join VocPost vp on vp.id=vwf.vocPost_id  ");
			sql.append("  left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id "); 
			sql.append("  left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id  ");
			sql.append("  left join Diagnosis diag on diag.medCase_id=vis.id  ");
			sql.append("  left join VocPriorityDiagnosis vpd on diag.priority_id=vpd.id "); 
			sql.append("  left join VocIdc10 mkb on mkb.id=diag.idc10_id  ");
			sql.append("  left join MedCase smc on vis.id=smc.parent_id ");
			sql.append("  left join MedService ms on smc.medService_id=ms.id "); 
			sql.append("  left join VocMedService vms on ms.vocMedService_id=vms.id "); 
			sql.append("  left join Patient pat on pat.id=vis.patient_id  ");
			sql.append("  left join VocSex vs on vs.id=pat.sex_id  ");
			sql.append("  left join VocSocialStatus vss on vss.id=pat.sex_id "); 
			sql.append("  left join MisLpu ml on ml.id=w.lpu_id  ");
			sql.append("  left join MisLpu ml1 on ml1.id=ml.parent_id  ");
			sql.append("  left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id "); 
			sql.append("  left join VocReason vr on vr.id=vis.visitReason_id "); 
			sql.append("  left join VocVisitResult vvr on vvr.id=vis.visitresult_id "); 
			sql.append("  where vis.id='").append(aMedCase).append("' ");
			sql.append("  group by vis.dateStart,vis.timeExecute,vwpt.code,ml.omccode,ml1.omccode,vlf.code,vis.emergency,ml.name,vr.code,vr.omccode,vvr.omccode,vis.uet,vodp.code,vodt.code ");
			sql.append("  order by vis.datestart desc,vis.timeExecute desc ");
			
			
			Collection<WebQueryResult> l_vis = service.executeNativeSql(sql.toString(),1) ;
			//res.append("&render=") ;
			for (WebQueryResult wqr_vis:l_vis) {
				StringBuilder href_vis = new StringBuilder() ;
				href_vis.append(href) ;
				href_vis.append(param("Lpu",wqr_vis.get15())) ;
				href_vis.append(param("Reason",wqr_vis.get6())) ;
				href_vis.append(param("ReasonC",wqr_vis.get5())) ;
				href_vis.append(param("Render",wqr_vis.get2())) ;
				href_vis.append(param("Result",wqr_vis.get7())) ;
				href_vis.append(param("Yet",wqr_vis.get8())) ;
				href_vis.append(param("WorkPlaceType",wqr_vis.get16())) ;
				href_vis.append(param("DepType",wqr_vis.get11())) ;
				href_vis.append(param("DiagnosisList",wqr_vis.get12())) ;
				href_vis.append(param("DiagnosisMain",wqr_vis.get13())) ;
				href_vis.append(param("DiagnosisConcomitant",wqr_vis.get14())) ;
				href_vis.append(param("DoctorPost",wqr_vis.get10())) ;
				href_vis.append(param("Emergency",wqr_vis.get1())) ;
				href_vis.append(param("Hts",null)) ;
				href_vis.append(param("LpuFunction",wqr_vis.get3())) ; //
				href_vis.append(param("Operations",null)) ;
				LOG.info("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
				String cost = ActionUtil.getContentOfHTTPPage("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys&"+href_vis.toString(),code_page);
				if (isf) {
					res.append("&render=") ;
					isf=false;
				} else {
					res.append("##") ;
				}
				StringBuilder c = getRender(cost,"","") ;
				res.append(c.toString().replaceAll("#", "%23")) ;
				
				
				//res.append("0#1111".replaceAll("#", "%23")) ;
			}
			
			
			
			/*
			Collection<WebQueryResult> l = service.executeNativeSql("select to_char(sls.dateStart,'yyyymmdd') as datestart,to_char(sls.dateFinish,'yyyymmdd') as dateFinish from MedCase sls where sls.id="+aMedCase) ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("AdmissionDate",null)) ;
			href.append(param("DischargeDate",null)) ;
			href.append(param("BedDays",null)) ;
			href.append(param("BirthDate",null)) ;
			href.append(param("DepType",null)) ;
			href.append(param("DiagnosisList",null)) ;
			href.append(param("DiagnosisMain",null)) ;
			href.append(param("DiagnosisConcomitant",null)) ;
			href.append(param("DoctorPost",null)) ;
			href.append(param("Emergency",null)) ;
			href.append(param("Foreigns",null)) ;
			href.append(param("HotelServices","0")) ;
			href.append(param("Hts",null)) ;
			href.append(param("Lpu",null)) ;
			href.append(param("LpuFunciton",null)) ; 
			href.append(param("Operations","")) ;
			href.append(param("Reason",null)) ;
			href.append(param("ReasonC",null)) ;
			href.append(param("Render",null)) ;
			href.append(param("Result",null)) ;
			href.append(param("Sex",null)) ;
			href.append(param("VidLpu","2")) ;//1-stac,2-polic
			href.append(param("Yet",null)) ;*/
		} else if ("SPO".equals(aType)) {
			//Дополнительная диспансеризация
			href.append(param("AddDisp",null)) ;
			href.append(param("AddDispAge",null)) ;
			href.append(param("AddDispCases",null)) ;
			href.append(param("AddDispHealthGroup",null)) ;
			
			StringBuilder sql = new StringBuilder() ;
			sql.append("select to_char(spo.dateStart,'yyyymmdd') as f1datestart");
			sql.append(" ,to_char(case when vis.emergency='1' then spo.datestart else spo.dateFinish end,'yyyymmdd') as f2dateFinish");
			sql.append(" ,to_char(pat.birthday,'yyyymmdd') as f3birthday");
			sql.append(" ,vs.omcCode as f4vsomccode");
			sql.append(" ,spo.id as f5spo");
			sql.append(" ,case when pvss.code='И0' then '1' else '0' end as f6foreign");
			sql.append(" , to_char(spo.dateStart,'dd.mm.yyyy') as f7date5start");
			sql.append(" ,to_char(case when vis.emergency='1' then spo.datestart else spo.dateFinish end,'dd.mm.yyyy') as f8date5Finish");
			sql.append(" ,vis.patient_id as f9patient");
			sql.append(" ,coalesce(k.kinsman_id,vis.patient_id) as f10kinsman");
			sql.append(" ,mp.series||' '||mp.polNumber as f11policy") ;
			sql.append(" ,coalesce(card.number,pat.patientSync) as f12patientcode") ;
			sql.append(" ,case when spo.datefinish-spo.datestart=0 or vis.emergency='1' then 1 else spo.datefinish-spo.datestart+1 end as f13beddays") ;
			sql.append(" from MedCase vis ");
			sql.append(" left join Medcard card on card.id=vis.medcard_id") ;
			sql.append(" left join MedCase spo on spo.id=vis.parent_id") ;
			sql.append(" left join Kinsman k on k.id=vis.kinsman_id") ;
			sql.append(" left join Patient pat on pat.id=vis.patient_id") ;
			sql.append(" left join VocSex vs on vs.id=pat.sex_id") ;
			sql.append(" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id") ;
			sql.append(" left join VocServiceStream vss on vss.id=vis.serviceStream_id") ;
			sql.append(" left join MedPolicy mp on mp.patient_id=pat.id") ;
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' ").append(aSqlAdd).append(") and (spo.dateFinish is not null or vis.emergency='1') and mp.actualdatefrom <=vis.dateStart and coalesce(mp.actualdateto,vis.datestart)>=vis.datestart and mp.dtype like 'MedPolicyOm%'") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) throw new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("BirthDate",wqr.get3())) ;
			
			href.append(param("Foreigns",wqr.get6())) ;
			href.append(param("HotelServices",null)) ;
			href.append(param("Sex",wqr.get4())) ;
			href.append(param("VidLpu","AMB")) ;//1-stac, 2- polic
			href.append(param("AdmissionDate",wqr.get1())) ;
			href.append(param("DischargeDate",wqr.get2())) ;
			href.append(param("BedDays",wqr.get13())) ;
			res.append("&dateFrom=").append(wqr.get7()).append("&dateTo=").append(wqr.get8())
				.append("&patient=").append(wqr.get9())
				.append("&kinsman=").append(wqr.get10())
				.append("&polNumber=").append(wqr.get11())
				.append("&card=").append(wqr.get12())
				;
			sql = new StringBuilder() ;
			sql.append(" select ");
			sql.append("  case when vis.emergency='1' then '1' else '0' end as f1emergency "); 
			sql.append("  ,list(case when smc.dtype='ServiceMedService' and ms.isNoOmc='1' then null else vms.code end) as f2medservce ");  
			sql.append("  ,vlf.code as f3lpufunction  ");
			sql.append("  ,ml.name as f4mlname  ");
			sql.append("  ,vr.code as f5vrcode  ");
			sql.append("  ,vr.omccode as f6vromccode  ");
			sql.append("  ,vvr.omccode as f7vvrcode  ");
			sql.append("  ,vis.uet as f8uet  ");
			sql.append("  ,list(mkb.code) as f9diagnosis   ");
			sql.append(" ,vodp.code as f10vodpcode") ;
			sql.append(" ,vodt.code as f11vodtcode") ;
			sql.append(" ,list(distinct mkb.code) as f12diag1nosis ") ;
			sql.append(" ,list(distinct case when vpd.code='1' then mkb.code else null end) as f13diag1nosis ") ;
			sql.append(" ,list(distinct case when vpd.code='3' then mkb.code else null end) as f14diag3nosis ") ;
			sql.append("  ,coalesce(ml.omccode,ml1.omccode) as f15mlomccode  ");
			sql.append("  ,vwpt.code as f16vwptcode  ");
			sql.append("  from MedCase vis   ");
			sql.append("  left join VocWorkPlaceType vwpt on vwpt.id=vis.workPlaceType_id ");
			sql.append("  left join MedCase spo on spo.id=vis.parent_id ");
			sql.append("  left join WorkFunction wf on wf.id = vis.WorkFunctionExecute_id "); 
			sql.append("  left join Worker w on w.id=wf.worker_id ");
			sql.append("  left join VocWorkFunction vwf on vwf.id=wf.workFunction_id "); 
			sql.append("  left join VocPost vp on vp.id=vwf.vocPost_id  ");
			sql.append("  left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id "); 
			sql.append("  left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id  ");
			sql.append("  left join Diagnosis diag on diag.medCase_id=vis.id  ");
			sql.append("  left join VocPriorityDiagnosis vpd on diag.priority_id=vpd.id "); 
			sql.append("  left join VocIdc10 mkb on mkb.id=diag.idc10_id  ");
			sql.append("  left join MedCase smc on vis.id=smc.parent_id ");
			sql.append("  left join MedService ms on smc.medService_id=ms.id "); 
			sql.append("  left join VocMedService vms on ms.vocMedService_id=vms.id "); 
			sql.append("  left join Patient pat on pat.id=vis.patient_id  ");
			sql.append("  left join VocSex vs on vs.id=pat.sex_id  ");
			sql.append("  left join VocSocialStatus vss on vss.id=pat.sex_id "); 
			sql.append("  left join MisLpu ml on ml.id=w.lpu_id  ");
			sql.append("  left join MisLpu ml1 on ml1.id=ml.parent_id  ");
			sql.append("  left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id "); 
			sql.append("  left join VocReason vr on vr.id=vis.visitReason_id "); 
			sql.append("  left join VocVisitResult vvr on vvr.id=vis.visitresult_id "); 
			sql.append("  where spo.id='").append(wqr.get5()).append("' ");
			sql.append("  group by vis.dateStart,vis.timeExecute,vwpt.code,ml.omccode,ml1.omccode,vlf.code,vis.emergency,ml.name,vr.code,vr.omccode,vvr.omccode,vis.uet,vodp.code,vodt.code ");
			sql.append("  order by vis.datestart desc,vis.timeExecute desc ");
 
			
			Collection<WebQueryResult> l_vis = service.executeNativeSql(sql.toString(),1) ;
			//res.append("&render=") ;
			for (WebQueryResult wqr_vis:l_vis) {
				StringBuilder href_vis = new StringBuilder() ;
				href_vis.append(href) ;
				href_vis.append(param("Lpu",wqr_vis.get15())) ;
				href_vis.append(param("Reason",wqr_vis.get6())) ;
				href_vis.append(param("ReasonC",wqr_vis.get5())) ;
				href_vis.append(param("Render",wqr_vis.get2())) ;
				href_vis.append(param("Result",wqr_vis.get7())) ;
				href_vis.append(param("Yet",wqr_vis.get8())) ;
				href_vis.append(param("WorkPlaceType",wqr_vis.get16())) ;
				href_vis.append(param("DepType",wqr_vis.get11())) ;
				href_vis.append(param("DiagnosisList",wqr_vis.get12())) ;
				href_vis.append(param("DiagnosisMain",wqr_vis.get13())) ;
				href_vis.append(param("DiagnosisConcomitant",wqr_vis.get14())) ;
				href_vis.append(param("DoctorPost",wqr_vis.get10())) ;
				href_vis.append(param("Emergency",wqr_vis.get1())) ;
				href_vis.append(param("Hts",null)) ;
				href_vis.append(param("LpuFunction",wqr_vis.get3())) ; //
				href_vis.append(param("Operations",null)) ;
				//LOG.info("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
				String cost = ActionUtil.getContentOfHTTPPage("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys&"+href_vis.toString(),code_page);
				if (isf) {
					res.append("&render=") ;
					isf=false;
				} else {
					res.append("##") ;
				}
				StringBuilder c = getRender(cost,"","") ;
				res.append(c.toString().replaceAll("#", "%23")) ;
				
				
				//res.append("0#1111".replaceAll("#", "%23")) ;
			}
			
			
			
			/*
			Collection<WebQueryResult> l = service.executeNativeSql("select to_char(sls.dateStart,'yyyymmdd') as datestart,to_char(sls.dateFinish,'yyyymmdd') as dateFinish from MedCase sls where sls.id="+aMedCase) ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append(param("AdmissionDate",null)) ;
			href.append(param("DischargeDate",null)) ;
			href.append(param("BedDays",null)) ;
			href.append(param("BirthDate",null)) ;
			href.append(param("DepType",null)) ;
			href.append(param("DiagnosisList",null)) ;
			href.append(param("DiagnosisMain",null)) ;
			href.append(param("DiagnosisConcomitant",null)) ;
			href.append(param("DoctorPost",null)) ;
			href.append(param("Emergency",null)) ;
			href.append(param("Foreigns",null)) ;
			href.append(param("HotelServices","0")) ;
			href.append(param("Hts",null)) ;
			href.append(param("Lpu",null)) ;
			href.append(param("LpuFunciton",null)) ; 
			href.append(param("Operations","")) ;
			href.append(param("Reason",null)) ;
			href.append(param("ReasonC",null)) ;
			href.append(param("Render",null)) ;
			href.append(param("Result",null)) ;
			href.append(param("Sex",null)) ;
			href.append(param("VidLpu","2")) ;//1-stac,2-polic
			href.append(param("Yet",null)) ;*/
		} else if ("EXTDISP".equals(aType)) {
			//Дополнительная диспансеризация
			href.append("AddDisp=");
			href.append("AddDispAge=");
			href.append("AddDispCases=");
			href.append("AddDispHealthGroup=");
			
	//		Collection<WebQueryResult> l = service.executeNativeSql("select to_char(sls.dateStart,'yyyymmdd') as datestart,to_char(sls.dateFinish,'yyyymmdd') as dateFinish from MedCase sls where sls.id="+aMedCase) ;
			//WebQueryResult wqr = l.iterator().next() ;
			href.append("AdmissionDate=");
			href.append("BedDays=");
			href.append("BirthDate=");
			href.append("DepType=");
			href.append("DiagnosisList=");
			href.append("DiagnosisMain=");
			href.append("DiagnosisConcomitant=");
			href.append("DischargeDate=");
			href.append("DoctorPost=");
			href.append("Emergency=");
			href.append("Foreigns=");
			href.append("HotelServices=");
			href.append("Hts=");
			href.append("Lpu=");
			href.append("LpuFunciton="); //
			href.append("Operations=");
			href.append("Reason=");
			href.append("ReasonC=");
			href.append("Render=");
			href.append("Result=");
			href.append("Sex=");
			href.append("VidLpu=");
			href.append("Yet=");
		}
		return res.toString() ;
	}
	private static StringBuilder getRender(String aHtmlCode,Object aAdditionDataFrom, Object aAdditionDataTo) {
		String[] htm = aHtmlCode.toUpperCase().split("BODY") ;
		StringBuilder ret = new StringBuilder() ;
		if (htm.length>2) {
			aHtmlCode = htm[1].substring(1) ;
			if (aHtmlCode.length()>2) {
				aHtmlCode = aHtmlCode.substring(0,aHtmlCode.length()-2) ;
			}
			String[] s = aHtmlCode.trim().split("#") ;
			if (s.length>1) {
			ret.append(s[0]).append("#").append(aAdditionDataFrom).append(s[1]).append(aAdditionDataTo) ;
			}
			return ret;
		} else {
			return ret ;
		}
	}
	private static StringBuilder param(String aParam, Object aValue) {
		StringBuilder ret = new StringBuilder(); 
		if (aValue!=null &&!(""+aValue).equals("")) {
			ret.append("&").append(aParam).append("=").append(aValue) ;
		}
		return ret ; 
	}
	public String createNewDiary(String aTitle, String aText, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		service.createNewDiary(aTitle, aText, username) ;
		return "Сохранено" ;
	}
	public void updateDataFromParameterConfig(Long aDepartment, Long aIsLowerCase, String aIds, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.updateDataFromParameterConfig(aDepartment, aIsLowerCase!=null && aIsLowerCase.equals(1L), aIds, false) ;
	}
	public void removeDataFromParameterConfig(Long aDepartment, String aIds, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.removeDataFromParameterConfig(aDepartment, aIds) ;
	}
	public String changeServiceStreamBySmo(Long aSmo, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.changeServiceStreamBySmo(aSmo, aServiceStream) ;
		return "Поток обслуживания изменен" ;
	}
	public String unionSloWithNextSlo(Long aSlo,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.unionSloWithNextSlo(aSlo) ;
		createAdminChangeMessageBySmo(aSlo, "UNION_SLO_WITH_NEXT_SLO", "Объединено СЛО со след. СЛО: "+aSlo, aRequest) ;

		return "Объединены" ;
	}
	public String getPatientDefaultInfo(Long aPatient,String aDateFrom, String aDateTo, HttpServletRequest aRequest) throws NamingException {
		StringBuilder ret = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult>  col = service.executeNativeSql("select vi.name||' дата установления '||to_char(i.dateFrom,'dd.mm.yyyy') || case when i.withoutexam='1' then ' без переосвидетельствования ' else coalesce(' дата следующего пересмотра '||to_char(i.nextRevisionDate,'dd.mm.yyyy'),'') end || case when i.incapable='1' then ' недееспособный ' ||' '||coalesce(' дата суда '||to_char(i.lawCourtDate,'dd.mm.yyyy'))||coalesce(' суд '||vlc.name) else '' end  from invalidity i left join VocInvalidity vi on vi.id=i.group_id        left join VocLawCourt vlc on vlc.id=i.lawCourt_id where i.patient_id="+aPatient+"   order by i.dateFrom desc ") ;
		if (!col.isEmpty()) {
			ret.append("\nИНВАЛИДНОСТЬ: ") ;
			for (WebQueryResult wqr : col) {
				ret.append(wqr.get1()) ;
			}
			
		}
		col.clear() ;
		col = service.executeNativeSql("select ml.name||' С '||to_char(sls.dateStart,'dd.mm.yyyy')||' ПО '||to_char(sls.dateFinish,'dd.mm.yyyy') from medcase sls left join medcase slo on slo.parent_id=sls.id  left join mislpu ml on ml.id=slo.department_id where sls.patient_id="+aPatient+" and sls.dtype='HospitalMedCase' and sls.dateFinish <= to_date('"+aDateFrom+"','dd.mm.yyyy') and slo.datefinish is not null order by sls.datefinish desc ") ;
		
		if (!col.isEmpty()) {
			ret.append("\nПРЕДЫДУЩИЕ ГОСПИТАЛИЗАЦИИ: ") ;
			for (WebQueryResult wqr : col) {
				ret.append(wqr.get1()) ;
			}
			
		}

		return ret.toString() ;
	}
	public String deniedHospitalizatingSls(Long aMedCaseId,Long aDeniedHospitalizatingId,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.deniedHospitalizatingSls(aMedCaseId,aDeniedHospitalizatingId) ;
        createAdminChangeMessageBySmo(aMedCaseId, "DENIED_HOSPITALIZATING", "Оформлен отказ от госпитализации", aRequest) ;

		return "Обновлены" ;
	}
	public String preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis,HttpServletRequest aRequest) throws Exception {
		IWebQueryService service1 = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean isEdit =true ;
		boolean isCurentOnly = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay",aRequest) ;
		if (isCurentOnly) {
			Collection<WebQueryResult> list = service1.executeNativeSql("select SLS.id from medcase SLS  left join vochospitalizationresult vhr on vhr.id= SLS.result_id where SLS.id='" + aMedCaseId + "' and (sls.dateFinish is not null or (vhr.code='11' and (current_date-sls.datefinish)<4))") ;
			if (!list.isEmpty()) {
				IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
	        	isEdit = TicketServiceJs.checkPermission(service, "DischargeMedCase", "editDischargeEpicrisis", aMedCaseId, aRequest) ;
			}
		}
		if (isEdit) {
			IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
			service.preRecordDischarge(aMedCaseId, aDischargeEpicrisis) ;
			return "Сохранено" ;
		} 
		throw new IllegalAccessError("У Вас стоит запрет на редактирование выписанного пациента!!!") ;
		
	}
	public String updateDischargeDateByInformationBesk(String aIds, String aDate,HttpServletRequest aRequest) throws Exception {
		
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.updateDischargeDateByInformationBesk(aIds, aDate) ;
		return "Обновлены" ;
	}

	public String getTypeDiagByAccoucheur(HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getTypeDiagByAccoucheur() ;
	}
	
	public static void createAdminChangeMessageBySmo (Long aSmo, String aType, String aTextInfo
			, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql.append("insert into AdminChangeJournal ( medcase, createDate, createTime")
			.append(", createUsername, ctype,  annulRecord) ")
			.append("values (")	.append(aSmo).append(", current_date, current_time, '")
			.append(login)
			.append("', '")
			.append(aType)
			.append("','")
			.append(aTextInfo)
			.append("')")
						;	
		service.executeUpdateNativeSql(sql.toString()) ;
		
	}
    public String changeStatCardNumber(String aNewStatCardNumber, Long aMedCase, HttpServletRequest aRequest) throws NamingException, JspException {
    	
    	boolean always = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber", aRequest) ;
    	IWebQueryService serviceWQR = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	Collection<WebQueryResult> l = serviceWQR.executeNativeSql("select ss.code from statisticstub ss where ss.medCase_id="+aMedCase) ;
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        //return service.(aStatCardNumber, aDateStart, aEntranceTime, aPatient);
        
        createAdminChangeMessageBySmo(aMedCase, "CHANGE_STAT_CARD_NUMBER", "Изменение номера стат. карты с:" +
				(l.isEmpty() ? "-" : l.iterator().next().get1()) +
				" на " +
				aNewStatCardNumber +
				" случая лечения в стационаре #" +
				aMedCase, aRequest) ;
        return service.getChangeStatCardNumber(aMedCase, aNewStatCardNumber,always) ;
    }
    public String getListTemperatureCurve(Long aMedCase, HttpServletRequest aRequest) throws Exception {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getTemperatureCurve(aMedCase) ;
    }
    
    public Long getPatient(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getPatient(aMedCase) ;
    }
    
    public String isOpenningSlo(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.isOpenningSlo(aMedCase) ;
    }
    
    public String deleteDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException  {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	createAdminChangeMessageBySmo(aMedCaseId, "HOSP_DELETE_DATA_DISCHARGE", "Удалены данные о дате выписки", aRequest) ;

    	return service.deleteDataDischarge(aMedCaseId) ;
    }
    
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleServiceByPatient(aMedService, aPatient, aService, aDate) ;
    }
    public String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleOperationByPatient(aSurOperation, aPatient, aOperation, aDate) ;
    }
    
    public String getOperations(Long aPatient, String aDateStart
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException {
    	StringBuilder sql = new StringBuilder() ;
    	if (aDateFinish!=null && !aDateFinish.equals("") && aDateFinish.length()==10) {
    		aDateFinish = "to_date('"+aDateFinish+"','dd.mm.yyyy')" ;
    	} else{
    		aDateFinish = "CURRENT_DATE" ;
    	}
    	sql.append("select to_char(operationDate,'DD.MM.YYYY') as operDate1,cast(operationTime as varchar(5)) as timeFrom,cast(operationTimeTo as varchar(5)) as timeTo,vo.name as voname,so.operationText as sooperationText from SurgicalOperation so left join MedService vo on vo.id=so.operation_id where so.patient_id='")
    		.append(aPatient)
    		.append("' and so.operationDate between to_date('").append(aDateStart).append("','dd.mm.yyyy') and ").append(aDateFinish).append(" order by so.operationDate") ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	
    	Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString()) ;
		StringBuilder result = new StringBuilder() ;
		result.append("\n") ;
		result.append("ХИРУРГИЧЕСКИЕ ОПЕРАЦИИ:");
		result.append("\n") ;
		for (WebQueryResult wqr :list1) {
			result.append(wqr.get1()).append(" ") ;
			result.append(wqr.get4()).append("\n") ;
			result.append(wqr.get5()!=null?wqr.get5():"") ;
		}
		return result.toString() ;
    	
    	
    }
    public String getExpMedservices(int aIsParamDepartment, int aIsLowerCase, int aIsNewStr,  Long aPatient, String aDateStart
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder period = new StringBuilder() ;
    	period.append("between to_date('").append(aDateStart)
    	.append("','dd.mm.yyyy') and  ") ;
    	if (aDateFinish!=null && !aDateFinish.equals("") && aDateFinish.length()==10) {
    		period.append("to_date('").append(aDateFinish).append("','dd.mm.yyyy')") ;
    	} else {
    		period.append("CURRENT_DATE") ;
    	}
    	StringBuilder sql = new StringBuilder() ;
    	String razd = "\n" ;
    	if (aIsNewStr==1) {
    		razd = "; " ;
    	}
    	if (aIsParamDepartment==2) {
			/*sql.append("select em.NumberDoc")
			.append(" ,'\n '||to_char(em.OrderDate,'dd.mm.yyyy') ||' ' ")
			.append(" || replace(list('\n'||em.comment),'\n, \n','\n\n') as comment from Document em") 
			.append(" left join patient p on p.id=em.patient_id")  
			.append(" where p.id='").append(aPatient).append("'")
			.append(" and em.dtype='ExternalMedservice'")
			.append(" and (em.orderDate ").append(period)
			.append(" or em.createDate ").append(period)
			.append(") group by em.numberdoc,em.orderDate order by em.orderDate");
			*/
    		sql.append("select em.NumberDoc ||' от ','").append(razd).append("'||to_char(em.OrderDate,'dd.mm.yyyy')||' '||vdpg.name||': '|| ")
    		.append(" list(vdp.name||': '||dp.value||' '||vdp.dimension)  as infoDirect")
    		.append(" from Document em ")
    		.append(" left join DocumentParameter dp on dp.document_id=em.id ")
    		.append(" left join VocDocumentParameter vdp on vdp.id = dp.parameter_id")
    		.append(" left join VocDocumentParameterGroup vdpg on vdpg.id=vdp.parameterGroup_id")
    		.append(" where em.patient_id='").append(aPatient).append("'")
    		.append(" and em.dtype='ExternalMedservice'")
    		.append(" and (em.orderDate ").append(period)
    		.append(" or em.createDate ").append(period)
    		.append(" )")
    		.append(" group by em.numberdoc,em.orderDate,vdpg.id,vdpg.name order by vdpg.name,em.orderDate") ;
    		
    	} else {
    		//String department = "219" ;
    		IWorkerService serviceWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
    		Long department = serviceWorker.getWorkingLpu(); 
    		sql.append("select em.NumberDoc ||' от ','").append(razd).append("'||to_char(em.OrderDate,'dd.mm.yyyy')||' '||lower(vdpg.name)||': '|| ")
			.append(" list(case when vdpc.isLowerCase='1'")
			.append(" then lower(vdp.name||': '||dp.value||' '||vdp.dimension)")
			.append(" else  vdp.name||': '||dp.value||' '||vdp.dimension")
			.append(" end)  as infoDirect")
			.append(" from Document em ")
			.append(" left join DocumentParameter dp on dp.document_id=em.id ")
			.append(" left join VocDocumentParameter vdp on vdp.id = dp.parameter_id")
			.append(" left join VocDocumentParameterConfig vdpc on vdpc.documentParameter_id = vdp.id")
			.append(" left join VocDocumentParameterGroup vdpg on vdpg.id=vdp.parameterGroup_id")
			.append(" where em.patient_id='").append(aPatient).append("'")
			.append(" and em.dtype='ExternalMedservice'")
			.append(" and (em.orderDate ").append(period)
			.append(" or em.createDate ").append(period)
			.append(" ) and vdpc.department_id='").append(department).append("'")
			.append(" group by em.orderDate,em.numberdoc,vdpg.id,vdpg.name order by vdpg.name,em.orderDate") ;
    	}
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString()) ;
		StringBuilder result = new StringBuilder() ;
		for (WebQueryResult wqr :list1) {
			//result.append(wqr.get1()) ;
			if (wqr.get2()!=null) result.append(wqr.get2()) ;
				
		}
		result.append("\n");
		//if (aType==2) return result.toString().toLowerCase().replaceAll("\n\n", ",").replaceAll("\n", ", ").replaceAll("  ", " ") ;
		return aIsLowerCase==1?result.substring(1).trim().toLowerCase():result.substring(1).trim() ;
		//return result.toString() ;
    }
    public String getLabInvestigations(Long aPatient, String aDateStart
			,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs, boolean aLuchIs, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getnvestigationsTextDTM(aPatient, aDateStart, aDateFinish,aLabsIs,aFisioIs,aFuncIs,aConsIs, aLuchIs);
    }

    public String setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	service.setPatientByExternalMedservice(aNumberDoc, aOrderDate, aPatient) ;
    	return "обновлено" ;
    }
    public String getDefaultDepartmentByUser(HttpServletRequest aRequest) throws NamingException {
    	StringBuilder res = new StringBuilder() ;
    	StringBuilder sql = new StringBuilder() ;
    	String username=LoginInfo.find(aRequest.getSession(true)).getUsername() ;
    	sql.append(" select ml.id,ml.name||coalesce(' ('||ml1.name||')','') from workfunction wf");
    	sql.append(" left join secuser su on su.id=wf.secuser_id");
    	sql.append(" left join worker w on w.id=wf.worker_id");
    	sql.append(" left join mislpu ml on ml.id=w.lpu_id");
    	sql.append(" left join mislpu ml1 on ml1.id=ml.parent_id");
    	sql.append(" where su.login='").append(username).append("'") ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	if (!list.isEmpty()) {
    		WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
    	} else {
    		res.append("##");
    	}
    	return res.toString() ;
    	
    }
    public String getTextDiaryByMedCase(Long aMedCase,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
    	sql.append("select d.id,d.record from Diary d ") ;
    	sql.append(" where d.medCase_id='").append(aMedCase).append("'") ;

    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	for(WebQueryResult wqr:list) {
    		//WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get2());
    		
    	} 
    	
    	return res.toString() ;
    }
    public String getDefaultBedTypeByDepartment(Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
    	sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment) ;
    	if (aServiceStream!=null && aServiceStream.intValue()>0) sql.append("' and bf.serviceStream_id='").append(aServiceStream) ;
    	sql.append("' and bf.dateFinish is null order by bf.amount desc") ;
    	
    	
    	
    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	if (!list.isEmpty()) {
    		WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
    		res.append(wqr.get3()).append("#").append(wqr.get4()).append("#") ;
    		
    	} else {
    		res.append("####");
    	}
    	
    	return res.toString() ;
    }
    public String getDefaultBedSubTypeByDepartment(Long aDepartment, Long aServiceStream,Long aBedType, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
    	sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment) ;
    	if (aServiceStream!=null && aServiceStream.intValue()>0) sql.append("' and bf.bedType_id='").append(aBedType) ;
    	sql.append("' and bf.serviceStream_id='").append(aServiceStream) ;
    	sql.append("' and bf.dateFinish is null") ;
    	
    	
    	
    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	if (!list.isEmpty()) {
    		WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get3()).append("#").append(wqr.get4()).append("#") ;
    	} else {
    		res.append("##");
    	}
    	return res.toString() ;
    }
    public String getDefaultBedFundBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql ;
    	StringBuilder res = new StringBuilder() ;
    	Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='"+aParent+"' and vht.code is not null",1) ;
    	String bedSubType="";
    	String hospType=null ;
    	if (!list.isEmpty()) {
    		WebQueryResult wqr = list.iterator().next() ;
    		hospType=""+wqr.get2() ;
    	}
    	if ("DAYTIMEHOSP".equals(hospType)) {
    		bedSubType=" and vbst.code='2' ";
    	} else if (hospType==null || hospType.equals("ALLTIMEHOSP")) {
    		bedSubType=" and vbst.code='1' ";
    	}
    	sql = new StringBuilder() ;
    	sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment)
    	.append("' and bf.serviceStream_id='").append(aServiceStream)
    	.append("' and to_date('").append(aDateFrom)
    	.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
    	sql.append(" ").append(bedSubType).append(" order by bf.amount desc");
    	
    	list.clear() ;
    	list = service.executeNativeSql(sql.toString(),2) ;
    	if (list.size()==1) {
    		WebQueryResult wqr = list.iterator().next() ;
    		res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
    		
    	} else {
    		res.append("##");
    	}
    	
    	return res.toString() ;
    }
    public String getDefaultInfoBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
		Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='"+aParent+"' and vht.code is not null",1) ;
		String bedSubType="";
		String hospType=null ;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			hospType=""+wqr.get2() ;
		}
		if (hospType!=null&&hospType.equals("DAYTIMEHOSP")) {
			bedSubType=" and vbst.code='2' ";
		} else if (hospType!=null&&hospType.equals("ALLTIMEHOSP")) {
			bedSubType=" and vbst.code='1' ";
		}
		sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ") ;
		sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
		sql.append(" where bf.lpu_id='").append(aDepartment)
			.append("' and bf.serviceStream_id='").append(aServiceStream)
			.append("' and to_date('").append(aDateFrom)
			.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
		sql.append(" ").append(bedSubType);
		String username=LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql1=new StringBuilder();
		sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join SecUser su on su.id=wf.secUser_id")
			.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" where su.login = '").append(username).append("' and w.lpu_id='").append(aDepartment).append("' and wf.id is not null") ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql1.toString(),1) ;
		if (!list1.isEmpty()) {
			WebQueryResult wqr = list1.iterator().next() ;
			res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
		} else {
			sql1=new StringBuilder();
			sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
				.append(" left join Worker w on w.id=wf.worker_id")
				.append(" left join SecUser su on su.id=wf.secUser_id")
				.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
				.append(" where w.lpu_id='").append(aDepartment).append("' and wf.isAdministrator='1'") ;
			list1 = service.executeNativeSql(sql1.toString(),1) ;
			if (!list1.isEmpty()) {
				WebQueryResult wqr = list1.iterator().next() ;
				res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
			} else {
				res.append("##");
			}
		}
		list.clear() ;
		list = service.executeNativeSql(sql.toString(),2) ;
		if (list.size()==1) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
			
		} else {
			res.append("##");
		}

		return res.toString() ;
    }

	/**
	 * Получить информацию для 263 приказа.
	 *
	 * @param slsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json результат
	 */
    public String sqlorder263(int slsId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(
				"SELECT hf.id as f0, hf.firstname as f1, hf.lastname as f2, hf.middlename as f3, to_char(hf.birthday,'dd.mm.yyyy') as f4," +
				" hf.phone as f5,case when hf.sex_id=1 then 'Ж' else 'М' end as f6" +
				" ,hf.typepolicy as f7,hf.seriespolicy as f8, hf.numberpolicy as f9, hf.directmedcase_id as f10, hf.numberfond as f11," +
				" to_char(hf.directdate,'dd.mm.yyyy') as f12, hf.diagnosis as f13,hf.orderlpucode as f14," +
				" to_char(hf.prehospdate,'dd.mm.yyyy') as f15,hf.directlpucode as f16, hf.profile as f17, to_char(hf.hospdate,'dd.mm.yyyy') as f18," +
				" to_char(hf.hospdischargedate,'dd.mm.yyyy') as f19,vdh.name as f20" +
				" from hospitaldatafond hf" +
				" left join vocsex v on v.id=hf.sex_id" +
				" left join vocdeniedhospitalizating vdh on vdh.id=hf.id" +
				" where hf.hospitalmedcase_id=" + slsId,1);
		JSONObject res = new JSONObject() ;
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next();
			res.put("idTxt", w.get1())
					.put("fnameTxt", w.get2())
					.put("nameTxt", w.get3())
					.put("lnameTxt", w.get4())
					.put("birthTxt", w.get5())
					.put("phoneTxt", w.get6())
					.put("sexTxt", w.get7())
					.put("typepoliceTxt", w.get8())
					.put("serpolTxt", w.get9())
					.put("numpolTxt", w.get10())
					.put("numdirectTxt", w.get11())
					.put("numfondTxt", w.get12())
					.put("directDateTxt", w.get13())
					.put("diagnosisTxt", w.get14())
					.put("orderlpuTxt", w.get15())
					.put("preDateTxt", w.get16())
					.put("directlpuTxt", w.get17())
					.put("profileTxt", w.get18())
					.put("datehospTxt", w.get19())
					.put("disDateTxt", w.get20())
					.put("denied", w.get21());
		}
		return res.toString();
    }

	/**
	 * Проверить наличие других предварительных госпитализаций.
	 *
	 * @param patId Patient.id
	 * @param aRequest HttpServletRequest
	 * @return String json c результатом
	 */
    public String prevPlanHospital(int patId,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String query="select distinct to_char(wchb.datefrom,'dd.mm.yyyy')" +
				" ,case when wchb.diagnosis<>'null. null' then coalesce(wchb.diagnosis, mkb.code||' ' ||mkb.name) else" +
				" mkb.code||' ' ||mkb.name end as mkb,m.name" +
				" ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as fiopost" +
				" ,wchb.id as f5_preId" +
				" from workcalendarhospitalbed wchb" +
				" left join mislpu m on wchb.department_id=m.id" +
				" left join medcase mc on wchb.patient_id=mc.patient_id" +
				" left join workfunction wf on wf.id=wchb.workfunction_id" +
				" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
				" left join worker w on w.id=wf.worker_id" +
				" left join patient wp on wp.id=w.person_id" +
				" left join vocidc10 mkb on mkb.id=wchb.idc10_id" +
				" where wchb.dateFrom is not null and wchb.datefrom>=CAST('today' AS DATE) and wchb.patient_id=" + patId;
        JSONArray res = new JSONArray() ;
		Collection<WebQueryResult> list = service.executeNativeSql(query);
		for (WebQueryResult w :list) {
			JSONObject o = new JSONObject() ;
			o.put("id",w.get5());
			o.put("date", w.get1())
					.put("diagnosis", w.get2())
					.put("lpu", w.get3())
					.put("fiopost", w.get4());
			res.put(o);
		}
		return res.toString();
    }

	/**
	 * Поставить пациента на наблюдение.
	 *
	 * @param slsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String c сообщением пользователю
	 */
    public String watchThisPatient(int slsId,HttpServletRequest aRequest) throws NamingException {
    	String res="Пациент добавлен в список наблюдения!";
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String query="select id from listwatch where datewatch=current_date";
    	Collection<WebQueryResult> list = service.executeNativeSql(query,1); 
    	int idlistwatch=0;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			idlistwatch=Integer.parseInt(wqr.get1().toString());
			}
		if (idlistwatch==0) { // надо добавить его
			query="INSERT into listwatch(datewatch) VALUES(current_date)";
			service.executeUpdateNativeSql(query); 
			query="select id from listwatch where datewatch=current_date";
			list = service.executeNativeSql(query,1); 
			if (!list.isEmpty()) {
				WebQueryResult wqr = list.iterator().next() ;
				idlistwatch=Integer.parseInt(wqr.get1().toString());
				}
		}
		query="select medcase_id from patientwatch where listwatch_id='" + idlistwatch + "' and medcase_id=(select parent_id from medcase where id='"+slsId+"')"; //есть ли уже
		list = service.executeNativeSql(query,1);
		if (!list.isEmpty()) res="Пациент уже был добавлен в список наблюдения!";
		else {
			query="INSERT INTO patientwatch(medcase_id,listwatch_id) VALUES((select parent_id from medcase where id='"+slsId+"'),'" + idlistwatch + "')";
			service.executeUpdateNativeSql(query);
		}
    	return res;
    }

	/**
	 * Снять пациента с наблюдения.
	 *
	 * @param slsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String c сообщением пользователю
	 */
    public String notWatchThisPatient(int slsId,HttpServletRequest aRequest) throws NamingException {
    	String res="Пациент убран из списка наблюдения!";
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String query="select medcase_id from patientwatch where listwatch_id=(select id from listwatch where datewatch=CAST('today' AS DATE)) and medcase_id=(select parent_id from medcase where id='"+slsId+"')"; //есть ли уже
    	Collection<WebQueryResult> list = service.executeNativeSql(query,1); 
    	if (!list.isEmpty()) { //удаляем
    		query="delete from patientwatch where listwatch_id=(select id from listwatch where datewatch=CAST('today' AS DATE)) and medcase_id=(select parent_id from medcase where id='"+slsId+"')";
    		service.executeUpdateNativeSql(query);
    	}
    	else res="Пациент и не был в списке наблюдения!";
    	return res;
    }

	/**
	 * Получить рост, вес, ИМТ.
	 *
	 * @param slsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json c результатом
	 */
	public String getHWeightIMT(int slsId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select height,weight,imt from statisticstub where medcase_id ='"+slsId+"'",1);
		JSONObject res = new JSONObject() ;
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next() ;
			res.put("height", w.get1())
					.put("weight", w.get2())
					.put("imt", w.get3());
		}
		return res.toString();
	}

	/**
	 * Сохранить рост, вес, ИМТ.
	 *
	 * @param slsId HospitalMedCase.id
	 * @param height Вес
	 * @param weight Рост
	 * @param imt ИМТ
	 * @param aRequest HttpServletRequest
	 * @return void
	 */
	public void setHWeightIMT(int slsId,int height,int weight,double imt,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update statisticstub set height='" + height + "',weight='"+weight+"',imt='"+imt+"' where medcase_id ='"+slsId+"'");
	}

	/**
	 * Проставить отметку в отчёте о том, что консультация была оказана (для диетолога).
	 *
	 * @param slsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return void
	 */
	public void setDietVisitIsDoneReportIMT(int slsId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String query="update statisticstub set dietdone=true where medcase_id ="+slsId;
		service.executeUpdateNativeSql(query);
	}

	/**
	 * Проставить перед удалением выписки: что юзер - лечащий врач (одна из его раб. ф-ий) последнего СЛО и всё это - в течение одного календарного дня.
	 *
	 * @param hmcId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return Boolean - true - можно удалять данные выписки, false - нельзя
	 */
	public Boolean checkUserIsALastSloTreatDoctorAndDishargeLess(int hmcId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String query="select case when (select ownerfunction_id from medcase where transferdate is null \n" +
				"and dtype='DepartmentMedCase' and parent_id="+hmcId+")\n" +
				"=ANY(select wf.id \n" +
				"from WorkFunction wf\n" +
				"left join WorkFunction gwf on gwf.id=wf.group_id\n" +
				"left join VocWorkFunction vwf on vwf.id=wf.workFunction_id\n" +
				"left join Worker w on w.id=wf.worker_id\n" +
				"left join MisLpu ml on ml.id=w.lpu_id\n" +
				"left join Worker sw on sw.person_id=w.person_id\n" +
				"left join WorkFunction swf on swf.worker_id=sw.id\n" +
				"left join SecUser su on su.id=swf.secUser_id\n" +
				"where su.login='"+login+"' and (wf.archival is null or wf.archival='0'))\n" +
				"then '1' else '0' end";
		Collection<WebQueryResult> list = service.executeNativeSql(query,1);
		boolean flag=false;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1().toString().equals("1")) {
				//проверка, что datefinish - текущая дата
				query="select datefinish,dischargetime from medcase where id="+hmcId;
				list = service.executeNativeSql(query);
				String datefinish="",timedisharge="";
				if (!list.isEmpty()) {
					wqr = list.iterator().next() ;
					datefinish=wqr.get1()!=null ?  wqr.get1().toString() : "";
					timedisharge=wqr.get2()!=null ?  wqr.get2().toString() : "";
				}
				if (!datefinish.equals("") && timedisharge!=null && !timedisharge.equals("")) {
					Date d = new java.util.Date();
					String dstr=(new SimpleDateFormat("yyyy-MM-dd")).format(d);
					flag = (datefinish.equals(dstr));
				}
			}
		}
		return flag;
	}

	/**
	 * Вывести список микробиологических исследований пациента с положительным результатом #91.
	 *
	 * @param dmcId DepartmentMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json с результатом
	 */
	public String showMBioResResList(int dmcId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select ms.code as name1,ms.name as name3,ms.shortname as shname,to_char(aslo.datestart,'dd.mm.yyyy') as dt" +
				" from diary d" +
				" left join forminputprotocol fipr on fipr.docprotocol_id=d.id and fipr.parameter_id=(select cast(keyvalue as int) from softconfig where key='FIP_parameterMicroBio')" +
				" left join uservalue uv on uv.id=fipr.valuevoc_id" +
				" left join medcase aslo on d.medcase_id=aslo.id and aslo.dtype='Visit'" +
				" left join medcase dmc on dmc.parent_id=aslo.parent_id" +
				" left join prescriptionlist pl on dmc.id=pl.medcase_id" +
				" left join prescription pr on pr.medcase_id=aslo.id" +
				" left join medservice ms on pr.medservice_id=ms.id" +
				" where d.dtype='Protocol' and dmc.DTYPE='DepartmentMedCase' and uv.cntball=1 and dmc.id=" + dmcId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
        JSONArray res = new JSONArray() ;
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject() ;
            o.put("name1", w.get1())
                    .put("name3", w.get2())
                    .put("shname", w.get3()==null? "": w.get3())
                    .put("dt", w.get4());
            res.put(o);
        }
		return res.toString();
	}

	/**
	 * Получить ФИО пациента и его стат. карту по dmc.id.
	 *
	 * @param dmcId DepartmentMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String результат
	 */
	public String getPatientFIOStat(int dmcId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(
		        "select pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename|| ' ' || to_char(pat.birthday,'dd.mm.yyyy')" +
                " from medCase dmc" +
                " left join MedCase as sls on sls.id = dmc.parent_id" +
                " left join Patient pat on dmc.patient_id = pat.id " +
                " where dmc.id=" + dmcId);
		return (list.isEmpty())? "":list.iterator().next().get1().toString();
	}

	/**
	 * Получить настройки по ключу.
	 *
	 * @param keyvalue Ключ
	 * @param aRequest HttpServletRequest
	 * @return String результат
	 */
	public String getSettingsKeyValueByKey(String keyvalue, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql( "select keyvalue from  softconfig where key='" + keyvalue + "'");
		return (list.isEmpty())? "":list.iterator().next().get1().toString();
	}

	/**
	 * Получить код потока обслуживания для отчётов jasper.
	 *
	 * @param name Поток обслуживания
	 * @param aRequest HttpServletRequest
	 * @return String результат
	 */
	public String getVocServiceStreamCodeByName(String name, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select code from  vocSstreamE2Entry where name='" + name + "'");
		return (list.isEmpty())? "":list.iterator().next().get1().toString();
	}

	/**
	 * Получить текст шаблона оценки риска по id шаблона aCardTemplId #97.
	 *
	 * @param aCardTemplId Поток обслуживания
	 * @param aRequest HttpServletRequest
	 * @return String результат
	 */
    public String getVocAssesmentCardById(String aCardTemplId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Collection<WebQueryResult> list = service.executeNativeSql("select name from assessmentCardTemplate where id=" + aCardTemplId);
		return (list.isEmpty())? "":list.iterator().next().get1().toString();
    }

	/**
	 * Проставить палату по умолчанию по отделению #101.
	 *
	 * @param slo DepartmentMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json с результатом
	 */
	public String getDefaultWorkPlaceByDepartment(String slo, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(
				"select wp.id as wpid,'№'||wp.name||' ('||wp1.name||')' as wpname, wp2.id as wp2id,wp2.name as wp2name" +
				" from workplace wp" +
				" left join WorkPlace wp1 on wp1.id=wp.parent_id" +
				" left join workplace wp2 on wp2.parent_id=wp.id" +
				" where wp.dtype='HospitalRoom' and (wp.isnoactuality is null or wp.isnoactuality='0')" +
				" and wp2.dtype='HospitalBed' and (wp2.isnoactuality is null or wp2.isnoactuality='0')" +
				" and wp.defaultroom=true" +
				" and wp.lpu_id=" + slo,1);
		JSONObject res = new JSONObject();
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next() ;
			res.put("wpid", w.get1())
					.put("wpname", w.get2())
					.put("wp2id", w.get3())
					.put("wp2name", w.get4());
		}
		return res.toString();
	}

	/**
	 * Проверить, админ ли (для удаления выписки в любой момент) #83 05072018.
	 *
	 * @param aRequest HttpServletRequest
	 * @return Boolean true - админ, false - нет
	 */
	public Boolean checkUserIsAdminToDeleteDischarge(HttpServletRequest aRequest) throws JspException {
		return (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Delete",aRequest)
				&& RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin",aRequest));
	}

	/**
	 * Обязательно ли заполнение услуги в дневнике? #122.
	 * Обязательно, если:
	 * это в СЛО
	 * поток облуживания платный/ДМС
	 * отделение создающего дневник не совпадает с отделением СЛО
	 *
	 * @param medCaseId MedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String "1" - необходима, "0" - нет
	 */
	public String getMedServiceNecessaryInDiary(Long medCaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql="select case when (ss.code='PRIVATEINSURANCE' or ss.code='CHARGED') and mc.dtype='DepartmentMedCase'" +
				" and ml.id<>mc.department_id" +
				" then '1' else '0' end" +
				" from medcase mc" +
				" left join vocservicestream ss on mc.servicestream_id=ss.id" +
				" left join secuser su on su.login='"+LoginInfo.find(aRequest.getSession(true)).getUsername() +
				"' left join workfunction wf on su.id=wf.secuser_id" +
				" left join Worker w on w.id=wf.worker_id" +
				" left join MisLpu ml on ml.id=w.lpu_id" +
				" where  mc.id="+medCaseId;
		Collection<WebQueryResult> l= service.executeNativeSql(sql) ;
		return !l.isEmpty() && l.iterator().next().get1().toString().equals("1") ? "1" : "0";
	}

	/**
	 * Получить id потока обслуживания в СЛО #122.
	 *
	 * @param medCaseId MedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String VocServiceStream.id
	 */
	public String getSstreamId(Long medCaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql="select ss.id from medcase mc" +
				" left join vocservicestream ss on mc.servicestream_id=ss.id" +
				" where  mc.id="+medCaseId;
		Collection<WebQueryResult> l= service.executeNativeSql(sql) ;
		return !l.isEmpty()? l.iterator().next().get1().toString() : "";
	}

	/**
	 * Получить dtype medcase для экспертных карт
	 * @param aMedcaseId MedCase.id
	 * @return String (0 - hospital, 1 - dep, 2 - visit, 3 - другое)
	 */
	public String getMedcaseDtype(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select case when mc.dtype='HospitalMedCase' then '0'\n" +
				"else case when mc.dtype='DepartmentMedCase' then '1'\n" +
				"else case when mc.dtype='Visit' then '2' else '0' end end end\n" +
				"from assessmentcard  ac\n" +
				"left join medcase mc on mc.id=ac.medcase_id\n" +
				"where ac.medcase_id="+aMedcaseId) ;
		return l.isEmpty() ? "" : l.iterator().next().get1().toString();
	}

	/**
	 * Получить dtype medcase
	 * @param aMedcaseId MedCase.id
	 * @return String (0 - hospital, 1 - dep, 2 - visit, 3 - polyclinic, 4 - short, 5 - service, -1 - другое)
	 */
	public String getMedcaseDtypeById(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select case when mc.dtype='HospitalMedCase' then '0'\n" +
				"else case when mc.dtype='DepartmentMedCase' then '1'\n" +
				"else case when mc.dtype='Visit' then '2'\n" +
                "else case when mc.dtype='PolyclinicMedCase' then '3' \n" +
                "else case when mc.dtype='ShortMedCase' then '4' \n" +
                "else case when mc.dtype='ServiceMedCase' then '5' else '-1' end end end end end end\n" +
				"from medcase mc where mc.id="+aMedcaseId) ;
		return l.isEmpty()? "" : l.iterator().next().get1().toString();
	}

	/**
	 * Получить кол-во дней с начала СЛС при создании дневника в приёмнике #135
	 * @param aMedcaseId HospitalMedCase.id
	 * @return String Кол-во дней/NULL если есть отказ от госпитализации
	 */
	public String getSlsCountDays(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("\n" +
				"select cast(round((EXTRACT(EPOCH FROM current_timestamp)-(SELECT EXTRACT(EPOCH FROM (mc.datestart + mc.entrancetime))  " +
				"from medcase mc where id="+aMedcaseId+"  and deniedhospitalizating_id is null))/3600/24) as int)") ;
		return (!l.isEmpty() && l.iterator().next().get1()!=null)? l.iterator().next().get1().toString():"";
	}

	/**
	 * Получить кол-во дней с начала СЛО и СЛС при создании дневника в СЛО #135
	 * @param aMedcaseId DepartmentMedCase.id
	 * @return String json Кол-во дней
	 */
	public String getSloCountDays(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
        JSONObject res=new JSONObject();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select cast(round((EXTRACT(EPOCH FROM current_timestamp)" +
				"-(SELECT EXTRACT(EPOCH FROM (hmc.datestart + hmc.entrancetime))  from medcase hmc where hmc.id=dmc.parent_id ))/3600/24) as int) as t1\n" +
				",cast(round((EXTRACT(EPOCH FROM current_timestamp)-(SELECT EXTRACT(EPOCH FROM (dmc.datestart + dmc.entrancetime))  " +
				"from medcase dmc where dmc.id="+aMedcaseId+"))/3600/24) as int) as t2\n" +
				"from medcase dmc\n" +
				"left join medcase hmc on hmc.id=dmc.parent_id\n" +
				"where dmc.id=" + aMedcaseId) ;
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next() ;
			res.put("hmcCnt",w.get1())
                    .put("dmcCnt",w.get2());
		}
		return res.toString();
	}

    /**
     * Получить браслеты пациента в госпитализации или в персоне #151
     * @param aSlsOrPatId Sls.id or Patient.id
     * @param aSlsOrPat 1 - СЛС, 0 - пациент
     * @return String json Браслеты пациента
     */
    public String selectIdentityPatient(Long aSlsOrPatId, Boolean aSlsOrPat, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        StringBuilder sql = new StringBuilder();
        sql.append("select cip.id,vc.name||' ('||vcip.name||')',vc.code as colorCode,vcip.name as vsipnameJust,vc.picture as pic, cip.info as inf ")
				.append("from vocColorIdentityPatient vcip ")
				.append("left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id ")
				.append("left join voccolor vc on vcip.color_id=vc.id ")
				.append(aSlsOrPat? "left join medcase_coloridentitypatient " : "left join patient_coloridentitypatient ")
				.append(" ss on ss.colorsidentity_id=cip.id where ")
				.append(aSlsOrPat? "medcase_id='" : "patient_id='")
				.append(aSlsOrPatId).append("' and cip.startdate<=current_date and (cip.finishdate is null or cip.finishdate>=current_date)");
        JSONArray res = new JSONArray() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		for (WebQueryResult w :list) {
			JSONObject o = new JSONObject() ;
			o.put("vcipId", w.get1())
					.put("vsipName", w.get2())
					.put("colorCode", w.get3())
					.put("vsipnameJust", w.get4())
					.put("picture", w.get5()!=null? w.get5() : "")
					.put("info", w.get6()!=null? w.get6() : "");
			res.put(o);
		}
        return res.toString();
    }

	/**
	 * Добавить браслет пациенту/СЛС #151
	 * @param aSlsOrPatId Sls.id or Patient.id
	 * @param aSlsOrPat 1 - СЛС, 0 - пациент
	 * @param idP vocColorIdentityPatient.id
	 * @return void
	 */
	public void addIdentityPatient(Long aSlsOrPatId, Boolean aSlsOrPat, Long idP, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql = new StringBuilder() ;
		String id="";
		Collection<WebQueryResult> res = service.executeNativeSql("insert into coloridentitypatient(startdate,finishdate,voccoloridentity_id,createusername) values(current_date,null,"+idP+",'"+login+"') returning id") ;
		for (WebQueryResult wqr : res) {
			id = wqr.get1().toString();
		}
		if (!id.equals("")) {
			sql.append("insert into ")
					.append(aSlsOrPat ? "medcase_coloridentitypatient(medcase_id, " : "patient_coloridentitypatient(patient_id, ")
					.append(" colorsidentity_id) values(")
					.append(aSlsOrPatId).append(",").append(id).append(")");
			service.executeUpdateNativeSql(sql.toString());
		}
	}

	/**
	 * Удалить браслет у пациента/СЛС (проставить дату окончания) #151
	 * @param aColorIdentityId ColorIdentityPatient.id
	 * @return void
	 */
	public void deleteIdentityPatient(Long aColorIdentityId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		//закрывается вчерашним днём, чтобы сразу снимался браслет
		service.executeUpdateNativeSql("update coloridentitypatient set finishdate=current_date-1,editusername='"+login+"' where id="+aColorIdentityId);
	}

    /**
     * Получить parent СЛС в СЛО #151
     * @param aSloId HDeparetmentMedCase.id
     * @return String parent СЛС
     */
    public String getParentId(String aSloId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Collection<WebQueryResult> l= service.executeNativeSql("select parent_id from medcase where id="+aSloId) ;
        return (!l.isEmpty() && l.iterator().next().get1()!=null)? l.iterator().next().get1().toString():"";
    }

	/**
	 * Получить, была ли проведена идентификация #173
	 * @param aMedCaseId Medcase.id (СЛС или СЛО)
	 * @return String 1 - была проведена идентификация, 0 - нет
	 */
	public String getIsPatientIdentified(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException, JspException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/PatientIdentify",aRequest)) return "1";
		Collection<WebQueryResult> l= service.executeNativeSql
				("select case when max(cast(isidentified as int))=1 then '1' else '0' end from medcase where id='"+aMedCaseId+"' or id=(select parent_id from medcase where id='"+aMedCaseId+"')") ;
		return (!l.isEmpty() && l.iterator().next().get1()!=null)? l.iterator().next().get1().toString():"";
	}

	/**
	 * Проставить, что была проведена идентификация #173
	 * @param aMedCaseId Medcase.id (СЛС или СЛО)
	 */
	public void setIsPatientIdentified(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		service.executeUpdateNativeSql
				("update medcase set isidentified='1', identdate=current_date,identtime=current_time,identusername='"
						+login+"' where id='"+aMedCaseId+"' or id=(select parent_id from medcase where id='"+aMedCaseId+"')") ;
	}

	/**
	 * Получить id карты оценки по коду
	 * @param aCode Code
	 * @return String
	 */
	public String getDiabetCardByCode(String aCode, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select id from assessmentCardTemplate where code='"+aCode+"'") ;
		return (!l.isEmpty() && l.iterator().next().get1()!=null)? l.iterator().next().get1().toString():"";
	}


	/**
	 * Получить офтальмологическое отделение
	 * @return
	 */
	public String getOpthalmicDep(HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select id from mislpu where isophthalmic=true") ;
		return l.isEmpty()? "" : l.iterator().next().get1().toString();
	}

	/**
	 * Установить дату предварительной госп на введении инг. анг. #181
	 * @param aPreId Long Предв. госп.
	 * @param dateFrom String Дата предв. госп.
	 */
	public void setPreHospOphtDate(Long aPreId, String dateFrom, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		service.executeUpdateNativeSql
				("update WorkCalendarHospitalBed set dateFrom=to_date('"+dateFrom+"','dd.mm.yyyy'), editdate=current_date,edittime=current_time,editusername='"
						+login+"' where id="+aPreId) ;
	}

	/**
	 * Получить, необходимо ли при переводе заполнять карту по нозологиям в акушерстве
	 * Необходимо, в случае перевода и родового или патологии беременности
	 * @param aSloId DepartmentMedCase.id
	 * @return 0 если необходимо
	 */
	public String checkNessessaryTransferNosologyCard(String aSloId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l= service.executeNativeSql("select case when lpu.ismaternityward=true or lpu.ispatologypregnant is true then '0' else '' end" +
				" from medcase dmc" +
				" left join mislpu lpu on lpu.id=dmc.department_id" +
				" where dmc.id=" + aSloId) ;
		return l.isEmpty()? "" : l.iterator().next().get1().toString();
	}

    /**
     * Получить, необходимо ли при выписке заполнять карту по нозологиям в акушерстве
     * Необходимо, в случае выписки из патологии беременности (последнее отделение в СЛС)
     * @param aSlsId DepartmentMedCase.id
     * @return 0 если необходимо
     */
    public String checkNessessaryDischargeNosologyCard(String aSlsId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Collection<WebQueryResult> l= service.executeNativeSql("select case when lpu.ispatologypregnant is true then '0' else '' end" +
                " from medcase dmc" +
                " left join mislpu lpu on lpu.id=dmc.department_id" +
                " left join medcase hmc on hmc.id=dmc.parent_id" +
                " where hmc.id=" + aSlsId + " and dmc.id = (select max(id) from medcase " +
                " where dtype='DepartmentMedCase' and parent_id=" + aSlsId + ")") ;
        return l.isEmpty()? "" : l.iterator().next().get1().toString();
    }

	/**
	 * Вывести данные для сопутствующих диагнозов в карте нозологий #185.
	 *
	 * @param aSlsId HospitalMedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json с результатом
	 */
	public String getConcomitantDiagnosisFromNosCard(Long aSlsId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select idc.id as id,idc.code||' '||idc.name as idcname" +
				" from birthnosologycard_vocbirthnosology bb" +
				" left join vocbirthnosology vb on vb.id=bb.nosologies_id" +
				" left join vocidc10 idc on idc.id=vb.idcCode_id" +
				" left join birthnosologycard b on b.id=bb.birthnosologycard_id" +
				" where b.medcase_id=" + aSlsId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		JSONArray res = new JSONArray() ;
		for (WebQueryResult w : list) {
			JSONObject o = new JSONObject() ;
			o.put("idcId", w.get1())
					.put("idcName", w.get2());
			res.put(o);
		}
		return res.toString();
	}
}