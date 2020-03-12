package ru.ecom.mis.web.dwr.medcase;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.expert.IQualityEstimationService;
import ru.ecom.mis.ejb.service.expert.QualityEstimationRow;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QualityEstimationServiceJs {
	
	public String getDefectsByCriterion(Long aCriterion, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;	
		String sql = "select id, name from vocQualityEstimationCritDefect vqect where criterion="+aCriterion;
		StringBuilder ret = new StringBuilder(); 
		Collection<WebQueryResult> list = service.executeNativeSql(sql) ;
		for (WebQueryResult r: list){
			if (ret.length()>0){ ret.append("#");}
			ret.append(r.get1()).append(":").append(r.get2());
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
		if (aSlo==null||aSlo.equals(0L)||aSlo.equals(aSmo)){
			sql.append("select id, id from medcase slo where parent_id=").append(aSmo).append(" and dtype='DepartmentMedCase'  order by datestart desc");
			List<Object[]> slos = service.executeNativeSqlGetObj(sql.toString()) ;
			if (slos.size()>0){
				aSlo = Long.valueOf(slos.get(0)[0].toString());
			}
			sql.setLength(0);
		}
		String kindCode = null;
		
		sql.append("select upper(smo.dtype),count(*) from medcase smo where smo.id='").append(aSmo).append("' group by smo.dtype") ;
		List<Object[]> list = service.executeNativeSqlGetObj(sql.toString()) ;
		if (!list.isEmpty()) {
			String dtype=list.get(0)[0].toString() ;
			//Стационар
			StringBuilder ret = new StringBuilder() ;
			if ("HOSPITALMEDCASE".equals(dtype)) {
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
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (!list.isEmpty()) {
					
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
					ret.append("#").append(row[10]!=null ? row[10] : "").append("#").append(row[11]!=null ? row[11] : "") ;
					
				}
				//Случай лечения в отделении
			} else if ("DEPARTMENTMEDCASE".equals(dtype)){
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
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (!list.isEmpty()) {
					Object[] row = list.get(0) ;
					ret.append(row[0]!=null?row[0]:"").append("#").append(row[1]!=null ? row[1] : "").append("#") ;
					if (row[2]!=null) {
						ret.append(row[2])
							.append("#").append(row[3]!=null ? row[3] : "").append(" ")
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
			} else if ("POLYCLINICMEDCASE".equals(dtype)) {
				kindCode="2";
				/**
				 * TODO 
				 * Доделать
				 */
				ret.append("######");
			} else if ("VISIT".equals(dtype)) {
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
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (!list.isEmpty()) {
					
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
			sql.append("select id, name, code from vocqualityestimationkind where code='").append(kindCode).append("'");
			list = service.executeNativeSqlGetObj(sql.toString());
			if (!list.isEmpty()) {
				Object[] o = list.get(0);
				ret.append("#").append(o[0]!=null?o[0]:"").append("#").append(o[1]!=null?o[1]:"");
			}
			
			return ret.toString();
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

    /**
     * Получить, является ли карта картой по 203 приказу (булевские ли значения критериев)
     * @param aCardId QualityEsimationCard.id экспертная карта
     * @param aRequest HttpServletRequest
     * @return String 1 - да, 0 - нет
     */
	public String IsQECardKindBoolean(Long aCardId, HttpServletRequest aRequest) throws Exception {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select case when vqec.code='PR203' then '1' else case when vqec. code='KMP' then '-1' else '0' end end from QualityEstimationCard qec left join VocQualityEstimationKind vqec on vqec.id=qec.kind_id where qec.id=" + aCardId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql,1) ;
		if (list.size()!=0) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1());
		}
		return res.toString();
	}

	/**
	 * Получить критерии по medcase при диагнозе
	 * @param medCaseId MedCase.id
	 * @param aRequest HttpServletRequest
	 * @return String json в списке: Критерии, автоматическая оценка, оценка в карте (если есть)
	 */
	public String showCriteriasByDiagnosis(Long medCaseId,HttpServletRequest aRequest) throws NamingException {
		JSONArray res = new JSONArray() ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		long medcase;
        String query="select parent_id from medcase where id=" +medCaseId;
		Collection<WebQueryResult> list0 = service.executeNativeSql(query);
		if (list0.size()!=0) {
		    WebQueryResult wqr = list0.iterator().next();
			medcase = Long.parseLong(wqr.get1().toString());
			ArrayList<String> listServicies = getAllServicesByMedCase(medcase, aRequest);

			query = "select distinct vqecrit.code,vqecrit.name,vqecrit.medservicecodes,coalesce(vqem.name,'') as vqename" +
					" from vocqualityestimationcrit vqecrit" +
					" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  " +
					" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id " +
					" left join diagnosis ds on ds.idc10_id=d.id " +
					" left join medcase mc on mc.id=ds.medcase_id " +
					" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id  " +
					" left join vocprioritydiagnosis prior on prior.id=ds.priority_id " +
					" left join patient pat on pat.id=mc.patient_id " +
					" left join qualityestimationcard qec on qec.medcase_id=mc.id" +
					" left join qualityestimation qe on qe.card_id=qec.id" +
					" left join VocQualityEstimationMark vqem on vqem.criterion_id=vqecrit.id " +
					" and vqem.id= (select max(qecC.mark_id) " +
					" from qualityestimationcrit qecC" +
					" left join qualityestimation qeC on qeC.card_id=qe.card_id and qecC.estimation_id=qeC.id " +
					" left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id" +
					" where vqemC.criterion_id=vqecrit.id and qeC.expertType='BranchManager')" +
					" where mc.id=" + medCaseId + " and reg.code='4' and prior.code='1'" +
					" and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqecrit.ischild=true)";
			Collection<WebQueryResult> list = service.executeNativeSql(query);
			if (list.size() > 0) {
				for (WebQueryResult w : list) {
					JSONObject o = new JSONObject() ;
					String[] mcodes = (w.get3() != null ? w.get3().toString().replaceAll("'","") : "").split(",");
					o.put("crit",w.get2())
							.put("mark",w.get4());
					Boolean flag=false;
					for (int i=0; i<mcodes.length && !flag; i++)
                        if (listServicies.indexOf(mcodes[i])!=-1) flag=true;
					if (flag) o.put("automark","Да");
					else o.put("automark","Нет");
					res.put(o);
				}
			}
		}
		return res.toString();
	}

    /**
     * Получить критерии чисто по диагнозу
     * @param idc10_id VocIdc10.id МКБ
     * @param regID Тип регистрации
     * @param priorId Приоритет
     * @param medcaseId СЛО
     * @param aRequest HttpServletRequest
     * @return String json в списке: Критерии
     */
	public String showJustCriterias(Long idc10_id, Long regID, Long priorId, Long medcaseId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder res=new StringBuilder();
		if (regID!=null && priorId!=null) {
			String query = "select case when (select code from vocdiagnosisregistrationtype where id= "
					+ regID + " )='4' and (select code from vocprioritydiagnosis where id= " + priorId + " )='1' then '1' else '0' end";
			if (service.executeNativeSql(query).iterator().next().get1().equals("1")) {
				query = "select distinct vqecrit.name\n" +
						" from vocqualityestimationcrit vqecrit\n" +
						" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  \n" +
						" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id \n" +
						" left join medcase mc on mc.id="+medcaseId + "\n" +
						" left join vocdiagnosisregistrationtype reg on reg.id=" + regID + "\n" +
						" left join vocprioritydiagnosis prior on prior.id=" + priorId + "\n" +
						" left join patient pat on pat.id=mc.patient_id \n" +
						" where vqecrit_d.vocidc10_id=" + idc10_id + "\n" +
						" and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqecrit.ischild=true)";
				Collection<WebQueryResult> list = service.executeNativeSql(query);
				if (list.size() > 0) {
					for (WebQueryResult w : list) {
						res.append(w.get1()).append("#");
					}
				} else res.append("##");
			} else res.append("##");
		}
		else { //если по умолчанию основной клинический (тут medcase - hospital)
			String query = "select distinct vqecrit.name\n" +
					" from vocqualityestimationcrit vqecrit\n" +
					" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id  \n" +
					" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id \n" +
					" left join medcase mc on mc.id=ANY(select id from medcase where parent_id=" + medcaseId + ")\n" +
					" left join vocdiagnosisregistrationtype reg on reg.code='4'  \n" +
					" left join vocprioritydiagnosis prior on prior.code='1' \n" +
					" left join patient pat on pat.id=mc.patient_id \n" +
					" where vqecrit_d.vocidc10_id=" + idc10_id + "\n" +
					" and (EXTRACT(YEAR from AGE(birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(birthday))<18 and vqecrit.ischild=true)";
			Collection<WebQueryResult> list = service.executeNativeSql(query);
			if (list.size() > 0) {
				for (WebQueryResult w : list) {
					res.append(w.get1()).append("#");
				}
			} else res.append("##");
		}
		return res.toString();
	}
	public ArrayList<String> getAllServicesByMedCase(Long aMedcaseId,HttpServletRequest aRequest) throws NamingException {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class);
		return service.getAllServicesByMedCase(aMedcaseId);
	}
	public String GetIfCommentYesNoNeeded(String type, Long markId, Long qEId, Boolean createEdit,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		String res = "";
		if (type.equals("BranchManager")) {//если оценки проставляет заведующий, может быть разница с автоматическим расчётом
			long medcase;
			String query;
			if (createEdit==null) {
				query = "select m.parent_id from qualityestimationcard qecard\n" +
						"left join medcase m on m.id=qecard.medcase_id\n" +
						"left join qualityestimation qe on qe.card_id=qecard.id\n" +
						"where qe.id=" + qEId; //если ред-е draft
			}
			else if (!createEdit) {
				query = "select m.parent_id from qualityestimationcard qec left join medcase m on m.id=qec.medcase_id where qec.id=" + qEId; //если создаётся новое
			}
			else {
				query = "select case when mc.dtype='HospitalMedCase' then mc.id else mc.parent_id end from medcase mc \n" +
						"left join qualityestimationcard qecard on qecard.medcase_id = mc.id\n" +
						"left join qualityestimation qe on qe.card_id=qecard.id\n" +
						"where qe.id=" + qEId; //если ред-е
			}
			Collection<WebQueryResult> list0 = service.executeNativeSql(query);
			if (list0.size() != 0) {
				medcase = Long.parseLong(list0.iterator().next().get1().toString());

                ArrayList<String> listServicies = getAllServicesByMedCase(medcase, aRequest);

				query = "select vqcrit.medservicecodes,qem.name\n" +
						"from vocqualityestimationcrit vqcrit\n" +
						"left join vocqualityestimationmark qem on qem.criterion_id=vqcrit.id\n" +
						"where qem.id=" + markId;
				WebQueryResult w = service.executeNativeSql(query).iterator().next();
				String mark = (w.get2() != null) ? w.get2().toString() : "";
				String mcodes = (w.get1() != null) ? w.get1().toString() : "";
				boolean flag = false;
				if (!mcodes.equals("")) {
					for (String scode : listServicies) {
						if (mcodes.contains("'" + scode + "'")) flag = true;
						//res+=scode+=" ; ";
					}
					if (mark.equals("Да") && !flag || mark.equals("Нет") && flag) res = "true";
					else res = "false";
				}
				else res = "false";
			}
		} else if (type.equals("Expert")) { //эксперт - пред. этам - зав.
			String query;
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

    /**
     * Создать черновик ЭК.
     * @param aMcaseId СЛО
     * @param aRequest HttpServletRequest
     * @return Long id созданного черновика
     */
	public Long createDraftEK(Long aMcaseId, String aCode, HttpServletRequest aRequest) throws NamingException {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class);
		return service.createDraftEK(aMcaseId, aCode);
	}

    /**
     * Получить departmentMedCase.
     * @param aCardId QualityEstimationCard.id
     * @param aRequest HttpServletRequest
     * @return String medcase_id СЛО
     */
	public String getDepMedcaseFromDraftEK(Long aCardId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select medcase_id from QualityEstimationCard where id=" + aCardId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql,1) ;
		if (list.size()!=0) {
			WebQueryResult wqr = list.iterator().next() ;
			res.append(wqr.get1());
		}
		return res.toString();
	}

    /**
     * Удалить критерий и всё, что на него ссылается.
     * @param aCritId qualityestimationcrit.id
     * @param aRequest HttpServletRequest
     * @return Boolean flag true - удалено, false - не найдено
     */
	public Boolean deleteCrit(Long aCritId, HttpServletRequest aRequest) throws NamingException {
		boolean flag=false;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String query ="select * from qualityestimationcrit where criterion_id=" + aCritId;
		Collection<WebQueryResult> list = service.executeNativeSql(query,1) ;
		if (list.size()==0) {
			query = "delete from vocqualityestimationcrit_diagnosis where vqecrit_id=" + aCritId;
			service.executeUpdateNativeSql(query);
			query = "delete from vocqualityestimationmark where criterion_id=" + aCritId;
			service.executeUpdateNativeSql(query);
			query = "delete from vocqualityestimationcrit where id=" + aCritId;
			service.executeUpdateNativeSql(query);
			flag=true;
		}
		return flag;
	}

    /**
     * Удалить связь критерий-диагноз.
     * @param aCritId qualityestimationcrit.id
     * @param aIdc10Id МКБ
     * @param aRequest HttpServletRequest
     */
	public void deleteDiagnoseOfCrit203ById(Long aCritId, Long aIdc10Id, HttpServletRequest aRequest) throws NamingException {
		(Injection.find(aRequest).getService(IWebQueryService.class)).executeUpdateNativeSql("delete from vocqualityestimationcrit_diagnosis where vqecrit_id=" + aCritId + " and vocidc10_id="+aIdc10Id);
	}

    /**
     * Добавить связь критерий-диагноз.
     * @param aCritId qualityestimationcrit.id
     * @param aIdc10Id МКБ
     * @param aRequest HttpServletRequest
     * @return Boolean flag true - добавлено, false - уже было
     */
	public Boolean addDiagnoseOfCrit203ById(Long aCritId, Long aIdc10Id, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String query="select * from vocqualityestimationcrit_diagnosis where vqecrit_id=" + aCritId + " and vocidc10_id="+aIdc10Id;
		Collection<WebQueryResult> list = service.executeNativeSql(query,1) ;
		boolean flag=false;
		if (list.size()==0) {
			service.executeUpdateNativeSql("insert into vocqualityestimationcrit_diagnosis(vocidc10_id, vqecrit_id) VALUES (" + aIdc10Id + "," + aCritId + ")");
			flag=true;
		}
		return flag;
	}

    /**
     * Получить диагноз по критерию.
     * @param aCritId qualityestimationcrit.id
     * @param aRequest HttpServletRequest
     * @return String данные диагноза
     */
	public String selectDiagnoseOfCrit203ById(Long aCritId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select idc.id,idc.code||' '||idc.name from vocidc10 idc\n" +
				"left join vocqualityestimationcrit_diagnosis vd on vd.vocidc10_id=idc.id\n" +
				"left join vocqualityestimationcrit vqec on vqec.id=vd.vqecrit_id\n" +
				"where vqec.id=" + aCritId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			for (WebQueryResult w : list) {
				res.append(w.get1()).append("#").append(w.get2()).append("!");
			}
		} else res.append("##");
		return res.toString();
	}

    /**
     * Получить услуги по критерию.
     * @param aCritId qualityestimationcrit.id
     * @param aRequest HttpServletRequest
     * @return String данные услуг
     */
	public String selectMedServOfCrit203ById(Long aCritId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select medservicecodes from vocqualityestimationcrit where id=" + aCritId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			for (WebQueryResult w : list) {
				res.append(w.get1());
			}
		} else res.append("##");
		return res.toString();
	}

    /**
     * Удалить связь критерий-услуга.
     * @param aCritId qualityestimationcrit.id
     * @param medServ String услуга
     * @param aRequest HttpServletRequest
     * @return String 0 - всё ок
     */
	public String deleteMedServOfCrit203ById(Long aCritId, String medServ, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select medservicecodes from vocqualityestimationcrit where id=" + aCritId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			for (WebQueryResult w : list) {
				res.append(w.get1());
			}
		}
		if (!res.toString().equals("")) {
			String ms=res.toString();
			ms=ms.replace("'"+medServ+"'","");
			ms=ms.replace(",,",",");
			ms=ms.replace("'","''");
			if (ms.length()>0 && ms.substring(0,1).equals(",")) ms=ms.substring(1);
			if (ms.endsWith(",")) ms=ms.substring(0,ms.length()-1);
			ms="'"+ms+"'";
			if ( ms.equals("null") || ms.equals("'")) ms="''";
			service.executeUpdateNativeSql("update vocqualityestimationcrit set  medservicecodes=" + ms + " where id=" + aCritId);
			return ms.replace("''","'").replace("''","'");
		}
		return "0";
	}

    /**
     * Добавить связь критерий-услуга.
     * @param aCritId qualityestimationcrit.id
     * @param medServId MedService.id
     * @param aRequest HttpServletRequest
     * @return String 0 - всё ок
     */
	public String addMedServOfCrit203ById(Long aCritId, String medServId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select medservicecodes from vocqualityestimationcrit where id=" + aCritId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			for (WebQueryResult w : list) {
				res.append(w.get1());
			}
		}
		sql = "select code from medservice where id=" + medServId;
		list = service.executeNativeSql(sql);
		String medServ="";
		if (list.size() > 0) {
			for (WebQueryResult w : list) {
				medServ=w.get1().toString();
			}
		}
		if (!medServ.equals("")) {
			String ms=res.toString();
			if (!ms.contains(medServ)) {
				if (res.toString().equals("")) {
					ms=medServ.replace("'","''");
					ms="'''"+ms+"'''";
				}
				else {
					ms=ms+","+"'"+medServ+"'";
					ms=ms.replace("'","''");
					ms="'"+ms+"'";
				}
				if (ms.equals("null") || ms.equals("'")) ms="''";
				service.executeUpdateNativeSql("update vocqualityestimationcrit set  medservicecodes=" + ms + " where id=" + aCritId);
				return ms.replace("''","'").replace("''","'");
			}
		}
		return "0";
	}

    /**
     * Очистить список услуг, привязанных к критерию.
     * @param aCritId qualityestimationcrit.id
     * @param aRequest HttpServletRequest
     */
	public void setMedServEmptyString(Long aCritId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update vocqualityestimationcrit set  medservicecodes='' where id=" + aCritId);
	}

    /**
     * Получить отделение, за которым закреплён пациент (для ЭК по отказам от госпитализаций) #105
     * @param mc MedCase.id СЛО
     * @param aRequest HttpServletRequest
     */
	public String getFixedDepartmentFromMedcase(String mc, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select mc.department_id,dep.name from medcase mc left join mislpu dep on dep.id=mc.department_id where mc.id=" + mc;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (list.size() > 0) {
			WebQueryResult w = list.iterator().next() ;
			res.append(w.get1()).append("#").append(w.get2());
		}
		else res.append("##");
		return res.toString();
	}

	/**
	 * Получить возможные способы предварительной записи пациентов (выбирает регистратор при создании предварительной записи) #145
	 * @param aSloId Slo.id
	 * @param aKindId vocqualityestimationkind.id
	 * @param aRequest HttpServletRequest
	 * @return Boolean Можно ли создавать (нельзя в случае, если выписан позднее опред. срока) #150
	 */
	public Boolean getIfCanCreateNow(Long aSloId, Long aKindId,HttpServletRequest aRequest) throws NamingException,JspException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();
		sql.append("select case when hmc.datefinish<current_date-(select cast(keyvalue as int) from softconfig where key='Pr203DaysAfterDischarge')")
				.append((aKindId!=null) ? " and (select code from vocqualityestimationkind where id="+aKindId+")='PR203'" : "")
				.append(" then '1' else '0' end")
				.append(" from medcase hmc")
				.append(" left join medcase slo on slo.parent_id=hmc.id")
				.append(" where slo.id=").append(aSloId).append(" or slo.parent_id=").append(aSloId);
		Collection<WebQueryResult> list = service.executeNativeSql( sql.toString());
		return list.isEmpty() || RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut",aRequest) || list.iterator().next().get1().equals("0");
	}

	/**
	 * Получить, есть ли необходимость делать эксертизу KMP
	 * @param aSloId Slo.id
	 * @param aRequest HttpServletRequest
	 * @return Boolean есть ли необходимость делать эксертизу KMP
	 */
	public Boolean getIfKMPNecessary(Long aSloId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct vocidc10_id,isconcomitant from")
				.append(" vocqualityestimationcrit_diagnosis")
				.append(" left join mislpu lpu on lpu.id=(select department_id from medcase where id=").append(aSloId).append(")")
                .append(" where vqecrit_id=ANY(select distinct vqecrit.id")
                .append(" from vocqualityestimationcrit vqecrit")
                .append(" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id")
                .append(" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id")
                .append(" left join diagnosis ds on ds.idc10_id=d.id")
                .append(" left join medcase mc on mc.id=ds.medcase_id")
                .append(" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id")
                .append(" left join vocprioritydiagnosis prior on prior.id=ds.priority_id")
                .append(" left join patient pat on pat.id=mc.patient_id")
                .append(" left join vocqualityestimationkind kind on kind.id=vqecrit.kind_id")
				.append(" left join mislpu lpu on lpu.id=mc.department_id")
                .append(" where mc.id=").append(aSloId)
                .append(" and kind.code='KMP'")
                .append(" and reg.code='4' and (prior.code='1' and (vqecrit_d.isconcomitant is null or vqecrit_d.isconcomitant=false)")
                .append(" or prior.code='3' and vqecrit_d.isconcomitant=true)) and lpu.isreportkmp=true");
        List<Object[]> list = service.executeNativeSqlGetObj(sql.toString()) ;
        //если есть соп. диагнозы, то необходимо, чтобы были все
		//если нет соп., то достаточно одного
		Boolean existsConc=false, yesMain=false, yesConc=false;
		for (Object[] o: list) {
			Boolean isConc = o[1]!=null? Boolean.valueOf(o[1].toString()) : false;
			if (isConc) existsConc=isConc;
		}
        for (Object[] o: list) {
            Long id = Long.valueOf(o[0].toString());
            Boolean isConc = o[1]!=null? Boolean.valueOf(o[1].toString()) : false;

            StringBuilder sql2 = new StringBuilder();
            sql2.append(" select ds.id from diagnosis ds")
                    .append(" left join medcase mc on mc.id=ds.medcase_id")
                    .append(" left join vocprioritydiagnosis prior on prior.id=ds.priority_id")
                    .append(" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id")
                    .append(" where mc.id=").append(aSloId).append(" and reg.code='4' and prior.code='");
            if (isConc) sql2.append("3");
            else sql2.append("1");
            sql2.append("' and ds.idc10_id='").append(id).append("'");
            if (!service.executeNativeSql( sql2.toString()).isEmpty()) {
            	if (!existsConc)
            		return true;  //просто основной есть и соп не нужен
            	if (!isConc) yesMain=true;
            	if (isConc) yesConc=true;

            	if (yesMain && yesConc){ //один осн. есть, один соп есть
					return true;
				}
			}
        }
		return false;
	}
}