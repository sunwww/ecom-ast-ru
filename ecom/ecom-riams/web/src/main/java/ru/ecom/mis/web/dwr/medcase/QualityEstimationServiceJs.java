package ru.ecom.mis.web.dwr.medcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;


import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.expert.IQualityEstimationService;
import ru.ecom.mis.ejb.service.expert.QualityEstimationRow;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;


public class QualityEstimationServiceJs {
	
	public String getDefectsByCriterion(Long aCriterion, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;	
		String sql = "select id, name from vocQualityEstimationCritDefect vqect where criterion="+aCriterion;
		StringBuilder ret = new StringBuilder(); 
		Collection<WebQueryResult> list = service.executeNativeSql(sql) ;
		for (WebQueryResult r: list){
			if (ret.length()>0){ ret.append("#");}
			ret.append(""+r.get1()+":"+r.get2());
		}
		
		
		return ret.length()>0?ret.toString():"";
	}
	
	public String checkIsCommentNeed(Long aMarkId, HttpServletRequest aRequest ) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;	
		String sql = "select case when vqem.isNeedComment='1' then 1 else 0 end, vqem.criterion_id from vocQualityEstimationMark vqem where id="+aMarkId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql) ;
		if (list.size()>0){
			WebQueryResult r =list.iterator().next();
			if (r.get1().toString().equals("1")){
				return "1@"+getDefectsByCriterion(Long.valueOf(r.get2().toString()), aRequest);
			}
			
			return r.get1().toString();
		} else return "0";
	}
	
	public Boolean ableCreate(String aTypeSpecialist, HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Create,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/"+aTypeSpecialist, aRequest) ;
	}
	public Long getIdQualityEstimationByType(String aType, Long aIdCard, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getIdQualityEstimationByType(aType, aIdCard) ;
	}
	public String getRowEdit(String aRequestParam, Long aCardId, String aTypeSpecialist, boolean aView, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		QualityEstimationRow row = service.getRow(IsFullExpertCardEdit(aRequest),aRequestParam, aCardId, aTypeSpecialist, aView) ;
		//res.append("<script type='text/javascript'>").append(row.getJavaScriptText()).append("</script>") ;
		return  row!=null?row.getTableColumn():"";
	}
	public String getRow( Long aCardId, String aTypeSpecialist, boolean aView, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		QualityEstimationRow row = service.getRow(aCardId, aTypeSpecialist, aView) ;
		//res.append("<script type='text/javascript'>").append(row.getJavaScriptText()).append("</script>") ;
		return  row!=null?row.getTableColumn():"";
	}
	public String getInfoBySmo(Long aSmo, HttpServletRequest aRequest) throws Exception {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder ret = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.id,p.lastname||' '||p.firstname||' '||p.middlename,")
			.append(" case  ")
			.append(" when smo.dtype='DepartmentMedCase' then case when psmo.statisticstub_id is null then 'нет №стат.карты' else ss1.code end ")
			.append(" when smo.dtype='HospitalMedCase' then case when smo.statisticstub_id is null then p.patientSync else ss.code end")
			.append(" when smo.dtype='Visit' then p.patientSync ");
		sql.append(" else ''||smo.id end")  
			.append(", case  ")
			.append(" when smo.dtype='HospitalMedCase' and smo.deniedHospitalizating_id is null ")
			.append(" then (select fslo.id from medcase fslo where fslo.parent_id=smo.id and fslo.datefinish is not null and fslo.dtype='DepartmentMedCase')")
			.append(" else null ")
			.append(" end lastslo ")
			.append(" from medcase smo ")
			.append(" left join statisticstub ss on smo.statisticstub_id=ss.id  ")
			.append(" left join medcase psmo on psmo.id=smo.parent_id  ")
			.append(" left join statisticstub ss1 on psmo.statisticstub_id=ss1.id  ")
			.append(" left join patient p on p.id=smo.patient_id ")
			.append("where smo.id='").append(aSmo).append("'") ;
		List<Object[]> list = service.executeNativeSqlGetObj(sql.toString()) ;
		if (list.size()>0) {
			Object[] row = list.get(0) ;
			ret.append(row[0]!=null?row[0]:"")
			.append("#").append(row[1]!=null?row[1]:"")
			.append("#").append(row[2]!=null?row[2]:"")
			.append("#").append(row[3]!=null?row[3]:"0")
			;
		} else {
			return null ;
		}
		return ret.toString() ;
		
	}
	public String getInfoByDep(Long aSmo, Long aDepartment, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getInfoByDep(aSmo, aDepartment);
		
	}
	public String getInfoBySlo(Long aSmo, Long aSlo, HttpServletRequest aRequest) throws Exception {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		if (aSlo==null||aSlo.equals(Long.valueOf(0))||aSlo.equals(aSmo)){
			sql.append("select id, id from medcase slo where parent_id="+aSmo+" and dtype='DepartmentMedCase'  order by datestart desc");
			List<Object[]> slos = service.executeNativeSqlGetObj(sql.toString()) ;
			if (slos.size()>0){
				aSlo = Long.valueOf(slos.get(0)[0].toString());
			}
			sql.setLength(0);
		}
		String kindCode = null;
		
		sql.append("select upper(smo.dtype),count(*) from medcase smo where smo.id='").append(aSmo).append("' group by smo.dtype") ;
		List<Object[]> list = service.executeNativeSqlGetObj(sql.toString()) ;
		if (list.size()>0) {
			String dtype=list.get(0)[0].toString() ;
			//Стационар
			StringBuilder ret = new StringBuilder() ;
			if (dtype!=null && dtype.equals("HOSPITALMEDCASE")) {
				kindCode="1";
				sql = new StringBuilder() ;
				sql.append("select wf.id as wfid,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
					.append(" ,mkb.id as mkbid,mkb.code as mkbcode,mkb.name as mkbname,diag.name as diagname,mkb1.id as mkb1id,mkb1.code as mkb1code,mkb1.name as mkb1name,diag1.name as diag1name")
					.append(",dep.id as depid,dep.name as depname")
					.append(" from medcase smoM") 
					.append(" left join medcase smoD on smoD.parent_id=smoM.id and smoD.DTYPE='DepartmentMedCase'")
					.append(" left join diagnosis diag on diag.medcase_id=smoD.id")
					.append(" left join diagnosis diag1 on diag1.medcase_id=smoM.id")		
					.append(" left join MisLpu dep on dep.id = smoD.department_id")
					.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='4'")
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code='1'")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code='1'")
					.append(" left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id")
					.append(" left join workfunction wf on wf.id=smoD.ownerFunction_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where smoM.id = '").append(aSmo).append("' and smoD.id='").append(aSlo).append("'")
					.append(" and vdrt.code='4' and vpd.code='1' ");
				;
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (list.size()>0) {
					
					Object[] row = list.get(0) ;
					ret.append(row[0]!=null?row[0]:"").append("#").append(row[1]!=null?row[1]:"").append("#") ;
					if (row[2]!=null) {
						ret.append(row[2])
							.append("#").append(row[3]!=null?row[3]:"").append(" ")
							.append(row[4]!=null?row[4]:"").append("#")
							.append(row[5]!=null?row[5]:"") ;
						
					} else {
						ret.append(row[6]!=null?row[6]:"")
							.append("#").append(row[7]!=null?row[7]:"")
							.append(" ").append(row[8]!=null?row[8]:"")
							.append("#").append(row[9]!=null?row[9]:"") ;
						
					}
					ret.append("#").append(row[10]!=null?row[10]:"").append("#").append(row[11]!=null?row[11]:"") ;
					
				}
				//Случай лечения в отделении
			} else if (dtype!=null && dtype.equals("DEPARTMENTMEDCASE")){
				kindCode="1";
				sql = new StringBuilder() ;
				sql.append("select wf.id as wfid,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
					.append(" ,mkb.id as mkbid,mkb.code as mkbcode,mkb.name as mkbname,diag.name as diagname,mkb1.id as mkb1id,mkb1.code as mkb1code,mkb1.name as mkb1name,diag1.name as diag1name")
					.append(",dep.id as depid,dep.name as depname")
					.append(" from medcase smoM") 
					.append(" left join medcase smoD on smoD.parent_id=smoM.id and smoD.DTYPE='DepartmentMedCase'")
					.append(" left join diagnosis diag on diag.medcase_id=smoD.id")
					.append(" left join diagnosis diag1 on diag1.medcase_id=smoM.id")		
					.append(" left join MisLpu dep on dep.id = smoD.department_id")
					.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='4'")
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code='1'")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code='1'")
					.append(" left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id")
					.append(" left join workfunction wf on wf.id=smoD.ownerFunction_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where smoD.id='").append(aSlo).append("'")
					.append(" and vdrt.code='4' and vpd.code='1' ");
				;
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (list.size()>0) {
					Object[] row = list.get(0) ;
					ret.append(row[0]!=null?row[0]:"").append("#").append(row[1]!=null?row[1]:"").append("#") ;
					if (row[2]!=null) {
						ret.append(row[2])
							.append("#").append(row[3]!=null?row[3]:"").append(" ")
							.append(row[4]!=null?row[4]:"").append("#")
							.append(row[5]!=null?row[5]:"") ;
						
					} else {
						ret.append(row[6]!=null?row[6]:"")
							.append("#").append(row[7]!=null?row[7]:"")
							.append(" ").append(row[8]!=null?row[8]:"")
							.append("#").append(row[9]!=null?row[9]:"") ;
						
					}
					ret.append("#").append(row[10]!=null?row[10]:"").append("#").append(row[11]!=null?row[11]:"") ;
					
				}
					// Поликлинический случай лечения
			} else if (dtype!=null && dtype.equals("POLYCLINICMEDCASE")) {
				kindCode="2";
				/**
				 * TODO 
				 * Доделать
				 */
				ret.append("######");
			} else if (dtype!=null&& dtype.equals("VISIT")) {
				//start
				kindCode="2";
				sql = new StringBuilder() ;
				sql.append("select wf.id as wfid,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
					.append(" ,mkb.id as mkbid,mkb.code as mkbcode,mkb.name as mkbname,diag.name as diagname")
					.append(",dep.id as depid,dep.name as depname")
					.append(" from medcase vis") 
					.append(" left join diagnosis diag on diag.medcase_id=vis.id")
					.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='4'")
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code='1'")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join workfunction wf on wf.id=vis.workFunctionExecute_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join MisLpu dep on dep.id = w.lpu_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where vis.id='").append(aSlo).append("'");
					//.append(" and vdrt.code='4' and vpd.code='1' "); ;
				;
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (list.size()>0) {
					
					Object[] row = list.get(0) ;
					ret.append(row[0]!=null?row[0]:"").append("#").append(row[1]!=null?row[1]:"").append("#") ;
					if (row[2]!=null) {
						ret.append(row[2])
							.append("#").append(row[3]!=null?row[3]:"").append(" ")
							.append(row[4]!=null?row[4]:"").append("#")
							.append(row[5]!=null?row[5]:"") ;
						
					} else {
						ret.append("# #") ;
						
					}
					ret.append("#").append(row[6]!=null?row[6]:"").append("#").append(row[7]!=null?row[7]:"") ;
					
				//finish
			}
		}
			sql.setLength(0);
			sql.append("select id, name, code from vocqualityestimationkind where code='"+kindCode+"'");
			list = service.executeNativeSqlGetObj(sql.toString());
			if (list.size()>0) {
				Object[] o = list.get(0);
				ret.append("#").append(o[0]!=null?o[0]:"").append("#").append(o[1]!=null?o[1]:"");
			}
			
			return ret!=null?ret.toString():null;
			}
		
		return null ;
	}
	
	public String getCountRow(Long aCardId, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getCountRow(aCardId);
		
	}
	private boolean IsFullExpertCardEdit(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Create,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/FullExpertCard", aRequest) ;
	}
//Milamesher булевские ли значения критериев
	public String IsQECardKindBoolean(Long aCardId, HttpServletRequest aRequest) throws Exception {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select case when vqec.code='PR203' then '1' else '0' end  from QualityEstimationCard qec left join VocQualityEstimationKind vqec on vqec.id=qec.kind_id where qec.id=" + aCardId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql,1) ;
		if (list.size()!=0) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1());
		}
		return res.toString();//.equals("5");
	}
	//Milamesher критерии по medcase при диагнозе
	public String showCriteriasByDiagnosis(Long id,HttpServletRequest aRequest) throws NamingException {
		StringBuilder res = new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Long medcase;
		String query="select parent_id from medcase where id=" +id;
		Collection<WebQueryResult> list0 = service.executeNativeSql(query);
		if (list0.size()!=0) {
			medcase = Long.parseLong(list0.iterator().next().get1().toString());
			String json = getAllServicesByMedCase(medcase, aRequest);
			List<String> allMatches = new ArrayList<String>();
			Matcher m = Pattern.compile("\"vmscode\":\"[A-Za-z0-9.]*\"").matcher(json);
			while (m.find()) {
				allMatches.add(m.group().replace("\"vmscode\":\"", "").replace("\"}]", "").replace("\"", ""));
				//res.append(m.group().replace("\"vmscode\":\"", "").replace("\"}]", "").replace("\"", "")).append(" ");
			}
			//return res.toString();
			/*m = Pattern.compile("\"vmscode\":\"\\S*\"},").matcher(json);
			while (m.find()) {
				allMatches.add(m.group().replace("\"vmscode\":\"", "").replace("\"},", ""));
				res.append(m.group().replace("\"vmscode\":\"", "").replace("\"},", "")).append(";");
			}*/
			query = "select distinct vqecrit.code,vqecrit.name,vqecrit.medservicecodes\n" +
					" from vocqualityestimationcrit vqecrit\n" +
					" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  \n" +
					" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id \n" +
					" left join diagnosis ds on ds.idc10_id=d.id \n" +
					" left join medcase mc on mc.id=ds.medcase_id \n" +
					" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id  \n" +
					" left join vocprioritydiagnosis prior on prior.id=ds.priority_id \n" +
					" where mc.id=" + id + " and reg.code='4' and prior.code='1'";
			Collection<WebQueryResult> list = service.executeNativeSql(query);
			if (list.size() > 0) {
				for (WebQueryResult w : list) {
					String mcodes = (w.get3() != null) ? w.get3().toString() : "";
					Boolean flag = false;
					res.append(w.get1()).append("#").append(w.get2()).append("#");
					if (allMatches.size() > 0) {
						for (int i=0; i<allMatches.size(); i++) {
							String scode = allMatches.get(i);
							if (mcodes.contains("'" + scode + "'")) flag = true;
						}
					}
					if (flag) res.append("Да");
					else res.append("Нет");
					res.append("!");
				}
			}
			else res.append("##");
		}
		else res.append("##");
		return res.toString();//*/}return null;
	}
	//Milamesher чисто критерии по диазнозу, список
	public String showJustCriterias(Long idc10_id, Long regID, Long priorId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res=new StringBuilder();
		if (regID!=null && priorId!=null) {
			String query = "select case when (select code from vocdiagnosisregistrationtype where id= "
					+ regID + " )='4' and (select code from vocprioritydiagnosis where id= " + priorId + " )='1' then '1' else '0' end";
			if (service.executeNativeSql(query).iterator().next().get1().equals("1")) {
				query = "select vqecrit.name\n" +
						" from vocqualityestimationcrit vqecrit\n" +
						" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  \n" +
						" where vqecrit_d.vocidc10_id=" + idc10_id;
				Collection<WebQueryResult> list = service.executeNativeSql(query);
				if (list.size() > 0) {
					for (WebQueryResult w : list) {
						res.append(w.get1()).append("#");
					}
				} else res.append("##");
			} else res.append("##");
		}
		else { //если по умолчанию основной клинический
			String query = "select vqecrit.name\n" +
					" from vocqualityestimationcrit vqecrit\n" +
					" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  \n" +
					" where vqecrit_d.vocidc10_id=" + idc10_id;
			Collection<WebQueryResult> list = service.executeNativeSql(query);
			if (list.size() > 0) {
				for (WebQueryResult w : list) {
					res.append(w.get1()).append("#");
				}
			} else res.append("##");
		}
		return res.toString();
	}
	public String getAllServicesByMedCase(Long aMedcaseId,HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
		return service.getAllServicesByMedCase(aMedcaseId);
	}
	public String GetIfCommentYesNoNeeded(String type, Long markId, Long qEId, Boolean createEdit,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String res = "";
		if (type.equals("BranchManager")) {//если оценки проставляет заведующий, может быть разница с автоматическим расчётом
			Long medcase;
			String query = "";
			if (!createEdit)
				query = "select m.parent_id from qualityestimationcard qec left join medcase m on m.id=qec.medcase_id where qec.id=" + qEId; //если создаётся новое
			else query = "select qecard.medcase_id from qualityestimationcard qecard\n" +
					"left join qualityestimation qe on qe.card_id=qecard.id\n" +
					"where qe.id=" + qEId; //если ред-е
			Collection<WebQueryResult> list0 = service.executeNativeSql(query);
			if (list0.size() != 0) {
				medcase = Long.parseLong(list0.iterator().next().get1().toString());
				String json = getAllServicesByMedCase(medcase, aRequest);
				List<String> allMatches = new ArrayList<String>();
				Matcher m = Pattern.compile("\"vmscode\":\"[A-Za-z0-9.]*\"").matcher(json);
				while (m.find()) {
					allMatches.add(m.group().replace("\"vmscode\":\"", "").replace("\"}]", "").replace("\"", ""));
				}
				query = "select vqcrit.medservicecodes,qem.name\n" +
						"from vocqualityestimationcrit vqcrit\n" +
						"left join vocqualityestimationmark qem on qem.criterion_id=vqcrit.id\n" +
						"where qem.id=" + markId;
				WebQueryResult w = service.executeNativeSql(query).iterator().next();
				String mark = (w.get2() != null) ? w.get2().toString() : "";
				String mcodes = (w.get1() != null) ? w.get1().toString() : "";
				Boolean flag = false;
				if (!mcodes.equals("")) {
					for (int i = 0; i < allMatches.size(); i++) {
						String scode = allMatches.get(i);
						if (mcodes.contains("'" + scode + "'")) flag = true;
					}
					if (mark.equals("Да") && !flag || mark.equals("Нет") && flag) res = "true";
					else res = "false";
				}
			}
		} else if (type.equals("Expert")) { //эксперт - пред. этам - зав.
			String query = "";
			if (!createEdit) {
				query = "select qem.name \n" +
						"from vocqualityestimationmark qem\n" +
						"left join qualityestimationcrit qecrit on qem.id=qecrit.mark_id\n" +
						"left join qualityestimation qe on qe.id=qecrit.estimation_id\n" +
						"left join qualityestimationcard qecard on qecard.id=qe.card_id\n" +
						"where qe.experttype='BranchManager' and qecard.id=" + qEId + " and qem.id=" + markId;

			} else {
				query = "select qem.name\n" +
						"from vocqualityestimationmark qem\n" +
						"left join qualityestimationcrit qecrit on qem.id=qecrit.mark_id\n" +
						"left join qualityestimation qe on qe.id=qecrit.estimation_id\n" +
						"left join qualityestimationcard qecard on qecard.id= qe.card_id\n" +
						"where qe.experttype='BranchManager' and qe.id=(select id from qualityestimation\n" +
						"where experttype='BranchManager' and card_id=(select card_id from qualityestimation where id=" + qEId + ")) \n" +
						"and qem.id=" + markId;
			}
			res = (service.executeNativeSql(query).size() > 0) ? "false" : "true";
		}
		return res;
	}
}