package ru.ecom.poly.web.dwr;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.mis.web.dwr.medcase.HospitalMedCaseServiceJs;
import ru.ecom.poly.ejb.services.ITicketService;
import ru.ecom.template.web.dwr.TemplateProtocolJs;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketServiceJs {

	public String showSimpleServiceBySpecialist(Long aWorkfunctionId, HttpServletRequest aRequest) throws SQLException, NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "select ms.id as serviceid, ms.code||' '||ms.name as servicename, case when link.isDefault='1' then 'true' else 'false' end as isDefault " +
				" from workfunction wf" +
				" left join vocworkfunction vwf on vwf.id=wf.workfunction_id " +
				" left join MedServiceComplexLink link on link.speciality_id=vwf.fondSpeciality_id" +
				" left join medservice ms on ms.id=link.innerMedService_id" +
				" where wf.id="+aWorkfunctionId+" and ms.id is not null order by link.weight";
		return service.executeSqlGetJson(sql);
	}
	
	public String getDefaultParameter(String aKey, String aDefaultValue, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection <WebQueryResult> res = service.executeNativeSql("select id,keyValue from SoftConfig where upper(key)=upper('"+aKey+"')");
		if (!res.isEmpty()){
			return res.iterator().next().get2().toString();
		} 
		return aDefaultValue;
	}
	public String[] getDiagnosisId (String[] diagnosis, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<diagnosis.length;i++) {
			if (i>0) {
				sb.append(",");
			}
			sb.append("'").append(diagnosis[i]).append("'");
		}
		String sql = "select id from vocidc10 where code in ("+sb.toString()+")";
		Collection <WebQueryResult> res = service.executeNativeSql(sql);
		StringBuilder ret = new StringBuilder();
		for (WebQueryResult r: res) {
			if (ret.length()>0) {ret.append(",");}
			ret.append(r.get1().toString());
		}
		return ret.toString().split(",");
	}
	private Collection<WebQueryResult> getPatients (String aAgeFrom, String aAgeTo, String aDateTo, String aSexId, String aLpu, String ids, int maxResults, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql="select pat.id as patId, mc.id as medId from medcard mc" +
				" left join patient pat on pat.id=mc.person_id" +
				" left join lpuattachedbydepartment att on att.patient_id=pat.id" +
				" where ";
		if (ids!=null && !ids.equals("")) {
			sql+=" pat.id in ("+ids+")";
		} else {
			String sexSql = (aSexId!=null&&!aSexId.equals(""))?" and pat.sex_id="+aSexId:"";
			if (aDateTo!=null && !aDateTo.equals("")) {
				aDateTo = "to_date('"+aDateTo+"','dd.MM.yyyy')";
			} else {
				aDateTo = "current_date";
			}
			if (aLpu!=null&&!aLpu.equals("")) {
				sexSql+=" and att.lpu_id="+aLpu+" and att.dateto is null";
			}
			sql += " cast(to_char("+aDateTo+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char("+aDateTo+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char("+aDateTo+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) between "+aAgeFrom + " and "+aAgeTo
					+" and pat.deathdate is null and (pat.noactuality is null or pat.noactuality='0')"+sexSql+" order by random()";
			//	System.out.println("==Find persons: "+sql);
		}

		return service.executeNativeSql(sql,maxResults);
	}
	
	public String generateTalons (String aWorkFunctionIds,String aDateFrom, String aDateTo, String times, Long serviceStream, Long workplace
			, Long visitReason, Long visitResult, String diagnosis, Long concludingActuity
			, Long recordCount, String aAgeFrom, String aAgeTo, String aSexId, String aLpu, String aPatientIds, boolean isProfOsmotr, HttpServletRequest aRequest) throws ParseException, NamingException {
		Date startDate = ru.nuzmsh.util.format.DateFormat.parseDate(aDateFrom);
		Date finishDate = ru.nuzmsh.util.format.DateFormat.parseDate(aDateTo);

		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String username = "ugolovasticov";
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		ITicketService ser = Injection.find(aRequest).getService(ITicketService.class);

		Calendar cal = new GregorianCalendar();
			cal.setTime(startDate);
			StringBuilder allDates = new StringBuilder();
			for (int i=0;true;i++) {
				if (cal.get(java.util.Calendar.DAY_OF_WEEK)== Calendar.SUNDAY) {
					cal.add(Calendar.DATE, 1);
					continue;}
				//System.out.println("=== i="+i+", date = "+sdf.format(cal.getTime()));
				if (i>0) {allDates.append("#");}			
				allDates.append(sdf.format(cal.getTime()));
				cal.add(Calendar.DATE, 1);
				if (cal.getTime().after(finishDate)) break;			
			}
			if (allDates.length()>0) {}else {return "" ;}
			//System.out.println("DATES = "+allDates);
			Collection<WebQueryResult> pats = getPatients(aAgeFrom, aAgeTo, aDateTo, aSexId, aLpu, aPatientIds, recordCount.intValue(), aRequest);
			if (pats.isEmpty()) return "";
			String mainPriority = service.executeNativeSql("select id from vocprioritydiagnosis where code='1'").iterator().next().get1().toString();
			
			Random rnd = new Random();
			String[] workFunctions = aWorkFunctionIds.replace(" ", "").split(",");
			String[] dates = allDates.toString().split("#"); 
			String[] diagnos = getDiagnosisId(diagnosis.replace(" ","").split(","), aRequest);
		//	String[] time = times.split(",");
			StringBuilder ids = new StringBuilder();
			for (WebQueryResult pat: pats) {
				String date = dates[rnd.nextInt(dates.length)];
				String dsId = diagnos[rnd.nextInt(diagnos.length)];
				String patId = pat.get1().toString();
				String medcardId = pat.get2().toString();
				Long spoId=null;
				if (isProfOsmotr) {
					spoId = ser.createMedcase("PolyclinicMedCase"); //Если профосмотр - все визиты в рамках одного осмотра
				}
				for (String aWorkFunctionId : workFunctions) {
					if (!isProfOsmotr) {
						spoId = ser.createMedcase("PolyclinicMedCase");
					}
					Long ticketId = ser.createMedcase("ShortMedCase");
					ids.append("Patient=").append(patId).append("&date=").append(date);
					ids.append("&wf=").append(aWorkFunctionId);
					String ticketSql = "update medcase set "
							+ "username='" + username + "', datestart=to_date('" + date + "','dd.MM.yyyy'), createdate=current_date"
							+ ", noactuality='0',parent_id=" + spoId + ", servicestream_id=" + serviceStream + ", patient_id=" + patId
							+ ",workfunctionexecute_id=" + aWorkFunctionId + ", visitresult_id=" + visitResult + ", visitreason_id=" + visitReason
							+ ", workplacetype_id=" + workplace
							+ ", createtime=cast('12:35' as time(5)), medcard_id=" + medcardId + " where id=" + ticketId + " and dtype='ShortMedCase'";
					String polSql = "update medcase set username = '" + username + "', datestart=to_date('" + date + "','dd.MM.yyyy'), datefinish=to_date('" + date + "','dd.MM.yyyy') , createdate=current_date" +
							" ,noactuality='0', lpu_id=(select coalesce(wf.lpu_id,w.lpu_id) from workfunction wf left join worker w on w.id=wf.worker_id where wf.id=" + aWorkFunctionId + ")" +
							" ,servicestream_id=" + serviceStream + ", patient_id=" + patId + ", ownerfunction_id=" + aWorkFunctionId + "" +
							" ,startfunction_id=" + aWorkFunctionId + ", finishfunction_id=" + aWorkFunctionId + ", idc10_id=" + dsId + " where id = " + spoId + " and dtype='PolyclinicMedCase'";
					ids.append("&ticket = ").append(ticketId);

					String diagnosisSql = "insert into diagnosis (name, establishdate, priority_id, idc10_id" +
							", medcase_id, illnesprimary_id, createusername) (select name , to_date('" + date + "','dd.MM.yyyy') " +
							", " + mainPriority + "," + dsId + "," + ticketId + "," + concludingActuity + ",'" + username + "'" +
							" from vocidc10 where id = " + dsId + ")";

					service.executeUpdateNativeSql(polSql); //Формируем СПО
					service.executeUpdateNativeSql(ticketSql); //Формируем визит
					service.executeUpdateNativeSql(diagnosisSql); //Формируем диагноз по визиту
					ids.append("&ds = ").append(dsId).append("\n");
				}
			}
		//	System.out.print(ids);
		
		return ids.toString();
		
	}
	
	public String deleteTalons (String aMedcardId, String aDate, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "delete from medcase where dtype = 'ShortMedCase' and dateStart is null";
		if (aMedcardId!=null&&!aMedcardId.equals("")) {
			sql +=" and medcard_id="+aMedcardId;
		}
		if (aDate!=null&&!aDate.equals("")) {
			sql +=" and createdate < to_date('"+aDate+"','dd.MM.yyyy')";
		}
		
		return "Успешно удалено " + service.executeUpdateNativeSql(sql) +" талонов";
	}

	/*Перекрестные СПО по пациенту и рабочей функции*/
	public String getCrossSPO(String aDate, Long aPatientId, Long aWorkfuntionId, HttpServletRequest aRequest) throws NamingException {
		String result = "";
		StringBuilder str = new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		str.append("select spo.id, to_char(spo.datestart,'dd.MM.yyyy') as dstart,to_char(spo.datefinish,'dd.MM.yyyy') as dfinish " +
                " from medcase spo " +
                " left join workfunction wf on wf.id=spo.ownerfunction_id" +
                " where spo.dtype='PolyclinicMedCase' and spo.datefinish is not null")
                .append(" and spo.patient_id=").append(aPatientId)
                .append(" and wf.workfunction_id = (select workfunction_id from workfunction where id=").append(aWorkfuntionId).append(")")
                .append(" and spo.datestart<=to_date('").append(aDate).append("','dd.MM.yyyy')")
                .append(" and spo.datefinish >=to_date('").append(aDate).append("','dd.MM.yyyy')");
		Collection res = service.executeNativeSql(str.toString(), 1);
		if (!res.isEmpty()) {
			WebQueryResult r = (WebQueryResult)res.iterator().next();
			result = r.get1().toString() + ":" + r.get2().toString() + ":" + r.get3().toString();
		}
		return result;
	}
	public String getUetByService(Long aService, HttpServletRequest aRequest) throws NamingException {
        String result = "";
        StringBuilder str = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        str.append("select uet from medservice where id=").append(aService).append(" and uet is not null");
        Collection<WebQueryResult> res = service.executeNativeSql(str.toString(), 1);
        if (!res.isEmpty()) {
            WebQueryResult r = res.iterator().next();
            result = r.get1().toString();
        }
        return result;
    }

	public String getInfoByTicket(Long aTicket, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select vss.id as vssid,vss.name as vssname,vwpt.id as vwptid") ;
		sql.append(" ,vwpt.name as vwptname,vr.id as vrid,vr.name as vrname") ;
		sql.append(" ,vvr.id as vvrid,vvr.name as vvrname,vh.id as vhid") ;
		sql.append(" ,vh.name as vhname,vdr.id as vdrid,vdr.name as vdrname,case when smc.emergency='1' then '1' else '0' end as emegrency") ;
		sql.append(" ,list(case when vpd.code='1' then mkb.id||'##'||mkb.code||' '||mkb.name||'##'||diag.name||'##'||vip.id||'##'||vip.name else null end) as diag") ;
		sql.append(" ,wf.id as wfid, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo") ;
		sql.append(" from medcase smc") ;
		sql.append(" left join diagnosis diag on diag.medcase_id=smc.id") ;
		sql.append(" left join VocIllnesPrimary vip on vip.id=diag.illnesPrimary_id") ;
		sql.append(" left join vocidc10 mkb on mkb.id=diag.idc10_id") ;
		sql.append(" left join vocprioritydiagnosis vpd on vpd.id=diag.priority_id") ;
		sql.append(" left join VocServiceStream vss on vss.id=smc.serviceStream_id") ;
		sql.append(" left join VocWorkPlaceType vwpt on vwpt.id=smc.workPlaceType_id") ;
		sql.append(" left join VocReason vr on vr.id=smc.visitReason_id") ;
		sql.append(" left join VocVisitResult vvr on vvr.id=smc.visitResult_id") ;
		sql.append(" left join VocHospitalization vh on vh.id=smc.hospitalization_id") ;
		sql.append(" left join VocDispanseryRegistration vdr on vdr.id=smc.dispRegistration_id") ;
		sql.append(" left join WorkFunction wf on wf.id=smc.workFunctionExecute_id") ;
		sql.append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id") ;
		sql.append(" left join Worker w on w.id=wf.worker_id") ;
		sql.append(" left join Patient wp on wp.id=w.person_id") ;
		
		sql.append(" where smc.id='").append(aTicket).append("'") ;
		sql.append(" group by vss.id,vss.name,vwpt.id,vwpt.name,vr.id,vr.name,vvr.id,vvr.name,vh.id,vh.name,vdr.id,vdr.name,smc.emergency,wf.id, vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		StringBuilder res = new StringBuilder() ;
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(isNull(wqr.get1())).append("@").append(isNull(wqr.get2())).append("@").append(isNull(wqr.get3())).append("@").append(isNull(wqr.get4())).append("@") ;
			res.append(isNull(wqr.get5())).append("@").append(isNull(wqr.get6())).append("@").append(isNull(wqr.get7())).append("@").append(isNull(wqr.get8())).append("@") ;
			res.append(isNull(wqr.get9())).append("@").append(isNull(wqr.get10())).append("@").append(isNull(wqr.get11())).append("@").append(isNull(wqr.get12())).append("@") ;
			res.append(isNull(wqr.get13())).append("@").append(isNull(wqr.get14())).append("@") ;
			res.append(isNull(wqr.get15())).append("@").append(isNull(wqr.get16())) ;
		} 
		return res.toString() ;
	}	
	public Object isNull(Object aObj) {
		return aObj!=null?aObj:"" ;
	}
	public String getInfoByWorkFunctionAndDate(Long aMedcard, Long aWorkFunction, String aDate, HttpServletRequest aRequest) throws NamingException {
		
		StringBuilder res = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		//StringBuilder sql1 = new StringBuilder() ;
	//	sql1.append("select wf.workFunction_id as vwfid,wf.id as wfid from WorkFunction wf where wf.id="+aWorkFunction) ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		sql.append("    select") ; 
		sql.append(" count(distinct case when spo.dateStart is null then spo.id else null end) as cntPatient0") ;
		sql.append(" ,count(distinct case when spo.dateStart is not null then spo.id else null end) as cntPatient1") ;
		sql.append(" ,count(distinct spo.id) as cntPatient2") ;

		sql.append(" from MedCase spo") ;
		sql.append(" left join Patient pat on spo.patient_id=pat.id") ; 
		sql.append(" left join Medcard mc on mc.person_id=pat.id") ; 
		sql.append(" left join WorkFunction owf on spo.ownerFunction_id=owf.id") ; 
		sql.append(" left join VocWorkFunction ovwf on owf.workFunction_id=ovwf.id") ; 
		sql.append(" left join Worker ow on owf.worker_id=ow.id ") ;
		sql.append(" left join Patient owp on ow.person_id=owp.id ");
		sql.append(" where  mc.id='").append(aMedcard).append("' and ") ;
		sql.append(" (spo.dateFinish = to_date('").append(aDate).append("','dd.mm.yyyy') or spo.dateStart = to_date('").append(aDate).append("','dd.mm.yyyy'))");
		sql.append(" and spo.dtype='ShortMedCase'") ; 
		sql.append(" and spo.workFunctionExecute_id='").append(aWorkFunction).append("'") ;
		sql.append(" group by owf.id,ovwf.name,owp.lastname,owp.middlename,owp.firstname") ; 
		sql.append(" order by owp.lastname,owp.middlename,owp.firstname");
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		WebQueryResult obj = list.isEmpty()?null:list.iterator().next() ;
		return obj==null?"нет направленных пациентов":res.append("направлено: <b>").append(obj.get3()).append("</b> из них оформлено: <b>").append(obj.get2()).append("</b>").toString() ;
	}

	public String getOpenSpoByPatient(Long aWorkFunction, Long aPatient, HttpServletRequest aRequest) throws NamingException {
		
		StringBuilder res = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder sql1 = new StringBuilder() ;
		sql1.append("select wf.workFunction_id as vwfid,wf.id as wfid from WorkFunction wf where wf.id=").append(aWorkFunction);
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql1.toString(),1) ;
		WebQueryResult obj1 = list1.isEmpty()?null:list1.iterator().next() ;
		sql.append("select spo.id,coalesce(to_char(spo.dateStart,'dd.mm.yyyy'),'нет даты начала ')||coalesce('-'||to_char(spo.dateFinish,'dd.mm.yyyy'),'') ||' '||ovwf.name || ' '||owp.lastname|| ' '||owp.firstname|| ' '||owp.middlename as docfio" )
			.append(" from medCase spo")
			.append(" left join WorkFunction owf on owf.id=spo.ownerFunction_id")
			.append(" left join Worker ow on ow.id=owf.worker_id")
			.append(" left join Patient owp on owp.id=ow.person_id")
			.append(" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id")
			.append(" where spo.patient_id='").append(aPatient).append("'")
			.append(" and spo.DTYPE='PolyclinicMedCase' and spo.dateFinish is null and (spo.noActuality='0' or spo.noActuality is null) and ovwf.id='")
			.append(obj1!=null?obj1.get1():"").append("'")
			.append(" group by  spo.id,spo.dateStart,spo.dateFinish,ovwf.name,owp.lastname,owp.firstname,owp.middlename")
			.append(" order by spo.dateStart desc") ;
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		WebQueryResult obj = list.isEmpty()?null:list.iterator().next() ;
		return obj==null?"":res.append(obj.get1()).append("@").append(obj.get2()).toString() ;
	}
	
	public String getOpenSpoBySmo(Long aSmoId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select spo.id,to_char(spo.dateStart,'dd.mm.yyyy') as dateStart,to_char(spo.dateFinish,'dd.mm.yyyy') as dateFinish")
			.append(" , coalesce(spo.dateFinish,CURRENT_DATE)-spo.dateStart as cnt1")
			.append(" , count(distinct case when vis.noActuality='1' then null else vis.id end) as cnt2")
			.append(" ,to_char(max(vis.dateStart),'dd.mm.yyyy') as maxvisDateStart")
			.append(" ,ovwf.name || ' '||owp.lastname as docfio" )
			.append(" ,list(distinct vvr.name) as vvrname,list(distinct mkb.code||' '||vpd.name) as diag")
			.append(" from medCase spo")
			.append(" left join WorkFunction owf on owf.id=spo.ownerFunction_id")
			.append(" left join Worker ow on ow.id=owf.worker_id")
			.append(" left join Patient owp on owp.id=ow.person_id")
			.append(" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id")
			.append(" left join MedCase vis1 on vis1.patient_id=spo.patient_id")
			.append(" left join MedCase vis on vis.parent_id=spo.id and vis.DTYPE='Visit'")
			.append(" left join Diagnosis diag on diag.medcase_id=vis.id")
			.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
			.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id")
			.append(" left join VocVisitResult vvr on vvr.id=vis.visitResult_id")
			.append(" left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id")
			.append(" where spo.DTYPE='PolyclinicMedCase' and vis1.id='")
			.append(aSmoId).append("'")
			.append(" and (spo.dateFinish is null or spo.dateFinish between CURRENT_DATE-30 and CURRENT_DATE) and (vis1.dtype='PolyclinicMedCase' and spo.id!=vis1.id or spo.id!=vis1.parent_id)")
			.append(" group by  spo.id,spo.dateStart,spo.dateFinish,ovwf.name,owp.lastname")
			.append(" order by spo.dateStart") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		res.append("<table border='1px'>") ;
		if (list.isEmpty()) {
			res.append("<tr><td>");
			res.append("НЕТ СЛУЧАЕВ ДЛЯ ПЕРЕНОСА ДАННЫХ") ;
			res.append("</td></tr>");
		} else {
			res.append("<tr><th></th><th>").append("Дата начала").append("</th><th>").append("Дата окончания");
			res.append("</th><th>").append("Длит-ть").append("</th><th>").append("Кол-во").append("</th><th>").append("Дата посл. визита").append("</th><th>").append("Начавший СПО");
			res.append("</th><th>").append("Результат визита").append("</th><th>").append("Диагноз");
			//res.append("</th><th>").append("").append("</th><th>");
			//res.append("</th><th>");
			//res.append("</th><th>");
			res.append("</th></tr>");
			for (WebQueryResult wqr:list) {
				res.append("<tr><td>");
				res.append("<input type='radio' name='rbSpo' id='rbSpo' value='").append(wqr.get1()).append("'>") ;
				res.append("</td><td>");
				res.append(wqr.get2()!=null?wqr.get2():"") ;
				res.append("</td><td>");
				res.append(wqr.get3()!=null?wqr.get3():"") ;
				res.append("</td><td>");
				res.append(wqr.get4()!=null?wqr.get4():"") ;
				res.append("</td><td>");
				res.append(wqr.get5()!=null?wqr.get5():"") ;
				res.append("</td><td>");
				res.append(wqr.get6()!=null?wqr.get6():"") ;
				res.append("</td><td>");
				res.append(wqr.get7()!=null?wqr.get7():"") ;
				res.append("</td><td>");
				res.append(wqr.get8()!=null?wqr.get8():"") ;
				res.append("</td><td>");
				res.append(wqr.get9()!=null?wqr.get9():"") ;
				res.append("</td></tr>");
			}
		}
		res.append("</table>") ;
		return res.toString() ;
	}
	public String moveVisitOtherSpo(Long aVisit, Long aNewSpo, HttpServletRequest aRequest) throws NamingException {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		service.moveVisitInOtherSpo(aVisit,aNewSpo) ;
		HospitalMedCaseServiceJs.createAdminChangeMessageBySmo(aVisit, "MOVE_VISIT_OTHER_SPO", "Перемещен визит в другое СПО "+aNewSpo+": "+aNewSpo, aRequest) ;
		return aVisit+"#Визит перенесен" ;
	}
	public String unionSpos(Long aOldSpo, Long aNewSpo, HttpServletRequest aRequest) throws NamingException {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		service.unionSpos(aOldSpo,aNewSpo) ;
		HospitalMedCaseServiceJs.createAdminChangeMessageBySmo(aNewSpo, "UNION_SPOS", "Объединены СПО "+aOldSpo+": "+aNewSpo, aRequest) ;
		return aNewSpo+"#СПО объединены" ;
	}
	
	public String changeServiceStreamBySmo(Long aSmo, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.changeServiceStreamBySmo(aSmo, aServiceStream) ;
		HospitalMedCaseServiceJs.createAdminChangeMessageBySmo(aSmo, "CHANGE_SERVICE_STREAM", "Изменение потока обслуживания: "+aSmo, aRequest) ;
		return "Поток обслуживания изменен" ;
	}
	public String changeLpuBySmo(Long aSmo, Long aLpu, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update MedCase set orderlpu_id='"+aLpu+"' where id='"+aSmo+"' or parent_id='"+aSmo+"'") ;	    			
		HospitalMedCaseServiceJs.createAdminChangeMessageBySmo(aSmo, "CHANGE_ORDER_LPU", "Изменение ЛПУ: "+aSmo, aRequest) ;
		return "ЛПУ изменен" ;
	}

	
	public String getSessionData(HttpServletRequest aRequest) {
		StringBuilder res = new StringBuilder() ;
		res.append(getSession(aRequest, "TicketService.Ticket.date")).append("@") ;
		res.append(getSession(aRequest, "TicketService.Ticket.workFunction")).append("@") ;
		res.append(getSession(aRequest, "TicketService.Ticket.workFunctionName")).append("@") ;
		res.append(getSession(aRequest, "TicketService.Ticket.medServices")).append("@") ;
		res.append(getSession(aRequest, "TicketService.Ticket.emergency")).append("@") ;
		//res.append(aRequest.getAttribute("TicketService.Ticket.workFunction")).append("@") ;
		//res.append(aRequest.getAttribute("TicketService.Ticket.workFunctionName")).append("@") ;
		//res.append(aRequest.getAttribute("TicketService.Ticket.medServices")).append("@") ;
		return res.toString() ;
	}
	private String getSession(HttpServletRequest aRequest,String aAttribute) {
		return aRequest.getSession(true).getAttribute(aAttribute)!=null?(String)aRequest.getSession(true).getAttribute(aAttribute):"" ;
	}
	public String saveSession(String aDate,String aWorkFunction
   			, String aWorkFunctionName, String aMedServices, boolean aEmergency
   			
   			, HttpServletRequest aRequest) {
		aRequest.getSession(true).setAttribute("TicketService.Ticket.date", aDate) ;
		aRequest.getSession(true).setAttribute("TicketService.Ticket.workFunction", aWorkFunction) ;
		aRequest.getSession(true).setAttribute("TicketService.Ticket.workFunctionName", aWorkFunctionName) ;
		aRequest.getSession(true).setAttribute("TicketService.Ticket.medServices", aMedServices) ;
		aRequest.getSession(true).setAttribute("TicketService.Ticket.emergency", aEmergency?"1":"0") ;
		return "Сохранено" ;
	}
	public String isHoliday(String aDate, HttpServletRequest aRequest) throws ParseException {
		return ru.nuzmsh.util.format.DateFormat.isHoliday(aDate) ? "1" :"0";
	}
	public String canICreateTicket(Long aId, Long aPatientId, Long aSpec, String aDate, Long aMedcardId, HttpServletRequest aRequest) throws NamingException, ParseException {
		String ret = "";
		if (ru.nuzmsh.util.format.DateFormat.isHoliday(aDate)) {
			ret = "<br><ol><li>Визит приходится на выходной день, создание визита невозможно</li></ol><br>";
		} else {
			//String patientId = 
			String crossSPO = getCrossSPO(aDate, aPatientId, aSpec, aRequest) ;
			if (!crossSPO.equals("")){
				ret = crossSPO;
			} else {
				crossSPO = findDoubleBySpecAndDate(aId, aMedcardId, aSpec, aDate, aRequest);
				if (!crossSPO.equals("")) {
					ret = crossSPO; 
				}
			}
		}
		return ret;
	}
	public String findDoubleBySpecAndDate(Long aId, Long aMedcard, Long aSpec, String aDate, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	
    	if(StringUtil.isNullOrEmpty(aDate)) {
            return "" ;
        }
    	
        StringBuilder sql = new StringBuilder() ;
        sql.append("select t.id,p.lastname||' ' || p.firstname||' '||p.middlename|| ' '||to_char(p.birthday,'dd.mm.yyyy') as patName ,to_char(coalesce(t.dateStart,t.dateFinish),'dd.mm.yyyy') as dates,t.createTime,vwf.name|| ' ' || wp.lastname|| ' ' || wp.firstname|| ' ' || wp.middlename as doctorInfo")
        	.append(" from MedCase as t ")
        	.append(" left join medcard as m on m.id=t.medcard_id")
			.append(" left join patient as p on m.person_id=p.id")
			.append(" left join workfunction as wf on wf.id=t.workfunctionExecute_id")
			.append(" left join VocWorkFunction as vwf on vwf.id=wf.workFunction_id")
			.append(" left join worker as w on wf.worker_id=w.id")
			.append(" left join patient as wp on wp.id = w.person_id")
			.append(" where t.dtype='ShortMedCase' and t.medcard_id='").append(aMedcard)
			.append("' and t.workFunctionExecute_id='").append(aSpec)
			.append("' and t.dateStart=to_date('").append(aDate).append("','dd.mm.yyyy') and (t.istalk is null or t.istalk='0') and (t.emergency is null or t.emergency='0') ") ;
        
        if (aId!=null) sql.append(" and t.id!=").append(aId);
        
        
        Collection<WebQueryResult> doubles = service.executeNativeSql(sql.toString()) ;
        if (!doubles.isEmpty()) {
        	StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (WebQueryResult res:doubles) {
				ret.append("<li>")
				.append("<a href='entityParentView-poly_ticket.do?id=").append(res.get1()).append("'>пациент: ")
				.append(res.get2())
				.append(" дата приема ").append(res.get3()).append(" ").append(res.get4()).append(" ")
				.append(" специал. ").append(res.get5())
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
        }
		return ""; 
	}
	public String findProvReason(Long aReason, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService wservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder ret = new StringBuilder() ;
		Collection<WebQueryResult> l = wservice.executeNativeSql("select id,code from VocReason where id="+aReason+" and code='PROFYLACTIC'");
		if (l.isEmpty()) {
			l = wservice.executeNativeSql("select id,name from VocReason where code='PROFYLACTIC' order by id",1) ;
			if (!l.isEmpty()) {
				WebQueryResult obj = l.iterator().next() ;
				ret.append(obj.get1()).append("#").append(obj.get2()) ;
			}
		}
		return ret.toString();
		
	}
    public String findMkbByCode(String aCode,HttpServletRequest aRequest) throws NamingException {
    	StringBuilder ret = new StringBuilder() ;
    	if (aCode!=null && !aCode.equals("")) { 
    		Map<String, String> theMap = enrusCreate();
			aCode=aCode.toUpperCase() ;
			char c1 = aCode.charAt(0) ;
			aCode = aCode.replaceFirst("Ю", ".") ;
			aCode = aCode.replaceFirst(",", ".") ;
			aCode = getLat(c1,theMap) + (aCode.length()>1 ? aCode.substring(1) : "") ;
			IWebQueryService wservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> l = wservice.executeNativeSql("select id,code||' '||name from VocIdc10 where code='"+aCode+"'");
			if (!l.isEmpty()) {
				WebQueryResult obj = l.iterator().next() ;
				ret.append(obj.get1()).append("#").append(obj.get2()) ;
			}
    	}
    	return ret.toString() ;
    }
    public Map<String,String> enrusCreate() {
    	Map <String,String> map = new HashMap<>();
    	map.put("Й", "Q" ) ;
    	map.put("Ц", "W" ) ;
    	map.put("У","E"  ) ;
    	map.put( "К", "R" ) ;
    	map.put("Е", "T"  ) ;
    	map.put( "Ф","A" ) ;
    	map.put( "Ы", "S") ;
    	map.put("В", "D"  ) ;
    	map.put("А","F" ) ;
    	map.put("П","G"  ) ;
    	map.put("Я","Z"  ) ;
    	map.put("Ч","X"  ) ;
    	map.put("С","C"  ) ;
    	map.put( "М", "V" ) ;
    	map.put("И", "B" ) ;
    	map.put("Н", "Y"  ) ;
    	map.put("Г", "U"  ) ;
    	map.put("Ш", "I"  ) ;
    	map.put("Щ", "O"  ) ;
    	map.put("З","P" ) ;
    	map.put( "Р","H" ) ;
    	map.put("О", "J"  ) ;
    	map.put("Л","K"  ) ;
    	map.put("Д", "L" ) ;
    	map.put("Т","N" ) ;
    	map.put( "Ь","M" ) ;
    	map.put( "Ю","." ) ;
    	return map ;
    }
    private String getLat(char aChar,Map <String,String> aMapSymb) {
    	String ret =aMapSymb.get(String.valueOf(aChar)) ;
    	return ret !=null ? ret: String.valueOf(aChar) ;
    }

	public String checkHospitalByMedcard(String aDateStart, Long aMedcard, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list=service.executeNativeSql("select person_id,id from medcard where id="+aMedcard) ;
		return list.isEmpty()?null:checkHospital(aDateStart, ConvertSql.parseLong(list.iterator().next().get1()), aServiceStream, aRequest) ;
	}
	
	public String checkPolicyByMedcard(Long aMedcardId,String aDatePlan, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		/*System.out.println(aMedcardId) ;
		System.out.println(aDatePlan) ;
		System.out.println(aServiceStream) ;*/
		aDatePlan = aDatePlan.trim() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select id,code from VocServiceStream where id='"+aServiceStream+"' and code='OBLIGATORYINSURANCE'" ;
		Collection<WebQueryResult> list1 = service.executeNativeSql(sql,1) ;
		if (!list1.isEmpty()) {
			sql = "SELECT mp.id,mp.dtype " 
	                +"FROM MedPolicy mp left join Medcard mc on mp.patient_id=mc.person_id left join Patient pat on pat.id=mc.person_id where mc.id='"+aMedcardId+"' "
	                +"AND ((mp.actualDateFrom<=to_date('"+aDatePlan+"','dd.mm.yyyy') and (mp.actualDateTo is null or mp.actualDateTo>=to_date('"+aDatePlan+"','dd.mm.yyyy')) "
	                +"and mp.DTYPE like 'MedPolicyOmc%') or pat.deathdate<= to_date('"+aDatePlan+"','dd.mm.yyyy'))" ;
			//System.out.println(sql) ;
			Collection<WebQueryResult> list = service.executeNativeSql(sql,1) ;
			if (list.isEmpty()) return "1" ;
			if (list.size()>1) return "2" ;
		}
		return "0" ;
	}
	
	public String checkHospital(String aDateStart, Long aPatient, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		
		String sql = "select id,name from vocServiceStream where id='"+aServiceStream+"' and code='OBLIGATORYINSURANCE' "; 
		Collection<WebQueryResult> list=service.executeNativeSql(sql,1) ;
		if (!list.isEmpty()) {
		sql = "select to_char(dateStart,'dd.mm.yyyy') as date1,to_char(dateFinish,'dd.mm.yyyy') as date2 from medcase where patient_id='"+aPatient+"' and dtype='HospitalMedCase' and dateStart<to_date('"+aDateStart+"','dd.mm.yyyy') and (dateFinish is null or dateFinish>to_date('"+aDateStart+"','dd.mm.yyyy')) and deniedHospitalizating_id is null order by datestart desc" ;
		list.clear() ;
		list=service.executeNativeSql(sql,1) ;
			if (!list.isEmpty()) {
				WebQueryResult wqr = list.iterator().next() ;
				return new StringBuilder().append(wqr.get1()).append("-")
						.append(wqr.get2()!=null?wqr.get2():"по наст. время").toString() ;
			}
		}
		return null ;
		
	}
	public String getMedServiceBySpec(Long aSpec, String aDate, HttpServletRequest aRequest) throws ParseException, NamingException {
		ITicketService service = Injection.find(aRequest).getService(ITicketService.class) ;
		if (aDate!=null && !aDate.equals("")) return service.getMedServiceBySpec(aSpec, aDate);
		return "" ;
	}
	public Long getUserByWorkFunction(HttpServletRequest aRequest) throws NamingException {
		return getWorkFunction(aRequest);
	}
	public Long getWorkFunction(HttpServletRequest aRequest) throws NamingException {
		IWorkerService servWorker = Injection.find(aRequest).getService(IWorkerService.class) ;
		return servWorker.getWorkFunction() ;
	}

	public String isEditCheck(Long aIdTicket, Long aDoctor, HttpServletRequest aRequest) throws Exception {
    	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
    	IWebQueryService serviceWQ = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String userCur=TemplateProtocolJs.getUsername(aRequest) ;
    	StringBuilder sql =new StringBuilder().append("select wf.id from WorkFunction wf " +
    			"left join Worker w on w.id=wf.worker_id " +
    			"left join SecUser su on su.id=wf.secuser_id where su.login='").append(userCur).append("'") ;
    	Collection<WebQueryResult> workFunctions = serviceWQ.executeNativeSql(sql.toString()) ;
		boolean isEditUser = !RolesHelper.checkRoles("/Policy/Poly/Ticket/IsDoctorEdit",aRequest) ;
		if (!isEditUser) {
		for (WebQueryResult work:workFunctions) {
    		isEditUser = checkUser(aIdTicket,aDoctor, ConvertSql.parseLong(work.get1()), aRequest) ;
    		if (isEditUser) break ;
    	}
		}
		if (isEditUser) {
			boolean isClosedPeriod = checkPermission(service, "ShortMedCase", "dateClosePeriod", aIdTicket, aRequest) ;
			if (isClosedPeriod) {
				boolean isEditClose = checkPermission(service, "ShortMedCase", "editDataClosePeriod", aIdTicket, aRequest); 
			//	System.out.println("isEditClose="+isEditClose) ;
				return  isEditClose?"1":"0#Период закрыт на редактирование";
			} else {
				return "1" ;
			}
		}
    	return "0#У Вас стоит ограничение на редактрование данного талона!" ;
	}
    	
	public boolean checkCreateDoubleBySpecAndDate(HttpServletRequest aRequest) throws Exception {
		return RolesHelper.checkRoles("/Policy/Poly/Ticket/IsNotCreateDoubleTicket",aRequest) ;
	}
    private boolean checkUser(Long aIdTicket,Long aDoctorExecute, Long aDoctorCur
    		, HttpServletRequest aRequest) throws Exception {
    	
		if (aDoctorExecute==null||aDoctorExecute.intValue()==0
				||aDoctorCur.intValue()==aDoctorExecute.intValue()) {
        	return true ;
        } else {
        	IScriptService service = (IScriptService)Injection.find(aRequest).getService("ScriptService") ;
        	return checkPermission(service, "Ticket", "editOtherUser", aIdTicket, aRequest) ;
        }
    	
    }
    public static boolean checkPermission(IScriptService aService, String aObject, String aPermission,  Long aIdTicket, HttpServletRequest aRequest) {
    	HashMap<String, Comparable> param = new HashMap<>() ;
    	Object res ;
    	param.put("obj",aObject) ;
		param.put("permission" ,aPermission) ;
		param.put("id", aIdTicket) ;
		res = aService.invoke("WorkerService", "checkPermission", new Object[]{param});
		long res1 = TemplateProtocolJs.parseLong(res);
		return res1>0;
    }

	/**
	 * Получить примечания к услугам в направлении/визите (с пробелом на конце)
	 * @param aMedCaseId MedCase.id
	 * @return String json Услуги с комментариями
	 */
	public String getServiceComments(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder();
		sql.append("select mc.id,ms.code||' '||ms.name||' ',mc.serviceComment ")
				.append(" from MedCase mc  ")
				.append(" left join MedService ms on mc.medService_id=ms.id ")
				.append(" where mc.dtype='ServiceMedCase' and mc.parent_id=' ")
				.append(aMedCaseId)
				.append("'");
		JSONArray res = new JSONArray() ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
		for (WebQueryResult w :list) {
			JSONObject o = new JSONObject() ;
			o.put("id", w.get1())
					.put("name", w.get2())
					.put("cmnt", w.get3());
			res.put(o);
		}
		return res.toString();
	}
}
