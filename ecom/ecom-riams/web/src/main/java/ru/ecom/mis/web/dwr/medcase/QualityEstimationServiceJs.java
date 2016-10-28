package ru.ecom.mis.web.dwr.medcase;

import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.expert.IQualityEstimationService;
import ru.ecom.mis.ejb.service.expert.QualityEstimationRow;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class QualityEstimationServiceJs {
	public Long checkIsCommentNeed(Long aMarkId, HttpServletRequest aRequest ) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;	
		String sql = "select case when vqem.isNeedComment='1' then 1 else 0 end from vocQualityEstimationMark vqem where id="+aMarkId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		if (list.size()>0){
			return Long.valueOf(list.iterator().next().get1().toString());
		} else return 0L;
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
			.append(" when smo.dtype='HospitalMedCase' then case when smo.statisticstub_id is null then p.patientSync else ss.code end");
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
		
		sql.append("select upper(smo.dtype),count(*) from medcase smo where smo.id='").append(aSmo).append("' group by smo.dtype") ;
		List<Object[]> list = service.executeNativeSqlGetObj(sql.toString()) ;
		if (list.size()>0) {
			String dtype=list.get(0)[0].toString() ;
			//Стационар
			if (dtype!=null && dtype.equals("HOSPITALMEDCASE")) {
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
					.append(" where smoM.id = '").append(aSmo).append("' and smoD.id='").append(aSlo).append("'") ;
				;
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (list.size()>0) {
					StringBuilder ret = new StringBuilder() ;
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
					return ret.toString() ;
				}
				//Случай лечения в отделении
			} else if (dtype!=null && dtype.equals("DEPARTMENTMEDCASE")){
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
					.append(" where smoD.id='").append(aSlo).append("'") ;
				;
				list = service.executeNativeSqlGetObj(sql.toString()) ;
				if (list.size()>0) {
					StringBuilder ret = new StringBuilder() ;
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
					return ret.toString() ;
				}
					// Поликлинический случай лечения
			} else if (dtype!=null && dtype.equals("POLYCLINICMEDCASE")) {
				
			}
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
}
