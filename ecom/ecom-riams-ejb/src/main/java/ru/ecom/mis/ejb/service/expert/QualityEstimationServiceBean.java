package ru.ecom.mis.ejb.service.expert;

import java.util.HashMap;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.service.protocol.field.AutoCompleteField;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;

@Stateless
@Remote(IQualityEstimationService.class)
public class QualityEstimationServiceBean implements IQualityEstimationService {
	public String getInfoBySlo(Long aSmo, Long aSlo)  {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select upper(smo.dtype),count(*) from medcase smo where smo.id='").append(aSmo).append("'") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
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
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code=1")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code=1")
					.append(" left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id")
					.append(" left join workfunction wf on wf.id=smoD.ownerFunction_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where smoM.id = '").append(aSmo).append("' and smoD.id='").append(aSlo).append("'") ;
				;
				list = theManager.createNativeQuery(sql.toString()).getResultList() ;
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
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code=1")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code=1")
					.append(" left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id")
					.append(" left join workfunction wf on wf.id=smoD.ownerFunction_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where smoD.id='").append(aSlo).append("'") ;
				;
				list = theManager.createNativeQuery(sql.toString()).getResultList() ;
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
	
	public String getInfoByDep(Long aSmo, Long aDepartment){
		StringBuilder sql = new StringBuilder() ;
		sql.append("select upper(smo.dtype),count(*) from medcase smo where smo.id='").append(aSmo).append("'") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		if (list.size()>0) {
			String dtype=list.get(0)[0].toString() ;
			//Стационар
			if (dtype!=null && dtype.equals("HOSPITALMEDCASE")) {
				sql = new StringBuilder() ;
				sql.append("select wf.id as wfid,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename")
					.append(" ,mkb.id as mkbid,mkb.code as mkbcode,mkb.name as mkbname,diag.name as diagname,mkb1.id as mkb1id,mkb1.code as mkb1code,mkb1.name as mkb1name,diag1.name as diag1name")
					.append(" from medcase smoM") 
					.append(" left join medcase smoD on smoD.parent_id=smoM.id and smoD.DTYPE='DepartmentMedCase'")
					.append(" left join diagnosis diag on diag.medcase_id=smoD.id")
					.append(" left join diagnosis diag1 on diag1.medcase_id=smoM.id")					
					.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='4'")
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code=1")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code=1")
					.append(" left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id")
					.append(" left join workfunction wf on wf.id=smoD.ownerFunction_id")
					.append(" left join vocworkFunction vwf on vwf.id=wf.workFunction_id")
					.append(" left join worker w on w.id = wf.worker_id")
					.append(" left join patient wp on wp.id=w.person_id")
					.append(" where smoM.id = '").append(aSmo).append("' and smoD.department_id='").append(aDepartment).append("'") ;
				;
				list = theManager.createNativeQuery(sql.toString()).getResultList() ;
				if (list.size()>0) {
					StringBuilder ret = new StringBuilder() ;
					Object[] row = list.get(0) ;
					ret.append(row[0]!=null?row[0]:"")
					.append("#").append(row[1]!=null?row[1]:"")
					.append("#").append(row[2]!=null?row[2]:"")
					.append("#").append(row[3]!=null?row[3]:"").append(" ").append(row[4]!=null?row[4]:"")
					.append("#").append(row[5]!=null?row[5]:"") ;
					return ret.toString() ;
				}
				//Поликлиника
			} else if (dtype!=null && dtype.equals("POLYCLINICMEDCASE")){
				
			} else {
				
			}
		}
		
		return null ;
	}
	
	public String getInfoBySmo(Long aSmo) {
		StringBuilder ret = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.id,p.lastname||' '||p.firstname||' '||p.middlename,")
			.append("case when smo.dtype='POLYCLINICMEDCASE' then smo.id ")
			.append("when smo.dtype='DEPARTMENTMEDCASE' then ifnull(psmo.statisticstub_id,'нет №стат.карты',ss1.code) ")
			.append("when smo.dtype='HOSPITALMEDCASE' then ifnull(smo.statisticstub_id,'нет №стат.карты',ss.code) ");
		sql.append("else smo.id end")  
			.append(" from medcase smo ")
			.append(" left join statisticstub ss on smo.statisticstub_id=ss.id  ")
			.append(" left join medcase psmo on psmo.id=smo.parent_id  ")
			.append(" left join statisticstub ss1 on psmo.statisticstub_id=ss1.id  ")
			.append(" left join patient p on p.id=smo.patient_id ")
			.append("where smo.id='").append(aSmo).append("'") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		if (list.size()>0) {
			Object[] row = list.get(0) ;
			ret.append(row[0]!=null?row[0]:"").append("#").append(row[1]!=null?row[1]:"").append("#").append(row[2]!=null?row[2]:"") ;
		} else {
			return null ;
		}
		return ret.toString() ;
	}
	
	public String getCountRow(Long aCardId) {
		
		StringBuilder sql1 = new StringBuilder() ;
		
		//sql1.append("select count(*),vqec.id from VocQualityEstimationCrit vqec")
		//	.append(" where (select qec.kind_id from qualityestimationcard qec where qec.id='").append(aCardId).append("')=vqec.kind_id")			;
		sql1.append("select count(*),vqec.id from VocQualityEstimationCrit vqec")
			.append(" left join qualityestimationcard qec on qec.kind_id=vqec.kind_id where qec.id='").append(aCardId).append("'")			;
		//log(sql1) ;
		List<Object[]> list1 = theManager.createNativeQuery(sql1.toString()).getResultList() ;
		Long cntSection ;
		//Long kind ;
		if (list1.size()>0) {
			StringBuilder result = new StringBuilder() ;
			Object[] row = list1.get(0) ;
			cntSection = WorkerServiceBean.parseLong(row[0]) ;
			result.append(cntSection) ;
			for (int i =0 ; i<list1.size();i++) {
				result.append("#").append(list1.get(i)[1]);
			}
			
			//kind = WorkerServiceBean.parseLong(row[1]) ;
			return result.toString() ;
		} else {
			log("row null") ;
			return null ;
		}
	}
	private void log(Object aObj) {
		System.out.println(aObj) ;
	}
	public QualityEstimationRow getRow(Boolean aFullExpertCard, String aRequestParam,Long aCardId, String aTypeSpecialist, boolean aView) {
		boolean replaceValue = false ;
		HashMap<String, String> val = new HashMap<String, String> () ;
		log(aRequestParam) ;
		if (!aView&&aRequestParam!=null && !aRequestParam.trim().equals("")) {
			replaceValue = true ;
			String[] repSt = aRequestParam.split("#") ;
			log("length = "+repSt.length);
			for (String str: repSt) {
				String[] o = str.split(":") ;
				val.put(o[0], o[1]) ;
			}
		}
		StringBuilder sql = new StringBuilder() ;
		StringBuilder sql1 = new StringBuilder() ;
		
		//sql1.append("select count(*),vqec.kind_id from VocQualityEstimationCrit vqec")
		//	.append(" where (select qec.kind_id from qualityestimationcard qec where qec.id='").append(aCardId).append("')=vqec.kind_id group by vqec.kind_id")			;
		sql1.append("select count(*),vqec.kind_id from VocQualityEstimationCrit vqec")
			.append(" left join qualityestimationcard qec on qec.kind_id=vqec.kind_id where qec.id='").append(aCardId).append("' group by vqec.kind_id")			;
		
		List<Object[]> list1 = theManager.createNativeQuery(sql1.toString()).getResultList() ;
		int cntSection ;
		
		Long kind ;
		if (list1.size()>0) {
			Object[] row = list1.get(0) ;
			cntSection = WorkerServiceBean.parseLong(row[0]).intValue() ;
			kind = WorkerServiceBean.parseLong(row[1]) ;
		} else {
			
			return null ;
		}
		
		if (!aView && !aFullExpertCard) {
			return getRowShort(replaceValue,val,kind, aCardId, aTypeSpecialist, cntSection, aView) ;
		}
		
		 sql.append("select vqec.id as vqecid,vqec.code as vqeccode,vqec.name as vqecname,vqec.shortname as vqecshortname")
			.append(" ,(select count(*) from VocQualityEstimationMark vocmark where vocmark.criterion_id=vqec.id),vqem.id as vqemid,vqem.code as vqemcode,vqem.name as vqemname,vqem.mark as vqemmark")
			.append(",")
			.append(" (select qeBM.expertType from qualityestimationcrit qecBM")  
			.append("	left join qualityestimation qeBM on qeBM.card_id='").append(aCardId).append("'  and qecBM.estimation_id=qeBM.id") 
			.append("	where qecBM.mark_id=vqem.id and qeBM.expertType='BranchManager'")
			.append(") as branchManager")
			.append(",")
			.append("(select qeE.expertType from qualityestimationcrit qecE")
			.append("	left join qualityestimation qeE on qeE.card_id='").append(aCardId).append("' and qecE.estimation_id=qeE.id ")
			.append("	where qecE.mark_id=vqem.id and qeE.expertType='Expert'")
			.append(") as Expert,")
			.append("(select max(qecC.id) from qualityestimationcrit qecC")
			.append("	left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qecC.estimation_id=qeC.id") 
			.append("	where qecC.mark_id=vqem.id and qeC.expertType='Coeur'")
			.append(") as Coeur") 
			.append(",")
			.append(" (select max(qecC.mark_id) from qualityestimationcrit qecC")
			.append("	left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qecC.estimation_id=qeC.id") 
			.append(" left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id")
			.append("	where vqemC.criterion_id=vqec.id and qeC.expertType='BranchManager'")
			.append(") as branchManagerAct")
			.append(",")
			.append("(select max(qecC.mark_id) from qualityestimationcrit qecC")
			.append("	left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qecC.estimation_id=qeC.id") 
			.append(" left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id")
			.append("	where vqemC.criterion_id=vqec.id and qeC.expertType='Expert'")
			.append(") as ExpertAct,")
			.append("(select max(qecC.mark_id) from qualityestimationcrit qecC")
			.append("	left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qecC.estimation_id=qeC.id") 
			.append(" left join vocqualityestimationmark vqemC on vqemC.id=qecC.mark_id")
			.append("	where vqemC.criterion_id=vqec.id and qeC.expertType='Coeur'")
			.append(") as CoeurAct") 
			//.append(" ,qecBM.id as branchManager,qecE.id as Expert,qecC.id as Coeur")
			.append(" from VocQualityEstimationCrit vqec") 
			.append(" left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id")
			//.append(" left join qualityestimationcrit qecBM on qecBM.mark_id=vqem.id")
			//.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCardId).append("' and qeBM.expertType='BranchManager' and qecBM.estimation_id=qeBM.id")
			//.append(" left join qualityestimationcrit qecE on qecE.mark_id=vqem.id")
			//.append(" left join qualityestimation qeE on qeE.card_id='").append(aCardId).append("' and qeE.expertType='Expert' and qecE.estimation_id=qeE.id")
			//.append(" left join qualityestimationcrit qecC on qecC.mark_id=vqem.id")
			//.append(" left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qeC.expertType='Coeur' and qecC.estimation_id=qeC.id")
			.append(" where vqec.kind_id='").append(kind).append("'")			
			.append(" group by vqem.id")			;
		 //log(sql) ;
		 StringBuilder table = new StringBuilder() ;
		 StringBuilder javaScript = new StringBuilder() ;
		 table.append("<table border=1 width=90%>")  ;
		 table.append("<tr>") ;
		 table.append("<th rowspan=2>№№п/п</th>") ;
		 table.append("<th rowspan=2 colspan=2>Критерии качества медицинской помощи</th>") ;
		 table.append("<th colspan=3>Оценочные баллы</th>") ;
		 table.append("</tr>") ;
		 table.append("<tr>") ;
		 
		 table.append("<th>зав. отд</th>") ;
		 table.append("<th>эксперт</th>") ;
		 table.append("<th>КЭР</th>") ;
		 
		 table.append("</tr>") ;
		 
		 List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		 if (list.size()>0) {
			 boolean firststr = false;
			 int cntPart =1 ;
			 int cntSubsection = 0 ;
			 for (int i=0;i<list.size();i++) {
				 Object[] row = list.get(i) ;
				 table.append("<tr>") ;
				 if (cntSubsection==0)  {
					 cntSubsection = WorkerServiceBean.parseLong(row[4]).intValue() ;
					 table.append("<td rowspan='").append((cntSubsection+1)).append("' valign='top'>").append(cntPart++).append(".</td>") ;
					 table.append("<td colspan=5 align='center'><b><i>").append(row[3]).append("-").append(row[2]).append("</i></b></td>") ;
					 table.append("</tr><tr>") ;
					 firststr = true ;
				 }
				 cntSubsection--;
				 table.append("<td>").append(row[7]).append("</td>") ;
				 table.append("<td>").append(row[8]).append("</td>") ;
				 //BranchManager - зав.отделением
				 //Expert - эксперт
				 //Coeur - КЭР
				 Object valMarkId =  row[12];
				 log(aTypeSpecialist) ;
				 if (replaceValue &&!aView && aTypeSpecialist.equals("BranchManager")) {
					 valMarkId=val.get(String.valueOf(cntPart-1)) ;
					 log(String.valueOf(cntPart-1)+":"+valMarkId) ;
				 }
				 table.append(recordExpert(row[8], valMarkId,row[9],cntPart, cntSection,cntSubsection, "BranchManager", aTypeSpecialist, firststr, aView) );
				 valMarkId =  row[14];
				 if (replaceValue &&!aView&& aTypeSpecialist.equals("Expert")) {
					 valMarkId=val.get(String.valueOf(cntPart-1)) ;
				 }				 
				 table.append(recordExpert(row[8], valMarkId, row[10],cntPart, cntSection, cntSubsection, "Expert", aTypeSpecialist, firststr, aView)) ;
				 valMarkId =  row[14];
				 if (replaceValue &&!aView && aTypeSpecialist.equals("Coeur")) {
					 valMarkId=val.get(String.valueOf(cntPart-1)) ;
				 }				 
				 table.append(recordExpert(row[8], valMarkId, row[11],cntPart, cntSection, cntSubsection, "Coeur", aTypeSpecialist, firststr, aView) );
				 
				 
				 
				 table.append("</tr>") ;
				 firststr =false ;
				 
			 }
		 }
		 table.append("</table>") ;
		 QualityEstimationRow row = new QualityEstimationRow() ;
		 row.setJavaScriptText(javaScript.toString()) ;
		 row.setTableColumn(table.toString()) ;
		return row;
	}

	public QualityEstimationRow getRow(Long aCardId, String aTypeSpecialist, boolean aView) {
		return getRow(true, null, aCardId, aTypeSpecialist, aView);
	}
	public QualityEstimationRow getRowShort(Long aKind, Long aCard, String aTypeSpecialist, int aCntSection, boolean aView) {
		return getRowShort(false,new HashMap<String,String>(), aKind, aCard, aTypeSpecialist, aCntSection, aView);
	}
	
	public QualityEstimationRow getRowShort(boolean aReplaceValue,HashMap<String,String> aValueMap, Long aKind, Long aCard, String aTypeSpecialist, int aCntSection, boolean aView) {
		StringBuilder sql = new StringBuilder() ;
		StringBuilder table = new StringBuilder() ;
		StringBuilder javaScript = new StringBuilder() ;
		sql.append("select vqec.id,vqec.shortname,vqec.name") 
				.append(",")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='BranchManager') as branchManager")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='BranchManager') as branchManagerMark")
		.append(" ,")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='Expert') as expert")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='Expert') as expertMark")
		.append(" ,")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='Coeur') as coeur")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where vqem.criterion_id=vqec.id and qeBM.expertType='Coeur') as coeurMark")

		.append(" from VocQualityEstimationCrit vqec")
		.append(" where vqec.kind_id='").append(aKind).append("'") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		 if (list.size()>0) {
			 table.append("<table border=1 width=90%>")  ;
			 table.append("<tr>") ;
			 table.append("<th rowspan=2 colspan=1>Критерии качества медицинской помощи</th>") ;
			 table.append("<th rowspan=2>№№п/п</th>") ;
			 table.append("<th colspan=3>Оценочные баллы</th>") ;
			 table.append("</tr>") ;
			 table.append("<tr>") ;
			 
			 table.append("<th>зав. отд</th>") ;
			 table.append("<th>эксперт</th>") ;
			 table.append("<th>КЭР</th>") ;
			 
			 table.append("</tr>") ;
			 int cntPart =1 ;
			 for (int i=0;i<list.size();i++) {
				 Object[] row = list.get(i) ;
				 table.append("<tr>") ;
				 table.append("<td align='left'>").append(row[1]).append("-").append(row[2]).append("</td>") ;
				 table.append("<td valign='top' align='right'>").append(cntPart++).append(".</td>") ;

					 
			 
				 
				 //table.append("<td>").append(row[8]).append("</td>") ;
				 //BranchManager - зав.отделением
				 //Expert - эксперт
				 //Coeur - КЭР
				 Object valMark = row [3] ;
				 if (aReplaceValue &&!aView && aTypeSpecialist.equals("BranchManager")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
					 //log(String.valueOf(cntPart-1)+":"+valMark) ;
				 }
				 table.append(recordExpertShort(row[4], valMark, cntPart, aCntSection, "BranchManager", aTypeSpecialist, aView) );
				 valMark =  row[5];
				 if (aReplaceValue &&!aView&& aTypeSpecialist.equals("Expert")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
				 }				 
				 table.append(recordExpertShort(row[6], valMark, cntPart, aCntSection, "Expert", aTypeSpecialist, aView)) ;
				 valMark =  row[7];
				 if (aReplaceValue &&!aView && aTypeSpecialist.equals("Coeur")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
				 }
				 table.append(recordExpertShort(row[8], valMark, cntPart, aCntSection, "Coeur", aTypeSpecialist, aView) );
				 
				 
				 table.append("</tr>") ;
				 
			 }
		 }
		 table.append("</table>") ;
		 QualityEstimationRow row = new QualityEstimationRow() ;
		 row.setJavaScriptText(javaScript.toString()) ;
		 row.setTableColumn(table.toString()) ;
		return row;
	}
	
	private StringBuilder recordExpertShort(Object aValueMark, Object aValueMarkId,  int aCntPart,int aCntSection, String aSpecActual, String aTypeSpecialist, boolean aView) {
		StringBuilder ret = new StringBuilder() ;
		if (!aView && aTypeSpecialist.equals(aSpecActual)) {
			 
			 String property = "criterion"+(aCntPart-1) ;
			 String propertyNext = "criterion"+aCntPart +"Name";
			 if (aCntPart==aCntSection)  propertyNext = "submitButton" ;
			 String label ="Оценка зав.отд." ;
			 String value = aValueMarkId!=null?String.valueOf(aValueMarkId):"" ;
			 String size = "10" ;
			 Boolean horizontalFill = false ;
			 Boolean required = false ;
			 String fieldColSpan = "1" ;
			 //table.append(LabelField.getField(false, false, property, label, labelColSpan, hideLabel,errors));
			 String vocName = "vocQualityEstimationMark" ;
			 String parentId =  "" ;
			 ret.append(AutoCompleteField.getField(vocName,property, label, value,parentId, fieldColSpan, size
					 , horizontalFill, required, Boolean.FALSE,null,String.valueOf(1)));
			 //javaScript.append(AutoCompleteField.getJavaScript(false,vocName, property,propertyNext,label)) ;
			 
		 } else {
			 ret.append("<td align='center'>");
			 if (aValueMark==null) {
				 ret.append("&nbsp;");
			 } else {
				 ret.append(aValueMark) ;
			 }
			 ret.append("</td>") ;
		 }
		return ret ;
	}
	
	private StringBuilder recordExpert(Object aValueMark, Object aValueMarkId, Object aValue, int aCntPart, int aCntSection,int aCntSubSection, String aSpecActual, String aTypeSpecialist, boolean aFirstStr, boolean aView) {
		StringBuilder ret = new StringBuilder() ;
		if (!aView && aTypeSpecialist.equals(aSpecActual)) {
			 if (aFirstStr) {
			 String property = "criterion"+(aCntPart-1) ;
			 String propertyNext = "criterion"+aCntPart +"Name";
			 if (aCntPart==aCntSection)  propertyNext = "submitButton" ;
			 String label ="Оценка зав.отд." ;
			 String value = aValueMarkId!=null?String.valueOf(aValueMarkId):"" ;
			 String size = "15" ;
			 Boolean horizontalFill = true ;
			 Boolean required = false ;
			 String fieldColSpan = "1" ;
			 //table.append(LabelField.getField(false, false, property, label, labelColSpan, hideLabel,errors));
			 String vocName = "vocQualityEstimationMark" ;
			 String parentId =  "" ;
			 ret.append(AutoCompleteField.getField(vocName,property, label, value,parentId, fieldColSpan, size
					 , horizontalFill, required, Boolean.FALSE,null,String.valueOf((aCntSubSection+1))));
			 //javaScript.append(AutoCompleteField.getJavaScript(false,vocName, property,propertyNext,label)) ;
			 }
		 } else {
			 ret.append("<td align='center'>");
			 if (aValue==null) {
				 ret.append("&nbsp;");
			 } else {
				 if (!aView) {
					 ret.append(aValueMark) ;
				 } else {
					 ret.append("X") ;
				 }
			 }
			 ret.append("</td>") ;
		 }
		return ret ;
	}
	public Long getIdQualityEstimationByType(String aType, Long aIdCard) {
		List<Object[]> result = theManager.createNativeQuery("select qe.id, qe.expert_id from qualityestimation qe where qe.card_id='"+aIdCard+"' and qe.expertType='"+aType+"'").getResultList();
		if (result.size()>0) return WorkerServiceBean.parseLong(result.get(0)[0]) ;
		return null ;
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext;

}
