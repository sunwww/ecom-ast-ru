package ru.ecom.mis.web.dwr.medcase;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.webservice.FondWebService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.validators.SnilsStringValidator;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class PatientServiceJs {

	private static final Logger LOG = Logger.getLogger(PatientServiceJs.class);

	public void polisIsChecked(String medcaseId,HttpServletRequest request) throws NamingException {
		IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
		service.executeUpdateNativeSql("update medcase_medpolicy " +
				"set datesync = current_date where medcase_id="+medcaseId);
	}

	public String getPaid(String aPatientId,HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		sql.append("select count(cao.cost) from patient p" + " left join contractperson cp on cp.patient_id= p. id" + " left join medcontract mc on mc.customer_id= cp.id" + " left join contractaccount ca on ca.contract_id= mc.id" + " left join contractaccountoperation cao on cao.account_id= ca.id" + " where p.id =").append(aPatientId);
		Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
		String str = "";
		for (WebQueryResult wqr : res) {
			str = wqr.get1().toString();
		}
		return str;
	}

	public String getPatientFromContractPerson(String aContractPerson,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String sql = "select patient_id from contractperson  where id = "+aContractPerson;
		Collection<WebQueryResult> res = service.executeNativeSql(sql);
		return res.iterator().next().get1().toString();
	}

	public String savePrivilege(String aPatientId, String aNumberdoc,String aSerialdoc,
								String aBegindate,String aEnddate, String aCategoryId,
								HttpServletRequest aRequest) throws NamingException {

		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder headSQL = new StringBuilder();
		StringBuilder bodySQL = new StringBuilder();

		headSQL.append("insert into privilege(person_id,category_id,begindate");
		bodySQL.append("VALUES(").append(aPatientId).append(",").append(aCategoryId).append(",'").append(aBegindate).append("'");

		if(aNumberdoc!=null && !aNumberdoc.equals("")){
			headSQL.append(",numberdoc");
			bodySQL.append(",'").append(aNumberdoc).append("'");
		}

		if(aSerialdoc!=null && !aSerialdoc.equals("")){
			headSQL.append(",serialdoc");
			bodySQL.append(",'").append(aSerialdoc).append("'");
		}

		if(aEnddate!=null && !aEnddate.equals("")){
			headSQL.append(",enddate");
			bodySQL.append(",'").append(aEnddate).append("'");
		}

		headSQL.append(")");
		bodySQL.append(")");
		service.executeUpdateNativeSql(headSQL.toString()+bodySQL.toString());
		return "1";
	}

	public String getPatients(String aLastname, String aFirstname, String aMiddlename
			, String aYear, HttpServletRequest aRequest) throws NamingException {
		if (aLastname==null || (aLastname +(aFirstname!=null ? aFirstname:"")
				+(aMiddlename!=null ? aMiddlename : "")).length()<4)  return "введите данные для поиска" ;
		StringBuilder sql = new StringBuilder() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		sql.append("select p.id,p.patientSync,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'dd.mm.yyyy') from patient p left join medpolicy mp on mp.patient_id=p.id where p.lastname like '").append(aLastname.toUpperCase()).append("%' ") ;
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ") ;
		}
		if (aYear!=null && !aYear.equals("")) {
			sql.append(" and to_char(p.birthday,'yyyy')='").append(aYear).append("' ") ;
		}
		
		sql.append(" group by p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday") ;
		sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),20);
		StringBuilder res = new StringBuilder() ;
		res.append("<ul id='listPatients'>") ;
		boolean isFirst = true ;
		for (WebQueryResult wqr:list) {
			
			StringBuilder s = new StringBuilder().append(wqr.get1()).append("#").append(wqr.get3()).append(" ").append(wqr.get4())
					.append(" ").append(wqr.get5()).append(" г.р. ").append(wqr.get6()) ;
			res.append("<li ondblclick=\"this.childNodes[1].checked='checked';checkPatient('").append(s).append("');\" onclick=\"this.childNodes[1].checked='checked';checkPatient('").append(s).append("')\">") ;
			res.append(" <input class='radio' type='radio' name='rdPatient' id='rdPatient' ") ;
			if (isFirst) {
				res.append(" checked='true' ") ;
				isFirst=false ;
			}
			res.append(" value='").append(s).append("'>") ;
			res.append(wqr.get2()) ;
			res.append(" ").append(wqr.get3()) ;
			res.append(" ").append(wqr.get4()) ;
			res.append(" ").append(wqr.get5()) ;
			res.append(" ").append(wqr.get6()) ;
			if (wqr.get1()!=null) {
				res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
					.append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
					.append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>") ;
				
			}
			res.append("</li>") ;
		}
		res.append("</ul>") ;
		
		return res.toString() ;
	}
	public String getFlowDocumentByPatient(Long aPatientId, Long aMedCase, Long aMedcard, String aTypeis, Long aViewType, HttpServletRequest aRequest) throws NamingException, JspException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder str = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		
		if (aViewType.equals(1L)) str.append(" Список документов на отправку по пациенту:") ;
		else if (aViewType.equals(2L)) str.append(" Список отправленных документов по пациенту:") ;
		else if (aViewType.equals(3L)) str.append(" Список документов по пациенту:") ;
		sql.append(" select fd.id as fdid");
		sql.append(",pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio");
		sql.append(",to_char(fd.createdate,'dd.mm.yyyy') as createdate ,coalesce('ИБ№'||ss.code,'МК№'||mc.number,'') as infodoc");
		sql.append(" ,vfdt.name as vfdtname,vfdp1.name as vfdp1name,vfdp2.name as vfdp2name,fd.createusername") ;
		sql.append(" from FlowDocument fd ");
		sql.append(" left join medcase sls on sls.id=fd.medcase ");
		sql.append(" left join statisticstub ss on ss.id=sls.statisticstub_id ");
		sql.append(" left join medcard mc on mc.id=fd.medcard ");
		sql.append(" left join patient pat on pat.id=fd.patient ");
		sql.append(" left join VocFlowDocumentType vfdt on vfdt.id=fd.type_id ");
		sql.append(" left join VocFlowDocmentPlace vfdp1 on vfdp1.id=fd.placeFrom_id ");
		sql.append(" left join VocFlowDocmentPlace vfdp2 on vfdp2.id=fd.placeTo_id ");
		sql.append("where fd.patient='").append(aPatientId).append("'") ;
		if (aTypeis!=null && !aTypeis.equals("")) sql.append(" and fd.type_id in (").append(aTypeis).append(")") ;
		boolean isDelete = false ;
		if (aViewType.equals(1L)) {
			sql.append(" and fd.postedDate is null") ;
			//System.out.print("----"+isDelete) ;
			if (RolesHelper.checkRoles("/Policy/Mis/Document/Flow/Delete", aRequest)) {
				isDelete = true ;
			}
		} else if (aViewType.equals(2L)) sql.append(" and fd.postedDate is not null") ;
		else if (aViewType.equals(3L)) sql.append(" ") ;
		//System.out.print(isDelete) ;
		if (aPatientId==null || aPatientId.equals(0L)) return "НЕ УКАЗАН ПАЦИЕНТ!!!" ;
		sql.append(" order by vfdt.name") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		if (list.isEmpty()) {
			str.append("НЕТ ДАННЫХ!!!");
		} 
		int i=0 ;
		for (WebQueryResult wqr:list) {
			i++ ;
			if (i==1) {
				str.append(wqr.get2()) ;
				str.append("<table class='tabview sel tableArrow' border='1'><tr>") ;
				if (isDelete) {
					str.append("<th>&nbsp;</th>") ;
				}
				str.append("<th>#</th>") ;
				str.append("<th>Дата</th>") ;
				str.append("<th>ИБ/МК</th>") ;
				str.append("<th>Вид</th>") ;
				str.append("<th>Отправлено из</th>") ;
				str.append("<th>Отправлено в</th>") ;
				str.append("<th>Пользователь</th>") ;
				str.append("</tr>");
			}
			str.append("<tr>") ;
			if (isDelete) str.append("<td><button onclick='deleteFlowDoc(\"").append(wqr.get1()).append("\")'>Удалить</button></td>");
			str.append("<td>").append(i).append("</td>") ;
			str.append("<td>").append(wqr.get3()).append("</td>") ;
			str.append("<td>").append(wqr.get4()).append("</td>") ;
			str.append("<td>").append(wqr.get5()).append("</td>") ;
			str.append("<td>").append(wqr.get6()).append("</td>") ;
			str.append("<td>").append(wqr.get7()).append("</td>") ;
			str.append("<td>").append(wqr.get8()).append("</td>") ;
			str.append("</tr>");
		}
		if (!list.isEmpty()) {
			str.append("</table>");
		}
		return str.toString() ;
	}
	public String deleteDataByFlowDoc(Long aId, Long aStatusUpdate,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		if (aStatusUpdate.intValue()==1||aStatusUpdate.intValue()==2) {
			String type="receipt";
			if (aStatusUpdate.intValue()==1) type="posted";
			sql.append("update flowdocument set ").append(type).append("Date=null");
			sql.append(",").append(type).append("Time=null");
			sql.append(",").append(type).append("Username=null");
		} else {
			sql.append("delete from flowdocument") ;
		}
		sql.append(" where id =").append(aId) ;
		service.executeUpdateNativeSql(sql.toString()) ;
		return "обновлено" ;
	}
	public String updatePostedDateByFlowDoc(String aIds, String aDate, String aTime, HttpServletRequest aRequest) throws NamingException {
		return updateFlowDocumentByPatient(aIds,aDate,aTime, 1L,aRequest) ;
	}
	public String updateReceitDateByFlowDoc(String aIds, String aDate, String aTime, HttpServletRequest aRequest) throws NamingException {
		return updateFlowDocumentByPatient(aIds,aDate,aTime, 2L,aRequest) ;
	}
	public String updateFlowDocumentByPatient(String aIds, String aDate, String aTime, Long aStatusUpdate, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sql = new StringBuilder() ;
		String type="receipt";
		if (aStatusUpdate.intValue()==1) type="posted";
		sql.append("update flowdocument set ").append(type).append("Date=to_date('")
			.append(aDate).append("','dd.mm.yyyy')");
		sql.append(",").append(type).append("Time='").append(aTime).append("'");
		sql.append(",").append(type).append("Username='").append(username).append("'");
		sql.append("where id in (").append(aIds).append(") and ").append(type).append("Date is null") ;
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String createFlowDocumentByPatient(Long aPlaceFrom, Long aPlaceTo
			, Long aPatientId, Long aMedCase, Long aMedcard, String aTypeis
			, HttpServletRequest aRequest) throws NamingException, JspException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		StringBuilder sqlCheck ;
		StringBuilder sql ;
		StringBuilder sql1 ;
		String[] typeis = aTypeis.split(",") ;
		boolean isCreate=true ;
		for (String type:typeis) {
			sql = new StringBuilder() ;
			sqlCheck = new StringBuilder() ;
			sql1 = new StringBuilder() ;
			if (aPlaceTo!=null && !aPlaceTo.equals(0L)) {
				sql1.append("select case when v.isTracking='1' then '1' else null end as istracking,v.type,case when v.homePlaceCode=(select vfdt.code from VocFlowDocmentPlace vfdt where vfdt.id='").append(aPlaceTo).append("') then null else '1' end as home from VocFlowDocumentType v where v.id=").append(type);
				List<Object[]> l = service.executeNativeSqlGetObj(sql1.toString()) ;
				String t = "" ;
				boolean istracksql=false;
				boolean istrack=false;
				if (!l.isEmpty()) {
					t = ""+l.get(0)[1] ;
					if (l.get(0)[0]!=null) {
						sql1=new StringBuilder() ;
						sql1.append("update flowdocument set istracking=null where patient='").append(aPatientId).append("' and type_id='").append(type).append("'") ;
						if (t.equals("HOSP")) { if (aMedCase!=null && !aMedCase.equals(0L)) {istracksql=true ;sql1.append(" and medcase='").append(aMedCase).append("'");} }
						else if (t.equals("POLYC")) {if (aMedcard!=null && !aMedcard.equals(0L)) {istracksql=true ;sql1.append(" and medcard='").append(aMedcard).append("'");} }
						if (l.get(0)[2]!=null&&istracksql) {istrack=true;}
					}
				}
				sqlCheck.append("select id from flowdocument where patient='").append(aPatientId).append("' and type_id='").append(type).append("'") ;
				sql.append("insert into flowdocument (placeFrom_id,placeTo_id,patient,isTracking");
				if (t.equals("HOSP")) sql.append(",medCase");
				if (t.equals("POLYC")) sql.append(",medcard");
				sql.append(",type_id,createdate,createtime,createusername) values (");
				isCreate=true ;
				if (aPlaceFrom!=null && !aPlaceFrom.equals(0L)) {
					sql.append("'").append(aPlaceFrom).append("'");
					sqlCheck.append(" and placeFrom_id='").append(aPlaceFrom).append("'");
				} else isCreate=false ;
				
				sql.append(",'").append(aPlaceTo).append("'");
				sqlCheck.append(" and placeTo_id='").append(aPlaceTo).append("'");
				sql.append(",");
				if (aPatientId!=null && !aPatientId.equals(0L)) sql.append("'").append(aPatientId).append("'"); else isCreate=false ;
				sql.append(",");
				if (istrack) sql.append("'1'"); else sql.append("null") ;
				if (t.equals("HOSP")) {
					if (aMedCase!=null && !aMedCase.equals(0L)) {
					sql.append(",'").append(aMedCase).append("'");
					sqlCheck.append(" and medCase='").append(aMedCase).append("'");
					} else sql.append(",null") ;
				}
				if (t.equals("POLYC")) {
					if (aMedcard!=null && !aMedcard.equals(0L)) {
					sql.append(",'").append(aMedcard).append("'");
					sqlCheck.append(" and medcard='").append(aMedcard).append("'");
					} else sql.append(",null") ;
				}
				
				sql.append(",");
				if (type!=null && !type.equals("0") && !type.equals("")) sql.append("'").append(type).append("'"); else isCreate=false ;
				sql.append(",current_date,current_time,'").append(username).append("')") ;

				if (isCreate) {
					if (!service.executeNativeSql(sqlCheck.append(" and receiptDate is null").toString()).isEmpty()) isCreate=false ;
					if (isCreate) {
						if (istracksql) {
							service.executeUpdateNativeSql(sql1.toString()) ;
						}
						service.executeUpdateNativeSql(sql.toString()) ;
					}
				}
			} else {isCreate=false;}
		}
		if (isCreate) {
			return getFlowDocumentByPatient(aPatientId, aMedCase, aMedcard, aTypeis, 1L, aRequest) ;
		} else {
			return "<b>НЕ ЗАПОЛНЕНЫ ВСЕ ОБЯЗАТЕЛЬНЫЕ ПОЛЯ!!!</b><br>"+getFlowDocumentByPatient(aPatientId, aMedCase, aMedcard, aTypeis, 1L, aRequest) ;
		}
		 
	}
	public String getIsPatientInList(Long aPatientId, HttpServletRequest aRequest) throws NamingException {
		 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		 StringBuilder sb = new StringBuilder();
		 String sql ="select pl.name as plName, pl.colorName as colorName, coalesce(plr.message,pl.message) as plrMessage" +
		 		" from patientListRecord plr" +
		 		" left join patientList pl on pl.id=plr.patientList" +
		 		" where plr.patient="+aPatientId;
		 Collection <WebQueryResult> res = service.executeNativeSql(sql);
		 for (WebQueryResult wqr: res) {
			if (sb.length()>0) {sb.append(":");}
			sb.append("<div style='").append(wqr.get2().toString()).append("'>");
			sb.append(wqr.get3().toString());
			sb.append("</div>");
			//sb.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3());
		 }
		 return sb.toString();
	}

	public String showPatientCheckByFondHistory(String aPatientId, String aType, HttpServletRequest aRequest) throws NamingException {
		 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		 StringBuilder ret = new StringBuilder();
		 try {
			 Object[] re = service.executeNativeSqlGetObj("select lastname, firstname, middlename, birthday, commonnumber from patient where id = "+aPatientId).get(0);
			 String whereSql ;
			 if (aType!=null&&aType.equals("1")) {
				 whereSql = "pf.lastname = '"+re[0]+"' and pf.firstname = '"+re[1]+"' and middlename = '"+re[2]+"' and pf.birthday = '"+re[3]+"'";
			 } else {
				 whereSql = "pf.commonnumber='"+re[4]+"'";
			 }
			  
			 String sql = "select pf.lastname, pf.firstname, pf.middlename, to_char(pf.birthday,'dd.MM.yyyy') as f3_birthday" +
				 		" ,to_char(pf.checkdate,'dd.MM.yyyy') as f4_check_date, coalesce(pf.lpuattached,'') as f5, coalesce(pf.attachedtype,'') as f6" +
				 		" ,coalesce(to_char(pf.attacheddate,'dd.MM.yyyy'),'') as f7_att_date" +
				 		", coalesce(department,'') as f8, coalesce(doctorsnils,'') as f9" +
				 		" from patientfond pf" +
				 		" where " +whereSql +
				 		" order by pf.checkdate desc limit 50";
			 
			 Collection <WebQueryResult> res = service.executeNativeSql(sql);
			 if (!res.isEmpty()) {
				 ret.append("<table border='1'>");
				 ret.append("<tr><td>Фамилия</td><td>Имя</td><td>Отчество</td><td>ДР</td><td>Дата проверки</td><td>ЛПУ прикрепления</td><td>Тип прикрепления</td><td>Дата прикрепления</td><td>Подразделение</td><td>СНИЛС врача</td></tr>");
				 for (WebQueryResult wqr: res) {
					 ret.append("<tr>")
					 .append("<td>").append(wqr.get1()).append("</td>")
					 .append("<td>").append(wqr.get2()).append("</td>")
					 .append("<td>").append(wqr.get3()).append("</td>")
					 .append("<td>").append(wqr.get4()).append("</td>")
					 .append("<td>").append(wqr.get5()).append("</td>")
					 .append("<td>").append(wqr.get6()).append("</td>")
					 .append("<td>").append(wqr.get7()).append("</td>")
					 .append("<td>").append(wqr.get8()).append("</td>")
					 .append("<td>").append(wqr.get9()).append("</td>")
					 .append("<td>").append(wqr.get10()).append("</td>")
					 .append("</tr>");
				}
				 ret.append("</table>");
			 }
			 return ret.toString() ;
		 } catch (Exception e) {
		 	LOG.error("ERR=",e);
			 return ""+e;
		 }
		 
	}
	
	public String getUserDocumentList(String aGroupName, HttpServletRequest aRequest) throws NamingException {
		StringBuilder ret = new StringBuilder();
		String sql = "select ud.id, ud.name, ud.filename from  userDocument ud" +
				" left join VocUserDocumentGroup vudg on ud.groupType_id = vudg.id where upper(vudg.code) = upper('"+aGroupName+"')";
	 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	 Collection <WebQueryResult> res = service.executeNativeSql(sql);
	 if (!res.isEmpty()) {
		 for (WebQueryResult r: res) {
			 ret.append(r.get1().toString()).append(":").append(r.get2().toString()).append(":").append(r.get3().toString()).append("#");
		 }
	 }
		return ret.length()>0?ret.substring(0, ret.length()-1):"";
	}
	public void changeMedPolicyType(Long aPolicyId, Long aNewPolicyTypeId, HttpServletRequest aRequest) throws NamingException {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
		service.changeMedPolicyType(aPolicyId, aNewPolicyTypeId);
	}
	public boolean updateDataByFondAutomatic(String aPatientFondId, String aCheckId
			, boolean isUpdatePatient, boolean isUpdateDocument, boolean isUpdatePolicy, boolean isUpdateAttachment
			, HttpServletRequest aRequest) throws NamingException {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
		return service.updateDataByFondAutomatic(Long.valueOf(aPatientFondId)
				, Long.valueOf(aCheckId), isUpdatePatient, isUpdateDocument
				, isUpdatePolicy, isUpdateAttachment);
		
	}
	public String checkAllPatients(String updPatient, String updDocument, String updPolicy,String updAttachment, String aType, String aPatientList,  HttpServletRequest aRequest) throws Exception {
		return FondWebService.checkAllPatientsByFond(updPatient, updDocument, updPolicy, updAttachment, aType, aPatientList, aRequest).toString();
	}
	public String checkDispAttached (String aDispTypeId, String aPatientId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String isDispAttachment = service.executeNativeSql("select case when attachmentpopulation ='1' then '1' else '0' end " +
				"from vocextdisp where id='"+aDispTypeId+"'", 1).iterator().next().get1().toString();
		JSONObject isPatAttached = new JSONObject(isPatientAttached(aPatientId, aRequest));

		return  isDispAttachment.equals("1") && isPatAttached.getString("statusCode").equals("0") ? "0" : "1";
	}
	public String checkPatientAttachedOrDead(String aPatientId, String isDeath, String isAttached, HttpServletRequest aRequest) throws NamingException {
		boolean checkDeath = "1".equals(isDeath);
		boolean checkAttachment = "1".equals(isAttached);
		JSONObject ret = new JSONObject();
		String statusCode="0", statusName = "-";
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list = service.executeNativeSql("select coalesce(pf.lpuattached,'') as lpuAttached" +
					", to_char(pf.checkdate,'dd.mm.yyyy') as checkDate" +
					" ,case when pf.deathdate is not null then to_char(pf.deathdate,'dd.mm.yyyy') else '' end as deathDate" +
					" ,coalesce(pf.doctorsnils,'') as doctorId" +
					" from patient p " +
					" left join patientfond pf on (pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename " +
					" and pf.birthday=p.birthday) where p.id='"+aPatientId+"' and pf.id is not null order by pf.checkdate desc", 1);
			Collection<WebQueryResult> defLpu =service.executeNativeSql("select sc.keyvalue, case when sc.description!='' then sc.description else '№ '|| sc.keyvalue end from softconfig sc where sc.key='DEFAULT_LPU_OMCCODE'");
				String defaultLpu = null, defaultLpuName = null;
			if (checkAttachment) {
				if (defLpu.isEmpty()) {
					ret.put("status","0");
					ret.put("statusName","Необходимо указать ЛПУ по умолчанию в настройках (DEFAULT_LPU_OMCCODE)");
					return ret.toString();
				} else {
					WebQueryResult wqr =defLpu.iterator().next();
					defaultLpu = wqr.get1()!=null?wqr.get1().toString():"";
					defaultLpuName = wqr.get2()!=null?wqr.get2().toString():"";
				}
			}
			if (!list.isEmpty()) {
				WebQueryResult wqr = list.iterator().next();
				String lastAttachment = wqr.get1().toString();
				String checkDate = wqr.get2()!=null?wqr.get2().toString():"";
				String deathDate = wqr.get3()!=null?wqr.get3().toString():"";
				String doctorSnils = wqr.get4()!=null?wqr.get4().toString():"";
					if (checkAttachment) {
						if (lastAttachment.equals(defaultLpu)) {
							if (StringUtil.isNullOrEmpty(doctorSnils)) {
								statusName = " Внимание! ФОНД не имеет информации о прикреплении пациента к участку!";
							}
							statusCode="1";
							statusName= "По данным ФОМС на "+checkDate+" пациент прикреплен к ЛПУ "+defaultLpuName+"."+statusName;
						} else {
							statusCode="0";
							statusName =  "По данным ФОМС на "+checkDate+" пациент не прикреплен к ЛПУ "+defaultLpuName+".";
						}
					}
					if (checkDeath&&deathDate!=null && deathDate.length()==10) {
						statusCode="2";
						statusName= "По данным ФОМС на "+checkDate+" пациент умер "+deathDate;
					}
			} else {
				if (checkAttachment) {
					statusCode="0";
					statusName = "Необходимо выполнить проверку по базе ФОМС";
				}
			}
			ret.put("statusCode",statusCode);
			ret.put("statusName",statusName);
			return ret.toString();
		}
	
	public String isPatientAttached (String aPatientId, HttpServletRequest aRequest) throws NamingException {
		return checkPatientAttachedOrDead(aPatientId,"0","1",aRequest);
	}
	
	public String getSexByOmccode(String aOmccode,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Collection<WebQueryResult> list = service.executeNativeSql("select id,name from vocSex where omcCode='"+aOmccode+"'",1) ;
        if (list.isEmpty()) {
            return "" ;
        } else {
            WebQueryResult wqr = list.iterator().next() ;
            return wqr.get1()+"#"+wqr.get2() ;
        }
    }

	public String editColorType(Long aPatient,String aColorTypeCurrent, HttpServletRequest aRequest) throws NamingException  {
		String colorType="1" ;
		if (aColorTypeCurrent!=null && aColorTypeCurrent.trim().equals("1")) {
			colorType="0" ;
		}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update Patient set colorType='"+colorType+"' where id='"+aPatient+"'") ;
		return "сохранено" ;
	}
	public String getAgeForDisp(Long aPatient, String aFinishDate, HttpServletRequest aRequest) {
		try {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list = service.executeNativeSql("select id,to_char(birthday,'dd.mm.yyyy') from patient where id='"+aPatient+"'",1) ;
			WebQueryResult wqr = list.iterator().next() ;
			
			String birthDayS = wqr.get2()!=null ? ""+wqr.get2() : "" ;
			//String birthDayYear = birthDayS.substring(5) ;
			java.sql.Date birthday = DateFormat.parseSqlDate(birthDayS) ;
			java.sql.Date finishDate = DateFormat.parseSqlDate(aFinishDate) ;
			Calendar calB = Calendar.getInstance() ;
			calB.setTime(birthday) ;
			Calendar calF = Calendar.getInstance() ;
			calF.setTime(finishDate) ;
			boolean reMonth = (calF.get(Calendar.MONTH) == calB.get(Calendar.MONTH)) ;
			String age=AgeUtil.getAgeCache(finishDate, birthday, 1);
		//	System.out.println("age:"+age) ;
			int sb1 = age.indexOf('.') ;
			int sb2 = age.indexOf('.',sb1+1) ;
			
			//int yearDif = Integer.valueOf(age.substring(0,sb1)).intValue() ;
			int yearDif = Integer.parseInt(age.substring(0,sb1)) ;
		//	System.out.println("yearDif:"+yearDif) ;
			//int monthDif = Integer.valueOf(age.substring(sb1+1, sb2)).intValue();
			int monthDif = Integer.parseInt(age.substring(sb1+1, sb2));
		//	System.out.println("monthDif:"+monthDif) ;
			//int dayDif =  Integer.valueOf(age.substring(sb2+1)).intValue() ;
			if (yearDif==2){
				if (monthDif>=6) {
					return "2.6" ;
				} else {
					return "2" ;
				} //TODO = переделать !! убрать неактуальные возрастные группы! 18-01-2019
			} else if (yearDif==1){
				if (monthDif==0) return "1" ;
				else if (monthDif==1 && reMonth) return "1" ;
				else if (monthDif==2 && reMonth) return "1.3" ;
				else if (monthDif==3) return "1.3" ;
				else if (monthDif==5 && reMonth) return "1.6" ;
				else if (monthDif==6) return "1.6" ;
				else if (monthDif==8 && reMonth) return "1.9" ;
				else if (monthDif==9) return "1.9" ;
				else if (monthDif==11 && reMonth) return "2" ;
				return "1."+monthDif+"" ;
			} else if (yearDif==0){
				return ""+yearDif+"."+monthDif ;
			//} else if(yearDif<18) {
			//	return ""+yearDif ;
			} else {
			//int year1=Integer.valueOf(birthDayS.substring(6)).intValue() ;
			//int year2=Integer.valueOf(aFinishDate.substring(6)).intValue() ;
				int year1=Integer.parseInt(birthDayS.substring(6)) ;
				int year2=Integer.parseInt(aFinishDate.substring(6)) ;
				if (year2<20) year2=year2+2000 ;
				if (year2<100) year2=year2+1900 ;
			//	System.out.println("year1="+year1) ;
			//	System.out.println("year2="+year2) ;
				return ""+(year2-year1) ;
			}
			
		} catch(Exception e) {
			LOG.error("ERR",e);
			return "" ;
		}
		
	}
	
	public String checkPolicy(String aRoles,HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(aRoles, aRequest) ? "1" : "0";
	}
	public String getFactorByProfession(Long aProfession,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = " select vdp.id,vdp.factorOfProduction from VocDocumentProfession vdp " +
				" where  vdp.id='" + aProfession + "'";
		Collection<WebQueryResult> list = service.executeNativeSql(sql,1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get2()!=null) {
				return ""+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodefByRegIcForeign(Long aArea, Long aCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select smo.id,smo.name from Omc_SprSmo smo ")
		.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
		.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
		.append(" where ")
		.append(" ter.id='").append(aArea)
		.append("' and com.id='").append(aCompany).append("'");
	//	System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getRegIcForeignByCodef(Long aOgrnCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select com.id,coalesce(com.omcCode,'')||' '||com.name from Omc_SprSmo smo ")
			.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
			.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
			.append(" where ")
			.append(" smo.id='").append(aOgrnCompany).append("'")
			;
		//System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodeByMedPolicyOmc(Long aType,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getCodeByMedPolicyOmc(aType);
		
	}
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getInfoVocForFond(aPassportType,aAddress,aPolicy);
		
	}
	public boolean updateDataByFond(Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress
			, boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress, boolean aIsAttachment, HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.updateDataByFond(username,aPatientId, aFiodr, aDocument, aPolicy, aAddress,aIsPatient,aIsPolicy
				, aIsDocument, aIsAddress, aIsAttachment);
		
	}
	public Object checkPatientByPolicy(Long aPatientId, String aSeries, String aNumber,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return FondWebService.checkPatientByMedPolicy(aRequest, getPatientInfo(aPatientId, service),aSeries,aNumber) ;
	}
	public Object checkPatientByCommonNumber(Long aPatientId, String aCommonNumber,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return FondWebService.checkPatientByCommonNumber(aRequest, getPatientInfo(aPatientId, service),aCommonNumber) ;
	}
	public Object checkPatientBySnils(Long aPatientId, String aSnils,HttpServletRequest aRequest) throws Exception {
		if (aSnils!=null&&!aSnils.equals("")){
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return FondWebService.checkPatientBySnils(aRequest, getPatientInfo(aPatientId, service),aSnils) ;
		} else {
			return "Не заполнено поле \"СНИЛС\"";	
		}
	}

	public Object checkPatientByFioDr(Long aPatientId, String aLastname,String aFirstname
			,String aMiddlename, String aBirthday,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return FondWebService.checkPatientByFioDr( aRequest, getPatientInfo(aPatientId, service),aLastname, aFirstname
				, aMiddlename,  aBirthday) ;
	}
	public Object checkPatientByDocument(Long aPatientId, Long aType, String aSeries
			,String aNumber,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String type = service.getOmcCodeByPassportType(aType) ;
		return FondWebService.checkPatientByDocument(aRequest, getPatientInfo(aPatientId, service),type, aSeries, aNumber) ;
	}
	private PatientForm getPatientInfo(Long aPatientId, IPatientService aService) {
		return (aPatientId!=null &&aPatientId> 0L)?aService.getPatientById(aPatientId):null ;
	}
	public String getAge(String aDate)  {
		try {
			java.sql.Date birthday = DateFormat.parseSqlDate(aDate) ;
			java.sql.Date curdate = new java.sql.Date(new java.util.Date().getTime()) ;
			return AgeUtil.getAgeCache(curdate, birthday, 3);
		} catch(Exception e) {
			LOG.error("Cant get age",e);
			return "" ;
		}
	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
								 String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,String aAction, HttpServletRequest aRequest) throws ParseException, NamingException {

		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, aAction);//"123";
	}
	private void createAdminChangeMessageByPatient (Long aSmo, String aType, String aTextInfo
			, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql.append("insert into AdminChangeJournal ( patient, createDate, createTime")
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
	public void movePatientDoubleData(Long aIdNew, Long aIdOld,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		service.movePatientDoubleData(aIdNew, aIdOld) ;
		createAdminChangeMessageByPatient(aIdNew, "MOVE_PATIENT_DOUBLE_DATA", "Перенесены данные из персоны "+aIdOld+" в "+aIdNew, aRequest) ;
	}

	public String addPatient(String aLastname, String aFirstname, String aMiddlename, String aBirthday, Long aSex, Long aSocialStatus, String aSnils, HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		SnilsStringValidator val = new SnilsStringValidator() ;
		val.validate(aSnils, null, aRequest) ;
		
		return service.addPatient(aLastname, aFirstname, aMiddlename, aBirthday, aSex, aSocialStatus, aSnils) ;
	}
	public boolean setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus,HttpServletRequest aRequest)throws Exception  {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ; 
		service.setAddParamByMedCase(aParam,aMedCase,aStatus);
		return true ;
	}

	public boolean checkSLSonDepartment(String idMedcase,HttpServletRequest aRequest)throws Exception  {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

		Collection<WebQueryResult> res = service.executeNativeSql("select count(c.id) from Certificate c " +
				"where dtype='ShortConfinementCertificate' and medcase_id = "+idMedcase);
		int count = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;

		if(count==0){
			res = service.executeNativeSql("select department_id from medcase where id =" + idMedcase);
			int id = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;
			res = service.executeNativeSql("select keyvalue from softconfig  where key = 'BirthDepId'");
			int depId =  !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;
			return depId==id;
		}
		return false;
	}
}