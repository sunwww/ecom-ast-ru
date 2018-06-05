package ru.ecom.mis.ejb.service.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.service.protocol.field.AutoCompleteField;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.expert.QualityEstimation;
import ru.ecom.mis.ejb.domain.expert.QualityEstimationCard;
import ru.ecom.mis.ejb.domain.expert.QualityEstimationCrit;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationKind;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.medcase.HospitalMedCaseServiceBean;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;

@Stateless
@Remote(IQualityEstimationService.class)
@SuppressWarnings("unchecked")
public class QualityEstimationServiceBean implements IQualityEstimationService {
	
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
					.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id and vpd.code='1'")
					.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id")
					.append(" left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'")
					.append(" left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id and vpd1.code='1'")
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
	
	
	
	public String getCountRow(Long aCardId) {
		
		StringBuilder sql1 = new StringBuilder() ;
		
		//sql1.append("select count(*),vqec.id from VocQualityEstimationCrit vqec")
		//	.append(" where (select qec.kind_id from qualityestimationcard qec where qec.id='").append(aCardId).append("')=vqec.kind_id")			;
		sql1.append("select vqec.id as vqecid from VocQualityEstimationCrit vqec")
			.append(" left join qualityestimationcard qec on qec.kind_id=vqec.kind_id where qec.id='").append(aCardId).append("' group by vqec.id,vqec.code  order by vqec.code")			;
		//log(sql1) ;
		List<Object> list1 = theManager.createNativeQuery(sql1.toString()).getResultList() ;
		Long cntSection ;
		//Long kind ;
		if (list1.size()>0) {
			StringBuilder result = new StringBuilder() ;
			
			cntSection = Long.valueOf(list1.size()) ;
			result.append(cntSection) ;
			for (int i =0 ; i<list1.size();i++) {
				result.append("#").append(list1.get(i));
			}
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
			cntSection = ConvertSql.parseLong(row[0]).intValue() ;
			kind = ConvertSql.parseLong(row[1]) ;
		} else {
			
			return null ;
		}
		if (!aView && !aFullExpertCard) {
			return getRowShort(replaceValue,val,kind, aCardId, aTypeSpecialist, cntSection, aView) ;
		}
		 sql.append("select vqec.id as vqecid,vqec.code as vqeccode,UPPER(vqec.name) as vqecname,vqec.shortname as vqecshortname")
			.append(" ,(select count(*) from VocQualityEstimationMark vocmark where vocmark.criterion_id=vqec.id),vqem.id as vqemid,vqem.code as vqemcode,vqem.name as vqemname,coalesce(''||vqem.mark,'-') as vqemmark")
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
				 //Milamesher 04062018 комментарии заведующего
				 .append(",")
				 .append(" (select qecBM.comment from qualityestimationcrit qecBM")
				 .append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCardId).append("'  and qecBM.estimation_id=qeBM.id")
				 .append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
				 .append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='BranchManager') as commentbranchManager")
			.append(" from VocQualityEstimationCrit vqec") 
			.append(" left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id")
			//.append(" left join qualityestimationcrit qecBM on qecBM.mark_id=vqem.id")
			//.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCardId).append("' and qeBM.expertType='BranchManager' and qecBM.estimation_id=qeBM.id")
			//.append(" left join qualityestimationcrit qecE on qecE.mark_id=vqem.id")
			//.append(" left join qualityestimation qeE on qeE.card_id='").append(aCardId).append("' and qeE.expertType='Expert' and qecE.estimation_id=qeE.id")
			//.append(" left join qualityestimationcrit qecC on qecC.mark_id=vqem.id")
			//.append(" left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("' and qeC.expertType='Coeur' and qecC.estimation_id=qeC.id")

		//diagnosis for kind
		 .append(" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqec.id");
		Boolean ifTypeBool=false;
		String sql2 = "select case when vqec.code='PR203' then '1' else '0' end  from QualityEstimationCard qec left join VocQualityEstimationKind vqec on vqec.id=qec.kind_id where qec.id=" + aCardId;
		List<Object[]> listkind = theManager.createNativeQuery(sql2).getResultList() ;
		if (listkind.size()!=0) {
			Object one = "1";
			Object row = listkind.get(0) ;
			if (row.equals(one)) {
				ifTypeBool=true;
				sql.append(" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id ")
						//.append(" left join qualityestimation qeC on qeC.card_id='").append(aCardId).append("'")
						.append(" left join qualityestimationcard qecard on qecard.id='").append(aCardId).append("'")
						.append(" left join medcase mcase on qecard.medcase_id= mcase.id ")
						.append(" left join diagnosis ds on ds.medcase_id=mcase.id ")
						.append(" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id ")
						.append(" left join vocprioritydiagnosis prior on prior.id=ds.priority_id ");
				//учитывание возраста
				sql.append("  left join patient pat on pat.id=mcase.patient_id ");
			}
		}

		sql.append(" where vqec.kind_id='").append(kind).append("'");
		if (ifTypeBool) {
			sql.append("  and ds.idc10_id=vqecrit_d.vocidc10_id  and reg.code='4' and prior.code='1'");
			//учитывание возраста
			sql.append(" and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqec.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqec.ischild=true) ");
		}
		sql.append(" group by vqem.id ,vqec.id,vqec.code,vqec.name,vqec.shortname,vqem.code ,vqem.name,vqem.mark")
		 	.append(" order by vqec.code");
		// log(sql) ;
		 StringBuilder table = new StringBuilder() ;
		 StringBuilder javaScript = new StringBuilder() ;
		 table.append("<table border='1' width=90%>")  ;
		 table.append("<tr>") ;
		 table.append("<th rowspan=2>№№п/п</th>") ;
		 table.append("<th rowspan=2 colspan=2>Критерии качества медицинской помощи</th>") ;
		 table.append("<th colspan=3>Оценочные баллы</th>") ;
		table.append("<th>Комментарии</th>") ;
		 table.append("</tr>") ;
		 table.append("<tr>") ;
		 table.append("<th>зав. отд</th>") ;
		 table.append("<th>эксперт</th>") ;
		 table.append("<th>КЭР</th>") ;
		table.append("<th>Комм. зав.</th>") ;
		 table.append("</tr>") ;
		 List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		 if (list.size()>0) {
			 boolean firststr = false;
			 int cntPart =1 ;
			 int cntSubsection = 0 ;
			 for (int i=0;i<list.size();i++) {
				 Object[] row = list.get(i) ;
				 table.append("<tr>") ;
				 if (cntSubsection<1)  {
					 cntSubsection = ConvertSql.parseLong(row[4]).intValue() ;
					 cntPart++;
					 table.append("<td rowspan='").append((cntSubsection+1)).append("' valign='top'><b><i>").append(row[1]).append(".</i></b></td>") ;
					 table.append("<td colspan=6 align='center'><b><i>").append(row[2]).append("</i></b></td>") ;
					 table.append("</tr>") ;
					 table.append("<tr>") ;
					 firststr = true ;
				 }
				 cntSubsection--;
				 if (row[5]!=null) {
					
					 table.append("<td>").append(row[7]).append("</td>") ;
					 table.append("<td>").append(row[8]).append("</td>") ;
				 
					 //BranchManager - зав.отделением
					 //Expert - эксперт
					 //Coeur - КЭР
					 Object valMarkId =  row[12];
					 //log(aTypeSpecialist) ;
					 if (replaceValue &&!aView && aTypeSpecialist.equals("BranchManager")) {
						 valMarkId=val.get(String.valueOf(cntPart-1)) ;
						 //log(String.valueOf(cntPart-1)+":"+valMarkId) ;
					 }
					 table.append(recordExpert(row[0],row[8], valMarkId,row[9],cntPart, cntSection,cntSubsection, "BranchManager", aTypeSpecialist, firststr, aView) );
					 valMarkId =  row[14];
					 if (replaceValue &&!aView&& aTypeSpecialist.equals("Expert")) {
						 valMarkId=val.get(String.valueOf(cntPart-1)) ;
					 }				 
					 table.append(recordExpert(row[0],row[8], valMarkId, row[10],cntPart, cntSection, cntSubsection, "Expert", aTypeSpecialist, firststr, aView)) ;
					 valMarkId =  row[14];
					 if (replaceValue &&!aView && aTypeSpecialist.equals("Coeur")) {
						 valMarkId=val.get(String.valueOf(cntPart-1)) ;
					 }				 
					 table.append(recordExpert(row[0],row[8], valMarkId, row[11],cntPart, cntSection, cntSubsection, "Coeur", aTypeSpecialist, firststr, aView) );


				 }
				 //Milamesher 04062018 комментарии зав.
				 if (i%2==0) {  //только 1 из оценок, чтобы без дублей
					 StringBuilder comments = new StringBuilder();
					 if (row[15] != null) comments.append(row[15]);
					 table.append("<td rowspan=\"2\">").append(comments.toString()).append("</td>");
				 }
				 table.append("</tr>") ;
				 firststr =false ;
				 
			 }
		 }
		 table.append("</table>") ;
		 table.append("<input type='hidden' value='"+list.size()+"' name='criterionSize' id='criterionSize'>") ;
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
		sql.append("select distinct vqec.id as vqecid,vqec.code as vqeccode,vqec.name as vqecname ")
				.append(",")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='BranchManager') as branchManager")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='BranchManager') as branchManagerMark")
		.append(" ,")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Expert') as expert")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Expert') as expertMark")
		.append(" ,")
		.append(" (select qecBM.mark_id from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Coeur') as coeur")
		.append(" ,")
		.append(" (select vqem.mark from qualityestimationcrit qecBM")	
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Coeur') as coeurMark")
		.append(",")
		.append(" (select list(''||qecd.defect) from qualityestimationcritdefect qecd ")
		.append(" left join qualityestimationcrit qecBM on qecBM.id=qecd.criterion")
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='BranchManager') as branchManagerMarkDefect")
		.append(",")
		.append(" (select list(''||qecd.defect) from qualityestimationcritdefect qecd ")
		.append(" left join qualityestimationcrit qecBM on qecBM.id=qecd.criterion")
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Expert') as expertMarkDefect")
		.append(",")
		.append(" (select list(''||qecd.defect) from qualityestimationcritdefect qecd ")
		.append(" left join qualityestimationcrit qecBM on qecBM.id=qecd.criterion")
		.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")	
		.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
		.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Coeur') as coeurMarkDefect")
				.append(",")
				.append(" (select qecBM.comment from qualityestimationcrit qecBM")
				.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")
				.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
				.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='BranchManager') as commentbranchManager")
				.append(" ,")
				.append(" (select qecBM.comment from qualityestimationcrit qecBM")
				.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")
				.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
				.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Expert') as commentexpert")
				.append(" ,")
				.append(" (select qecBM.comment from qualityestimationcrit qecBM")
				.append(" left join qualityestimation qeBM on qeBM.card_id='").append(aCard).append("'  and qecBM.estimation_id=qeBM.id")
				.append(" left join vocQualityEstimationMark vqem on vqem.id=qecBM.mark_id")
				.append(" where qecBM.criterion_id=vqec.id and qeBM.expertType='Coeur') as commentcoeur")
		.append(" from VocQualityEstimationCrit vqec")

		.append(" left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqec.id");
		Boolean ifTypeBool=false;
		String sql2 = "select case when vqec.code='PR203' then '1' else '0' end  from QualityEstimationCard qec left join VocQualityEstimationKind vqec on vqec.id=qec.kind_id where qec.id=" + aCard;
		List<Object[]> listkind = theManager.createNativeQuery(sql2).getResultList() ;
		if (listkind.size()!=0) {
			Object one = "1";
			Object row = listkind.get(0) ;
			if (row.equals(one)) {
				ifTypeBool=true;
				sql.append(" left join vocidc10 d on d.id=vqecrit_d.vocidc10_id ")
						.append(" left join qualityestimationcard qecard on qecard.id='").append(aCard).append("'")
						.append(" left join medcase mcase on qecard.medcase_id= mcase.id ")
						.append(" left join diagnosis ds on ds.medcase_id=mcase.id ")
						.append(" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id ")
						.append(" left join vocprioritydiagnosis prior on prior.id=ds.priority_id ");
				//учитывание возраста
				sql.append("  left join patient pat on pat.id=mcase.patient_id ");
			}
		}

		sql.append(" where vqec.kind_id='").append(aKind).append("'");
		if (ifTypeBool) {
			sql.append("  and ds.idc10_id=vqecrit_d.vocidc10_id and reg.code='4' and prior.code='1' ");
			//учитывание возраста
			sql.append(" and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqec.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqec.ischild=true) ");
		}
		sql.append(" order by vqec.code") ;
		System.out.println("shortRow="+sql.toString());
		//log(sql);
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
		 if (list.size()>0) {
			 List<String[]> list2=null;
			 if (ifTypeBool) {
				 list2 = getExecutedCriterias(aCard) ;
			 }
			 table.append("<table border=1 width=90%>")  ;//
			 table.append("<tr>") ;
			 if (ifTypeBool)
			 	table.append("<th rowspan=2 colspan=1>Критерии качества медицинской помощи (зелёным цветом выделены те критерии, которые выполнены согласно автоматическому подсчёту оказанных услуг; оранжевым - те, что не выполнены).</th>") ;
			 else
				 table.append("<th rowspan=2 colspan=1>Критерии качества медицинской помощи.</th>") ;
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
				 String defects = "";
				 Object[] row = list.get(i) ;
				 if (aTypeSpecialist.equals("BranchManager")){
					 defects = ""+row[9];
				 } else if (aTypeSpecialist.equals("Expert")) {
					 defects = ""+row[10];
				 } else if (aTypeSpecialist.equals("Coeur")) {
					 defects = ""+row[11];
				 }
				 //надо получить оказанные услуги по критериям - выполнено/нет
				 table.append("<tr>") ;
				 table.append("<td align='left'>").append(row[1]).append("-").append(row[2]).append("</td>") ;			//HERE
				 String color1="",color2="";
				 if (ifTypeBool) {
					 color1=" style=\"background-color:LightGreen\"";
					 color2=" style=\"background-color:lightsalmon\"";
				 }
				 StringBuilder comments=new StringBuilder();
				 if (row[12]!=null) comments.append(row[12]);
				 comments.append(";");
				 if (row[13]!=null) comments.append(row[13]);
				 comments.append(";");
				 if (row[14]!=null) comments.append(row[14]);
				 int index=-1;
				 if (list!=null && list2!=null) {
					 for (int t = 0; t < list2.size(); t++) {
						 if (list2.get(t)[0].toString().equals(list.get(i)[1].toString())) index = t;
					 }
				 }
				 if (index!=-1 && list2!=null && list2.get(index)!=null && list2.get(index)[1]!=null && list2.get(index)[1].equals("yes")) {
					 table.append("<td valign='top' align='right'"+color1+">").append(cntPart++).append("<input type='hidden' id='criterion" + (cntPart - 1) + "Comment' value='" + defects + "'></td>").append("<input type='hidden' id='criterion" + (cntPart - 1) + "CommentYesNo' value='"+comments.toString()+"'></td>");
				 }
				 else
					 table.append("<td valign='top' align='right'"+color2+">").append(cntPart++).append("<input type='hidden' id='criterion" + (cntPart - 1) + "Comment' value='" + defects + "'></td>").append("<input type='hidden' id='criterion" + (cntPart - 1) + "CommentYesNo' value='"+comments.toString()+"'></td>");
				 //BranchManager - зав.отделением
				 //Expert - эксперт
				 //Coeur - КЭР
				 Object valMark = row [3] ;
				
				 if (aReplaceValue &&!aView && aTypeSpecialist.equals("BranchManager")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
					 //log(String.valueOf(cntPart-1)+":"+valMark) ;
				 }
				 table.append(recordExpertShort(row[0],row[4], valMark, cntPart, aCntSection, "BranchManager", aTypeSpecialist, aView) );
				 valMark =  row[5];
				 if (aReplaceValue &&!aView&& aTypeSpecialist.equals("Expert")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
				 }				 
				 table.append(recordExpertShort(row[0],row[6], valMark, cntPart, aCntSection, "Expert", aTypeSpecialist, aView)) ;
				 valMark =  row[7];
				 if (aReplaceValue &&!aView && aTypeSpecialist.equals("Coeur")) {
					 valMark=aValueMap.get(String.valueOf(cntPart-1)) ;
				 }
				 table.append(recordExpertShort(row[0],row[8], valMark, cntPart, aCntSection, "Coeur", aTypeSpecialist, aView) );
				 //Milamesher 04062018 - комментарий по желанию
				 table.append("<td colspan=6 align='center'><input onclick=\"showYesNoCommentFromBean(").append((cntPart - 1)).append(")\" type=\"button\" value=\"Комм. зав.\" /></td>");
				 table.append("</tr>") ;
			 }
		 }
		 table.append("</table>") ;
		 table.append("<input type='hidden' value='"+list.size()+"' name='criterionSize' id='criterionSize'>") ;
		 QualityEstimationRow row = new QualityEstimationRow() ;
		 row.setJavaScriptText(javaScript.toString()) ;
		 row.setTableColumn(table.toString()) ;
		return row;
	}
	
	private StringBuilder recordExpertShort(Object aCriterionId, Object aValueMark, Object aValueMarkId,  int aCntPart,int aCntSection, String aSpecActual, String aTypeSpecialist, boolean aView) {
		return recordExpertShort( aCriterionId,  aValueMark,  aValueMarkId,   aCntPart, aCntSection,  aSpecActual,  aTypeSpecialist, aView,null);
	}
	private StringBuilder recordExpertShort(Object aCriterionId, Object aValueMark, Object aValueMarkId,  int aCntPart,int aCntSection, String aSpecActual, String aTypeSpecialist, boolean aView, String aDefects) {
		StringBuilder ret = new StringBuilder() ;
		if (!aView && aTypeSpecialist.equals(aSpecActual)) {
			 
			 String property = "criterion"+(aCntPart-1) ;
			 //String propertyNext = "criterion"+aCntPart +"Name";
			 //if (aCntPart==aCntSection)  propertyNext = "submitButton" ;
			 String label ="Оценка зав.отд." ;
			 String value = aValueMarkId!=null?String.valueOf(aValueMarkId):"" ;
			 String size = "10" ;
			 Boolean horizontalFill = false ;
			 Boolean required = false ;
			 String fieldColSpan = "1" ;
			 //table.append(LabelField.getField(false, false, property, label, labelColSpan, hideLabel,errors));
			 String vocName = "vocQualityEstimationMark" ;
			 String parentId =  ""+aCriterionId ;
			 ret.append(AutoCompleteField.getField(vocName,property, label, value,parentId, fieldColSpan, size
					 , horizontalFill, required, Boolean.FALSE,null,String.valueOf(1)));
			 //javaScript.append(AutoCompleteField.getJavaScript(false,vocName, property,propertyNext,label)) ;
			// System.out.println("1 recordExpertShot, ret="+ret);
		 } else {
			 ret.append("<td align='center'>");
			 if (aValueMark==null) {
				 ret.append("&nbsp;");
			 } else {
				 ret.append(aValueMark) ;
			 }
			// ret.append("<input type='hidden' id='criterion"+aCntPart+"Comment'></td>") ;
			 //System.out.println("2 recordExpertShot, ret="+ret);
		 }
		return ret ;
	}
	
	private StringBuilder recordExpert(Object aCriterionId, Object aValueMark, Object aValueMarkId, Object aValue, int aCntPart, int aCntSection,int aCntSubSection, String aSpecActual, String aTypeSpecialist, boolean aFirstStr, boolean aView) {
		StringBuilder ret = new StringBuilder() ;
		if (!aView && aTypeSpecialist.equals(aSpecActual)) {
			 if (aFirstStr) {
			 String property = "criterion"+(aCntPart-1) ;
			 //String propertyNext = "criterion"+aCntPart +"Name";
			 //if (aCntPart==aCntSection)  propertyNext = "submitButton" ;
			 String label ="Оценка зав.отд." ;
			 String value = aValueMarkId!=null?String.valueOf(aValueMarkId):"" ;
			 String size = "15" ;
			 Boolean horizontalFill = true ;
			 Boolean required = false ;
			 String fieldColSpan = "1" ;
			 //table.append(LabelField.getField(false, false, property, label, labelColSpan, hideLabel,errors));
			 String vocName = "vocQualityEstimationMark" ;
			 String parentId =  ""+aCriterionId ;
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
		if (result.size()>0) return ConvertSql.parseLong(result.get(0)[0]) ;
		return null ;
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext;
	@EJB IHospitalMedCaseService theHospitalMedCaseService ;
	//Milamesher получение списка критерии+услуги
	private List<String[]> getExecutedCriterias(Long aCard) {

		List<String[]> total = new ArrayList<String[]>();
		String query="select distinct vqecrit.code,vqecrit.name,vqecrit.medservicecodes\n" +
				"from vocqualityestimationcrit vqecrit\n" +
				"left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id\n" +
				"left join vocidc10 d on d.id=vqecrit_d.vocidc10_id\n" +
				"left join diagnosis ds on ds.idc10_id=d.id\n" +
				"left join medcase mc on mc.id=ds.medcase_id\n" +
				"left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
				"left join vocprioritydiagnosis prior on prior.id=ds.priority_id\n" +
				"left join qualityestimationcard qecard on qecard.medcase_id=mc.id\n" +
				"where qecard.id="+aCard+" and reg.code='4' and prior.code='1'";
		List<Object[]> list = theManager.createNativeQuery(query).getResultList() ;
		if (list.size()>0) {
			query = "select m.parent_id from qualityestimationcard qecard\n" +
					"left join qualityestimation qe on qe.card_id=qecard.id\n" +
					"left join medcase m on m.id=qecard.medcase_id\n" +
					"where qecard.id=" + aCard;
			List<Object> list0 = theManager.createNativeQuery(query).getResultList();
			if (list0.size() > 0) {
				Long id = Long.parseLong(list0.get(0).toString());
				//log(id);
				String json=theHospitalMedCaseService.getAllServicesByMedCase(id);
				//log(json);
				List<String> allMatches = new ArrayList<String>();
				Matcher m = Pattern.compile("\"vmscode\":\"[A-Za-z0-9.]*\"").matcher(json);
				while (m.find()) {
					allMatches.add(m.group().replace("\"vmscode\":\"", "").replace("\"}]", "").replace("\"", ""));
					//res.append(m.group().replace("\"vmscode\":\"", "").replace("\"}]", "").replace("\"", "")).append(" ");
				}
				for (int i = 0; i < list.size(); i++) {
					String mcodes = (list.get(i)[2] != null) ? list.get(i)[2].toString() : "";
					Boolean flag = false;
					total.add(new String[2]);
					total.get(i)[0] = list.get(i)[0].toString();
					if (allMatches.size() > 0) {
						for (int j = 0; j < allMatches.size(); j++) {
							String scode = allMatches.get(j).toString();
							if (mcodes.contains("'" + scode + "'")) flag = true;
							//log("scode "+scode);
							//log("mcodes"+mcodes);
						}
					}
					if (flag) total.get(i)[1] = "yes";
					else total.get(i)[1] = "no";
					//log (flag);
				}
			}
			//log("total"+total.toString());
		}
		return total;
	}
	//Milamesher создание черновик ЭК
	public Long createDraftEK(Long aMcaseId) {
		//если уже есть QE, то вернуть имеющийся, нет - создать
		List<Object> ids= theManager.createNativeQuery("select id from qualityestimation where card_id=ANY(select id from qualityestimationcard  where medcase_id=" + aMcaseId + " and kind_id=5)").getResultList();
		if (ids == null || ids.size()==0) {
			QualityEstimationCard qecard = new QualityEstimationCard();
			//беру первый основной клинический диагноз
			Long idc10_id = ConvertSql.parseLong(theManager.createNativeQuery("select ds.idc10_id from diagnosis ds\n" +
					"left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id \n" +
					"left join vocprioritydiagnosis prior on prior.id=ds.priority_id\n" +
					" where ds.medcase_id= " + aMcaseId + " and prior.code='1' and reg.code='4' order by ds.id").getResultList().get(0));
			String login = theContext.getCallerPrincipal().toString();
			MedCase mcase = aMcaseId != null ? theManager.find(MedCase.class, aMcaseId) : null;
			Long kind_id = ConvertSql.parseLong(theManager.createNativeQuery("select id from vocqualityestimationkind where code='PR203'").getResultList().get(0));
			VocQualityEstimationKind kind = kind_id != null ? theManager.find(VocQualityEstimationKind.class, kind_id) : null;
			Long wf_id = ConvertSql.parseLong(theManager.createNativeQuery("select id from workfunction where secuser_id=(select id from secuser where login='" + login + "')").getResultList().get(0));
			WorkFunction wf = wf_id != null ? theManager.find(WorkFunction.class, wf_id) : null;
			VocIdc10 idc10 = idc10_id != null ? theManager.find(VocIdc10.class, idc10_id) : null;
			Object cardnumber = theManager.createNativeQuery("select code from statisticstub  where medcase_id=(select parent_id from medcase where id=" + aMcaseId + ") ").getResultList().get(0);
			if (mcase != null && kind_id != null && wf != null && idc10 != null && cardnumber != null) {
				qecard.setCreateDate(new java.sql.Date((new java.util.Date()).getTime()));
				qecard.setCreateUsername(login);
				qecard.setMedcase(mcase);
				qecard.setPatient(mcase.getPatient());
				qecard.setKind(kind);
				qecard.setDepartment(mcase.getDepartment());
				qecard.setDoctorCase(wf);
				qecard.setDiagnosis(idc10.getName());  //надо?
				qecard.setIdc10(idc10);
				qecard.setCardNumber(cardnumber.toString());
				theManager.persist(qecard);
			/*Long qecardid=Long.valueOf(qecard.getId()) ;
			if (qecardid!=null) {*/
				QualityEstimation qe = new QualityEstimation();
				qe.setCreateDate(new java.sql.Date((new java.util.Date()).getTime()));
				qe.setCreateUsername(login);
				qe.setExpertType("BranchManager");
				qe.setExpert(wf);
				qe.setCard(qecard);
				qe.setIsDraft(true);
				theManager.persist(qe);
				//нужно критерии
				List<Object> crits = theManager.createNativeQuery("select distinct vqecrit.id\n" +
						"from vocqualityestimationcrit vqecrit\n" +
						"left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqecrit.id\n" +
						"left join vocidc10 d on d.id=vqecrit_d.vocidc10_id\n" +
						"left join diagnosis ds on ds.idc10_id=d.id\n" +
						"left join medcase mc on mc.id=ds.medcase_id\n" +
						"left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
						"left join vocprioritydiagnosis prior on prior.id=ds.priority_id\n" +
						"left join patient pat on pat.id=mc.patient_id\n" +
						"where mc.id=" + aMcaseId + " and reg.code='4' and prior.code='1'\n" +
						" and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqecrit.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqecrit.ischild=true)").getResultList();
				if (crits != null) {
					for (int i = 0; i < crits.size(); i++) {
						QualityEstimationCrit crit = new QualityEstimationCrit();
						Long id = Long.parseLong(crits.get(i).toString());//Long.parseLong(String.valueOf(crits.get(i)));
						VocQualityEstimationCrit vcrit = id != null ? theManager.find(VocQualityEstimationCrit.class, id) : null;
						if (vcrit != null) {
							crit.setEstimation(qe);
							crit.setCriterion(vcrit);
							theManager.persist(crit);
						}
					}
				}
				return Long.valueOf(qe.getId());
			}
			return null;
		}
		else {
			Object isdraft= theManager.createNativeQuery("select case when isdraft=true  then '1' else '0' end from qualityestimation where id="+ids.get(0)).getResultList().get(0);
			if (isdraft.equals("1")) return ConvertSql.parseLong(ids.get(0));  //это черновик
			else return null; //ЗАВ УЖЕ ЗАПОЛНИЛ!
		}
	}
}