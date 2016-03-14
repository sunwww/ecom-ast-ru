package ru.ecom.mis.web.dwr.medcase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.ejb.service.ISoftConfigService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.poly.web.dwr.TicketServiceJs;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * Сервис по случаю лечения в стационаре
 * @author Tkacheva Sveltana
 */
public class HospitalMedCaseServiceJs {
	public String toNull (String aValue) {
		if (aValue==null ||aValue.equals("")||aValue.trim().equals("")) return "null";
		return aValue.trim();
	}
	public String createTemperatureCurve (Long aMedCase, String aParams, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		String[] par = aParams.split(":");
		System.out.println("=== == medcase = "+aMedCase+" = " +aParams+" = length "+par.length);
		String takingDate = toNull(par[0]);
		String pulse = toNull(par[6]);
		String bloodPressureDown = toNull(par[5]);
		String bloodPressureUp = toNull(par[4]);
		String weight = toNull(par[9]);
		String respirationRate = toNull(par[8]);
		String degree = toNull(par[7]);
		String illnessDayNumber = toNull(par[1]);
		String dayTime = toNull(par[3]);
		
		String stool = par.length>10?toNull(par[10]):"null";
		
		//String hospDayNumber = par[2];
		
		
		
		 
	
		StringBuilder sql = new StringBuilder();
		sql.append("insert into temperatureCurve (takingDate, pulse, bloodPressureDown, bloodPressureUp, weight, respirationRate, degree")
		.append(", illnessdaynumber, daytime_id, medcase_id, stool_id) values (");
		
		sql.append("to_date('"+takingDate+"','dd.MM.yyyy'),"+pulse+","+bloodPressureDown+","+bloodPressureUp+","+weight+","+respirationRate);
		sql.append(", "+degree+", "+illnessDayNumber+", "+dayTime+", "+aMedCase+", "+stool);
		sql.append(")");
		System.out.println("=== === "+sql);
		return "" + service.executeUpdateNativeSql(sql.toString());
		
	}
	public String getServiceStreamAndMkbByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select smc.serviceStream_id||'@@'||vss.name as vssname, max(case when vpd.code='1' and vdrt.code='3' then mkb.id||'@@'||mkb.code||' '||mkb.name else null end) as mkbname")
			.append(", max(case when vpd.code='1' and vdrt.code='4' then mkb.id||'@@'||mkb.code||' '||mkb.name else null end) as mkbname1")
			.append(" from medcase smc")
			.append(" left join Diagnosis d on d.medCase_id=smc.id")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id")
			.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id")
			.append(" left join VocIdc10 mkb on mkb.id=d.idc10_id")
			.append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
			.append(" where smc.id='").append(aMedCase).append("' group by smc.serviceStream_id,vss.name") ;
		List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString()) ;
		if (!resL.isEmpty()) {
			Object[] obj = resL.get(0) ;
			res=new StringBuilder();
			if (obj[0]!=null) {res.append(obj[0]);} else {res.append("@@");}
			res.append("@@") ;
			if (obj[1]!=null) {res.append(obj[1]);} else {if (obj[2]!=null) {res.append(obj[2]);} else {res.append("@@");}}
			res.append("@@") ;
		} else {
			res.append("@@") ;res.append("@@") ;res.append("@@") ;res.append("@@") ;
		}
		return res.toString() ;
	}
	public String getServiceByMedCase(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select smc.medservice_id,ms.code||' '||ms.name as serviceName")
			.append(",smc.workfunctionexecute_id,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo")
			.append(",smc.idc10_id,mkb.code||' '||mkb.name as mkb")
			.append(",to_char(smc.datestart,'dd.mm.yyyy') as datestart,smc.medserviceAmount")
			.append(",smc.serviceStream_id,vss.name as vssname")
			.append(" from medcase smc")
			.append(" left join MedService ms on ms.id=smc.medservice_id")
			.append(" left join WorkFunction wf on wf.id=smc.workFunctionExecute_id")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join Patient wp on wp.id=w.person_id")
			.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" left join VocIdc10 mkb on mkb.id=smc.idc10_id")
			.append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id")
			.append(" where smc.parent_id='").append(aMedCase).append("' and smc.dtype='ServiceMedCase' order by smc.id") ;
		List<Object[]> resL = service.executeNativeSqlGetObj(sql.toString()) ;
		for (int i=0;i<resL.size();i++) {
			for (int j=0;j<10;j++) {
				res.append(resL.get(i)[j]).append("@#@") ;
			}
			res=new StringBuilder(res.length()>0?res.toString().trim().substring(0,res.length()-3):"");
			res.append("#@#") ;
		}
		return res.length()>0?res.toString().trim().substring(0,res.length()-3):"" ;
	}
	
	public String saveServiceByMedCase(Long aMedCase, String aServices, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder res = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		service.executeUpdateNativeSql("delete from medcase where parent_id="+aMedCase+" and dtype='ServiceMedCase'");
		if (aServices!=null&&!aServices.equals("")) {
			String[] otherServs = aServices.split("#@#");
			if (otherServs.length>0) {
				
				for (int i=0;i<otherServs.length;i++) {
					String[] serv = otherServs[i].split("@#@") ;
					sql = new StringBuilder() ;
					sql.append("insert into medcase (noActuality,dtype,createdate,createtime,username,parent_id")
						.append(",medservice_id,workfunctionexecute_id,idc10_id")
						.append(",datestart,medserviceAmount,serviceStream_id) values (") ;
					sql.append("'0','ServiceMedCase',current_date,current_time,'").append(login)
						.append("','").append(aMedCase).append("','").append(serv[0]).append("','").append(serv[1]).append("','").append(serv[2])
						.append("',to_date('").append(serv[3]).append("','dd.mm.yyyy'),'").append(serv[4]).append("','").append(serv[5])
						.append("')");
					System.out.println(sql) ;
					service.executeUpdateNativeSql(sql.toString()) ;
				}
			}
		}

		return "" ;
	}
	
	public String checkEditProtocolControl(Long aDiaryMessage, Long aDiary, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		List<Object[]> list = service.executeNativeSqlGetObj("select dm.diary_id,dm.record from diarymessage dm left join diary d on d.id=dm.diary_id where (dm.validitydate>current_date or dm.validitydate=current_date and dm.validitytime>=current_time) and d.username='"+login+"' and d.id='"+aDiary+"'") ;
		if (list.size()>0) {
			Object[] obj=list.get(0) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("update diary set record='").append(obj[1]).append("',editdate=current_date,edittime=current_time where id="+aDiary) ;
			service.executeUpdateNativeSql(sql.toString()) ;
			service.executeUpdateNativeSql("update diarymessage dm set IsDoctorCheck='1' where dm.diary_id="+aDiary) ;
		}
		return "" ;
	}
	public String getDiaryDefects(Long aDiaryId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder req = new StringBuilder();
		StringBuilder res = new StringBuilder();
		req.append("select vdd.id, vdd.name from VocDefectDiary vdd ");
		req.append("order by vdd.id ");
		List<Object[]> list = service.executeNativeSqlGetObj(req.toString()) ;
		for (int i=0;i<list.size();i++) {
			Object[] obj = list.get(i);
			res.append(obj[0]).append(":").append(obj[1]).append("#");
		}
		
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	public String getDiaryText(Long aDiaryId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		List<Object[]> list = service.executeNativeSqlGetObj("select id,record from diary where id="+aDiaryId) ;
		StringBuilder ret = new StringBuilder() ;
		if (list.size()>0) {
			ret.append(list.get(0)[1]) ;
		}
		return ret.toString() ;
	}
	public String setDiaryDefect(Long aDiaryId, Long aDefectId, String aComment,String aRecord,String aVk, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Calendar cal = Calendar.getInstance() ;
		cal.add(Calendar.HOUR, 24) ;
		boolean vk = (aVk!=null && aVk.equals("1"))?true:false ;
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy") ;
		sql.append("insert into DiaryMessage (isVk,diary_id,defect_id,comment,record,createusername,createdate,createtime,validitydate,validitytime) ")
			.append("values ('").append(vk?"1":"0").append("','").append(aDiaryId).append("','")
			.append(aDefectId).append("','").append(aComment).append("','").append(aRecord)
			.append("','").append(login).append("',current_date,current_time,to_date('").append(f.format(cal.getTime())).append("','dd.mm.yyyy'),current_time)") ;
		System.out.println(sql) ;
		service.executeUpdateNativeSql(sql.toString()) ;
		sql = new StringBuilder() ;
		List<Object[]> list = service.executeNativeSqlGetObj("select d.id,d.username,to_char(d.dateregistration,'dd.mm.yyyy')||' '||pat.lastname from diary d left join medcase mc on mc.id=d.medcase_id left join patient pat on pat.id=mc.patient_id where d.id='"+aDiaryId+"'") ;
		if (list.size()>0) {
			Object[] obj = list.get(0) ;
			String username=""+obj[1] ;
			
			sql = new StringBuilder() ;
			
			sql.append("insert into CustomMessage (messageText,messageTitle,recipient")
				.append(",dispatchDate,dispatchTime,username,validityDate,messageUrl)") 
				.append("values ('").append("На исправление дневник").append("','")
				.append(obj[2]).append("','").append(username)
				.append("',current_date,current_time,'").append(login).append("',current_date,'")
				.append("js-stac_slo-list_edit_protocol.do?id="+aDiaryId).append("')") ;
			service.executeUpdateNativeSql(sql.toString()) ;
		}
		return "1" ;
	}
	public String getDiariesByHospital(Long aMedcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder res = new StringBuilder();
		String sql = "select d.id, to_char(d.dateregistration,'dd.MM.yyyy')|| ' ' ||to_char(d.timeregistration,'HH:MI') as dt," +
			" d.record from medcase sls " +
			" left join medcase vis on vis.patient_id=sls.patient_id" +
			" left join diary d on d.medcase_id= vis.id" +
			" where sls.id="+aMedcaseId +
			" and d.dtype='Protocol'" +
			" and (vis.dtype='Visit' or vis.dtype='HospitalMedCase' or vis.dtype='DepartmentMedCase')" +
			" and d.dateregistration is not null and d.dateregistration>=sls.datestart" +
			" and case when vis.dtype='Visit' and vis.parent_id!=sls.id then (select case when vwf.isNoDiagnosis='1' then '1' else '0' end from medcase v" +
			" left join workfunction wf on wf.id=v.workfunctionexecute_id" +
			" left join vocworkfunction vwf on vwf.id=wf.workfunction_id where v.id=vis.id) else '1' end = '1'" +
			" order by d.dateregistration desc, d.timeregistration desc";
		Collection<WebQueryResult> wqr = service.executeNativeSql(sql);
		if (!wqr.isEmpty()) {
			for (WebQueryResult w: wqr) {
				res.append(w.get1()).append("#").append(w.get2()).append("#").append(w.get3()).append("@");
			}
		}
		
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	
	public String getPrefixByProtocol(Long aDiaryId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql("select vtp.prefixprint from Diary d left join voctypeprotocol vtp on vtp.id=d.type_id where d.id="+aDiaryId+" and vtp.prefixprint is not null") ;
		if (list.isEmpty()) {
			return null ;
		} else {
			return new StringBuilder().append(list.iterator().next().get1()).toString() ;
		}
		
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
		if (aMode==null||aMode.equals("1")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where isTable4='1'") ;
		} else if (aMode==null||aMode.equals("2")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2 is null or isTable2='0') and (isTable3 is null or isTable3='0') and (isTable4 is null or isTable4='0') and (isTable5 is null or isTable5='0')") ;
		} else if (aMode==null||aMode.equals("3")) {
			service.executeUpdateNativeSql("delete from HospitalDataFond where (isTable2='1' or isTable3='1') and (isTable5 is null or isTable5='0')") ;
		}
		return null;
	}
	public String deleteHDF(String aNapr,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("delete from HospitalDataFond where id='"+aNapr+"'") ;
		return null;
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
		if (aValSet==null || aValSet.equals("null")) {
			service.executeUpdateNativeSql("update "+aTable+" set "+aFldSet+"=null where "+aFldId+"='"+aValId+"' "+(aWhereAdd!=null &&!aWhereAdd.equals("")?" and "+aWhereAdd:"" )) ;
		} else {
			service.executeUpdateNativeSql("update "+aTable+" set "+aFldSet+"='"+aValSet+"' where "+aFldId+"='"+aValId+"' "+(aWhereAdd!=null &&!aWhereAdd.equals("")?" and "+aWhereAdd:"" )) ;
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
			.append(" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id")
			.append(" where sls.id='"+aMedCaseId+"' and (ml.isnoomc is null or ml.isnoomc='0') ")
			.append(" group by sls.id,slo.id,ml.name,slo.dateStart,slo.transferDate	")
			.append(" having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  ") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		if (list.size()>0) {
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
		if (aYesNoId != null && !aYesNoId.equals(Long.valueOf(0))) {
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
		ISoftConfigService sservice = Injection.find(aRequest).getService(ISoftConfigService.class) ;
		//StringBuilder url = new StringBuilder() ;
		String code_page = ""+getDefaultParameterByConfig("code_csp_page", "CP1251", aRequest) ;
		String address_lpu = ""+getDefaultParameterByConfig("address_lpu", "_______________________________________________________", aRequest) ;
		String name_lpu = ""+getDefaultParameterByConfig("name_lpu", "_______________________________________________________", aRequest) ;
		name_lpu = name_lpu.replaceAll("\"", "%22");
		//url.append(aRequest.getServerName()) ;
		Object csp_port=getDefaultParameterByConfig("csp_post", "57772", aRequest) ;
		
		if (aRequest.getServerPort()>1000) {
			//url.append(!csp_port.equals("")?(":"+csp_port):"").append(aRequest.getServerPort()) ;
		}
		String cspurl = new StringBuilder().append("expert").append(!csp_port.equals("")?(":"+csp_port):"")
				.append("/csp/").append(getDefaultParameterByConfig("data_base_namespace", "riams", aRequest)).toString() ;
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
		if (aType!=null &&aType.equals("HOSP")) {
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
			sql.append(" where sls.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' "+aSqlAdd+") and sls.dischargeTime is not null") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
			if (l.isEmpty()) new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ПО ВЫПИСАННЫМ ОМС БОЛЬНЫМ!!!") ;
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
				//System.out.println(url_csp) ;
				
				StringBuilder c ;
				if (aIsUrl) {
					res.append(" ").append(wqr_slo.get17()).append(" С ").append(wqr_slo.get15()).append(" ПО ").append(wqr_slo.get16()).append(" url=").append(url_csp) ;
				} else {
					String cost = ActionUtil.getContentOfHTTPPage(url_csp.toString().replaceAll(" ", ""),code_page);
					//String cost = "11#2222" ;
					//System.out.println("----cost--->"+cost) ;
					if (isf) {
						res.append("&render=") ;
						isf=false;
					} else {
						res.append("%23%23") ;
					}
					c = getRender(cost,new StringBuilder().append(wqr_slo.get17()).append(". КОЙКИ "),new StringBuilder().append(" С ").append(wqr_slo.get15()).append(" ПО ").append(wqr_slo.get16())) ;
					res.append(c.toString().replaceAll("#", "%23").replaceAll(" ", "%20")) ;
				}
				
				
				//System.out.println(res.toString()) ;
				}
			
			}
			//res.append("&render=21663%23ПУЛЬМОНОЛОГИЧЕСКОЕ ОТДЕЛЕНИЕ. КОЙКИ АЛЛЕРГОЛОГИЧЕСКИЕ. КРУГЛОСУТОЧНЫЙ СТАЦИОНАР, ПОЛНЫЙ СЛУЧАЙ,ВЗРОСЛЫЕ С 03.01.2014 ПО 14.01.2014") ;
		} else if (aType!=null && aType.equals("PREVISIT")) {
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
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' "+aSqlAdd+") and mp.actualdatefrom <=coalesce(vis.dateStart,wcd.calendardate,vis.datefinish) and coalesce(mp.actualdateto,vis.datestart,wcd.calendardate,vis.datefinish)>=coalesce(vis.datestart,wcd.calendardate,vis.datefinish) and mp.dtype like 'MedPolicyOm%'") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
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
				System.out.println("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
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
		} else if (aType!=null && aType.equals("VISIT")) {
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
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' "+aSqlAdd+") and mp.actualdatefrom <=vis.dateStart and coalesce(mp.actualdateto,vis.datestart)>=vis.datestart and mp.dtype like 'MedPolicyOm%'") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
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
				System.out.println("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
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
		} else if (aType!=null && aType.equals("SPO")) {
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
			sql.append(" where vis.id=").append(aMedCase).append(" and (vss.code='OBLIGATORYINSURANCE' "+aSqlAdd+") and (spo.dateFinish is not null or vis.emergency='1') and mp.actualdatefrom <=vis.dateStart and coalesce(mp.actualdateto,vis.datestart)>=vis.datestart and mp.dtype like 'MedPolicyOm%'") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql(sql.toString(),1) ;
			if (l.isEmpty()) new Exception("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ЗАКРЫТОМУ СЛУЧАЮ ПОЛИКЛИНИЧЕСКОГО ОБСЛУЖИВАНИЯ ПО ОМС БОЛЬНЫМ!!!") ;
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
				//System.out.println("http://"+cspurl+"/getmedcasecost.csp?CacheUserName=_system&CachePassword=sys"+href_vis.toString()) ;
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
		} else if (aType!=null && aType.equals("EXTDISP")) {
			//Дополнительная диспансеризация
			href.append("AddDisp=").append("") ;
			href.append("AddDispAge=").append("") ;
			href.append("AddDispCases=").append("") ;
			href.append("AddDispHealthGroup=").append("") ;
			
			Collection<WebQueryResult> l = service.executeNativeSql("select to_char(sls.dateStart,'yyyymmdd') as datestart,to_char(sls.dateFinish,'yyyymmdd') as dateFinish from MedCase sls where sls.id="+aMedCase) ;
			WebQueryResult wqr = l.iterator().next() ;
			href.append("AdmissionDate=").append("") ;
			href.append("BedDays=").append("") ;
			href.append("BirthDate=").append("") ;
			href.append("DepType=").append("") ;
			href.append("DiagnosisList=").append("") ;
			href.append("DiagnosisMain=").append("") ;
			href.append("DiagnosisConcomitant=").append("") ;
			href.append("DischargeDate=").append("") ;
			href.append("DoctorPost=").append("") ;
			href.append("Emergency=").append("") ;
			href.append("Foreigns=").append("") ;
			href.append("HotelServices=").append("") ;
			href.append("Hts=").append("") ;
			href.append("Lpu=").append("") ;
			href.append("LpuFunciton=").append("") ; //
			href.append("Operations=").append("") ;
			href.append("Reason=").append("") ;
			href.append("ReasonC=").append("") ;
			href.append("Render=").append("") ;
			href.append("Result=").append("") ;
			href.append("Sex=").append("") ;
			href.append("VidLpu=").append("") ;
			href.append("Yet=").append("") ;
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
		service.updateDataFromParameterConfig(aDepartment, aIsLowerCase!=null && aIsLowerCase.equals(Long.valueOf(1)), aIds, false) ;
		return ;
	}
	public void removeDataFromParameterConfig(Long aDepartment, String aIds, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.removeDataFromParameterConfig(aDepartment, aIds) ;
		return ;
	}
	public String changeServiceStreamBySmo(Long aSmo, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.changeServiceStreamBySmo(aSmo, aServiceStream) ;
		return "Поток обслуживания изменен" ;
	}
	public String unionSloWithNextSlo(Long aSlo,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.unionSloWithNextSlo(aSlo) ;
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
		return "Обновлены" ;
	}
	public String preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis,HttpServletRequest aRequest) throws Exception {
		IWebQueryService service1 = Injection.find(aRequest).getService(IWebQueryService.class) ;
		boolean isEdit =true ;
		boolean isCurentOnly = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay",aRequest) ;
		if (isCurentOnly) {
			StringBuilder sql = new StringBuilder() ;
			sql.append("select SLS.id from medcase SLS  left join vochospitalizationresult vhr on vhr.id= SLS.result_id where SLS.id='").append(aMedCaseId).append("' and (sls.dateFinish is not null or (vhr.code='11' and (current_date-sls.datefinish)<4))") ;
			Collection<WebQueryResult> list = service1.executeNativeSql(sql.toString()) ;
			if (list.size()>0) {
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
	//Получить данные диагноза по умолчанию для акушерства
	public String getIdc10ByDocDiag(Long aIdDocDiag,HttpServletRequest aRequest) throws NamingException { 
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getIdc10ByDocDiag(aIdDocDiag) ;
	}
	public String getTypeDiagByAccoucheur(HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getTypeDiagByAccoucheur() ;
	}
	public String getRW(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getRW(aMedCase) ;
	}
	public String setRW(Long aMedCase, String aRwDate, String aRwNumber, HttpServletRequest aRequest) throws NamingException, ParseException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.setRW(aMedCase, aRwDate, aRwNumber) ;
		return "" ;
	}
    public String changeStatCardNumber(String aNewStatCardNumber, Long aMedCase, HttpServletRequest aRequest) throws NamingException, JspException {
    	System.out.println(
    			new StringBuffer()
    			.append("Изменение номера стат. карты: ")
    			.append(aNewStatCardNumber)
    			.append(" случая лечения в стационаре #")
    			.append(aMedCase)
    		) ;
    	boolean always = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber", aRequest) ;
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        //return service.(aStatCardNumber, aDateStart, aEntranceTime, aPatient);
        return service.getChangeStatCardNumber(aMedCase, aNewStatCardNumber,always) ;
    }
    public String getListTemperatureCurve(Long aMedCase, HttpServletRequest aRequest) throws Exception {
    	System.out.println("Температурная кривая");
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
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException, ParseException {
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
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException, ParseException {
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
			,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs, boolean aLuchIs, HttpServletRequest aRequest) throws NamingException, ParseException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getnvestigationsTextDTM(aPatient, aDateStart, aDateFinish,aLabsIs,aFisioIs,aFuncIs,aConsIs, aLuchIs);
    }
    public String getPlannigHospitalization(String aDateFrom, String aDateTo
    		,Long aDepartment, Long aBedType, Long aCountBed) {
    	
    	return "" ;
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
    	if (list.size()>0) {
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
    	sql = new StringBuilder() ;
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
    	sql = new StringBuilder() ;
    	sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment) ;
    	if (aServiceStream!=null && aServiceStream.intValue()>0) sql.append("' and bf.serviceStream_id='").append(aServiceStream) ;
    	sql.append("' and bf.dateFinish is null order by bf.amount desc") ;
    	
    	
    	
    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	if (list.size()>0) {
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
    	sql = new StringBuilder() ;
    	sql.append("select vbt.id as vbtid, vbt.name as vbtname,vbst.id as vbstid,vbst.name as vbstname from BedFund bf ") ;
    	sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
    	sql.append(" where bf.lpu_id='").append(aDepartment) ;
    	if (aServiceStream!=null && aServiceStream.intValue()>0) sql.append("' and bf.bedType_id='").append(aBedType) ;
    	sql.append("' and bf.serviceStream_id='").append(aServiceStream) ;
    	sql.append("' and bf.dateFinish is null") ;
    	
    	
    	
    	Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),2) ;
    	if (list.size()>0) {
    		WebQueryResult wqr = list.iterator().next() ;
    		//res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
    		res.append(wqr.get3()).append("#").append(wqr.get4()).append("#") ;
    		
    	} else {
    		res.append("##");
    	}
    	
    	return res.toString() ;
    }
    public String getDefaultBedFundBySlo(Long aParent, Long aDepartment, Long aServiceStream, String aDateFrom,HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sql = new StringBuilder() ;
    	StringBuilder res = new StringBuilder() ;
    	Collection<WebQueryResult> list = service.executeNativeSql("select vht.id,vht.code from MedCase hmc left join VocHospType vht on vht.id=hmc.hospType_id where hmc.id='"+aParent+"' and vht.code is not null",1) ;
    	String bedSubType="";
    	String hospType=null ;
    	if (list.size()>0) {
    		WebQueryResult wqr = list.iterator().next() ;
    		hospType=""+wqr.get2() ;
    	}
    	if (hospType!=null && hospType.equals("DAYTIMEHOSP")) {
    		bedSubType=" and vbst.code='2' ";
    	} else if (hospType==null||hospType.equals("ALLTIMEHOSP")) {
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
		if (list.size()>0) {
			WebQueryResult wqr = list.iterator().next() ;
			hospType=""+wqr.get2() ;
		}
		if (hospType.equals("DAYTIMEHOSP")) {
			bedSubType=" and vbst.code='2' ";
		} else if (hospType.equals("ALLTIMEHOSP")) {
			bedSubType=" and vbst.code='1' ";
		}
    	sql = new StringBuilder() ;
		sql.append("select bf.id, vbt.name||' ('||vbst.name||' ' || case when bf.noFood='1' then 'без питания' else 'с питанием' end ||')' from BedFund bf ") ;
		sql.append(" left join vocBedType vbt on vbt.id=bf.bedType_id left join vocBedSubType vbst on vbst.id=bf.bedSubType_id ") ;
		sql.append(" where bf.lpu_id='").append(aDepartment)
			.append("' and bf.serviceStream_id='").append(aServiceStream)
			.append("' and to_date('").append(aDateFrom)
			.append("','dd.mm.yyyy') between bf.dateStart and coalesce(bf.dateFinish,CURRENT_DATE)") ;
		sql.append(" ").append(bedSubType).append("");
		String username=LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql1=new StringBuilder();
		sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
			.append(" left join Worker w on w.id=wf.worker_id")
			.append(" left join SecUser su on su.id=wf.secUser_id")
			.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
			.append(" where su.login = '").append(username).append("' and w.lpu_id='").append(aDepartment).append("' and wf.id is not null") ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql1.toString(),1) ;
		if (list1.size()>0) {
			WebQueryResult wqr = list1.iterator().next() ;
			res.append(wqr.get1()).append("#").append(wqr.get2()).append("#") ;
		} else {
			sql1=new StringBuilder();
			sql1.append("select wf.id as wfid,case when wf.code is null then '' else wf.code||' ' end || vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename from WorkFunction wf")
				.append(" left join Worker w on w.id=wf.worker_id")
				.append(" left join SecUser su on su.id=wf.secUser_id")
				.append(" left join Patient as p on p.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
				.append(" where w.lpu_id='").append(aDepartment).append("' and wf.isAdministrator='1'") ;
			list1.clear() ;
			list1 = service.executeNativeSql(sql1.toString(),1) ;
			if (list1.size()>0) {
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
}
